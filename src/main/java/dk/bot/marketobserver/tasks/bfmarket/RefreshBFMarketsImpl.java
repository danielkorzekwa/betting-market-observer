package dk.bot.marketobserver.tasks.bfmarket;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dk.bot.betfairservice.BetFairException;
import dk.bot.betfairservice.BetFairService;
import dk.bot.betfairservice.model.BFMarketData;
import dk.bot.betfairservice.model.BFMarketDetails;
import dk.bot.marketobserver.cache.bfmarket.BFMarketCache;
import dk.bot.marketobserver.dao.MarketDetailsDAO;
import dk.bot.marketobserver.marketservice.MarketServiceConfig;
import dk.bot.marketobserver.model.MarketData;
import dk.bot.marketobserver.model.MarketDetailsRunner;
import dk.bot.marketobserver.model.MarketFilter;
import dk.bot.marketobserver.model.factory.MarketDataFactory;
import dk.bot.marketobserver.model.factory.MarketDetailsRunnerFactory;
import dk.bot.marketobserver.runnerservice.MarketRunnerListener;
import dk.bot.marketobserver.tasks.bwin.BwinMarketPrices;
import dk.bot.marketobserver.util.AbstractTask;
import dk.bot.marketobserver.util.MarketFilterMatcher;

/**
 * Get markets from betfair.com and put them into cache.
 * 
 * @author daniel
 * 
 */
public class RefreshBFMarketsImpl extends AbstractTask {

	private final BetFairService betFairService;
	private final BFMarketCache bfMarketCache;
	private MarketServiceConfig marketServiceConfig;
	private final MarketDetailsDAO marketDetailsDao;
	private MarketRunnerListener marketRunnerListener;

	public RefreshBFMarketsImpl(String taskName, BetFairService betFairService, MarketDetailsDAO marketDetailsDao,
			BFMarketCache bfMarketCache) {
		super(taskName);
		this.betFairService = betFairService;
		this.marketDetailsDao = marketDetailsDao;
		this.bfMarketCache = bfMarketCache;
	}
	
	

	public void init(MarketServiceConfig marketServiceConfig,MarketRunnerListener marketRunnerListener) {
		this.marketServiceConfig = marketServiceConfig;
		this.marketRunnerListener = marketRunnerListener;
	}

	@Override
	public void doExecute() {
		if (marketServiceConfig == null) {
			throw new IllegalStateException("Service not configured.");
		}

		long now = System.currentTimeMillis();

		Date fromDate = new Date(now + marketServiceConfig.getStartInHoursFrom() * 1000l * 3600l);
		Date toDate = new Date(fromDate.getTime() + marketServiceConfig.getStartInHoursTo() * 1000l * 3600l);

		Set<Integer> eventTypeIds = new HashSet<Integer>();
		if (marketServiceConfig.getMarketFilters() != null) {
			for (MarketFilter marketFilter : marketServiceConfig.getMarketFilters()) {
				if (marketFilter.getEventTypeId() != null) {
					eventTypeIds.add(marketFilter.getEventTypeId());
				}
			}
		}
		List<BFMarketData> bfMarkets = betFairService.getMarkets(fromDate, toDate, eventTypeIds);
		long creationTime = System.currentTimeMillis();

		/** filter markets */
		List<BFMarketData> matchedMarkets = new ArrayList<BFMarketData>();
		for (BFMarketData market : bfMarkets) {
			boolean matched = checkMarketConditions(market, marketServiceConfig.getExchangeId(), marketServiceConfig
					.getMarketStatus(), marketServiceConfig.getMarketType(), marketServiceConfig.getMarketFilters());

			if (matched) {
				matchedMarkets.add(market);
			}
		}

		Set<MarketData> markets = new HashSet<MarketData>(matchedMarkets.size());
		for (BFMarketData bfMarket : matchedMarkets) {
			
			boolean saveInDb=false;
			MarketData cachedMarketData = bfMarketCache.getObject(new MarketData(bfMarket.getMarketId()));
			List<MarketDetailsRunner> marketRunners;
			Date suspendTime;
			if(cachedMarketData!=null) {
				marketRunners = cachedMarketData.getRunners();
				suspendTime = cachedMarketData.getSuspendTime();
			}
			else {
				MarketData marketDetails = marketDetailsDao.findMarketDetails(bfMarket.getMarketId());
				if (marketDetails == null) {
					BFMarketDetails bfMarketDetails = betFairService.getMarketDetails(bfMarket.getMarketId());
					marketRunners = MarketDetailsRunnerFactory.create(bfMarketDetails.getRunners());
					suspendTime = bfMarketDetails.getMarketSuspendTime();
					saveInDb=true;
				}
				else {
					marketRunners = marketDetails.getRunners();
					suspendTime=marketDetails.getSuspendTime();
				}
			}
			
			MarketData marketData = MarketDataFactory.create(bfMarket,suspendTime,marketRunners);
			if(saveInDb) {
				marketDetailsDao.saveMarketDetails(marketData);
			}
			markets.add(marketData);
		}
		bfMarketCache.setObjects(markets, creationTime);
		marketRunnerListener.onMarketDiscovery(markets);
	}

	/**
	 * 
	 * @param market
	 * @return false if all conditions are not true
	 */
	private boolean checkMarketConditions(BFMarketData market, int exchangeId, String marketStatus, String marketType,
			List<MarketFilter> marketFilters) {

		if (market.getExchangeId() != exchangeId) {
			return false;
		}
		if (marketStatus != null && !market.getMarketStatus().equals(marketStatus)) {
			return false;
		}
		if (marketType != null && !market.getMarketType().equals(marketType)) {
			return false;
		}

		boolean matched = MarketFilterMatcher.match(market.getEventHierarchy(), market.getMarketName(),marketFilters);
		if (!matched) {
			return false;
		}

		return true; // all conditions are true
	}

}
