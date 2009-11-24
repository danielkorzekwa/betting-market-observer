package dk.bot.marketobserver.tasks.bwin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dk.bot.bwinservice.BWinService;
import dk.bot.bwinservice.model.BWinMarket;
import dk.bot.bwinservice.model.BWinSport;
import dk.bot.bwinservice.model.BwinMarkets;
import dk.bot.bwinservice.model.BwinRegionEnum;
import dk.bot.bwinservice.model.BwinSportEnum;
import dk.bot.marketobserver.cache.bfmarket.BFMarketCache;
import dk.bot.marketobserver.cache.bwin.BwinCache;
import dk.bot.marketobserver.cache.bwin.matchedbwin.MatchedBwinCache;
import dk.bot.marketobserver.model.MarketData;
import dk.bot.marketobserver.util.AbstractTask;

/**
 * Get bwin markets from bwin.com and put into the bwin cache.
 * 
 * @author daniel
 * 
 */
public class RefreshBwinCache extends AbstractTask {

	private final BWinService bwinService;
	private final BwinCache bwinCache;
	private final BFMarketCache bfMarketCache;
	private final MatchedBwinCache matchedBwinCache;

	public RefreshBwinCache(String taskName, BWinService bwinService, BFMarketCache bfMarketCache,
			BwinCache bwinCache, MatchedBwinCache matchedBwinCache) {
		super(taskName);
		this.bwinService = bwinService;
		this.bfMarketCache = bfMarketCache;
		this.bwinCache = bwinCache;
		this.matchedBwinCache = matchedBwinCache;
	}

	@Override
	public void doExecute() {
		BwinMarkets bwinMarkets = bwinService.getBwinMarkets();
		Map<BwinSportEnum, BWinSport> bwinSports = convertToBwinSports(bwinMarkets.getMarkets());

		/** Match betfair - bwin markets */
		Set<BwinMarketPrices> bwinPricesList = new HashSet<BwinMarketPrices>();
		Collection<MarketData> markets = bfMarketCache.getObjects();
		if (markets != null) {
			for (MarketData marketData : markets) {
					BetFairBwinRegionEnum region = BetFairBwinRegionEnum.getRegion(marketData.getEventHierarchy());
					BwinMarketPrices bwinPrices = BwinPriceHelper.getBWinMarketPrices(marketData,region, bwinSports);
					if (bwinPrices != null) {
						bwinPricesList.add(bwinPrices);
					}
			}
		}

		long creationTime = System.currentTimeMillis();
		matchedBwinCache.setObjects(bwinPricesList, creationTime);
		bwinCache.setObjects(bwinMarkets.getMarkets(), creationTime);
		if (!bwinMarkets.getStatus()) {
			throw new RuntimeException("Bwin error.");
		}
	}

	private Map<BwinSportEnum, BWinSport> convertToBwinSports(Collection<BWinMarket> bwinMarkets) {
		Map<BwinSportEnum, BWinSport> bwinSports = new HashMap<BwinSportEnum, BWinSport>();

		for (BWinMarket bwinMarket : bwinMarkets) {
			BWinSport bwinSport = bwinSports.get(bwinMarket.getSport());
			if (bwinSport == null) {
				bwinSport = new BWinSport(bwinMarket.getSport(), new HashMap<BwinRegionEnum, List<BWinMarket>>());
				bwinSports.put(bwinMarket.getSport(), bwinSport);
			}
			List<BWinMarket> regionMarkets = bwinSport.getMarkets().get(bwinMarket.getRegion());
			if (regionMarkets == null) {
				regionMarkets = new ArrayList<BWinMarket>();
				bwinSport.getMarkets().put(bwinMarket.getRegion(), regionMarkets);
			}

			regionMarkets.add(bwinMarket);
		}

		return bwinSports;

	}
}
