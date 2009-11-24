package dk.bot.marketobserver.tasks.analyzerunners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;

import dk.bot.marketobserver.cache.bfmarket.BFMarketCache;
import dk.bot.marketobserver.cache.completemarkets.CompleteMarketsCache;
import dk.bot.marketobserver.marketservice.MarketService;
import dk.bot.marketobserver.model.Market;
import dk.bot.marketobserver.model.MarketData;
import dk.bot.marketobserver.runnerservice.RunnerService;
import dk.bot.marketobserver.util.AbstractTask;

/**
 * Scheduled task to monitor HR sp markets that start 10m mins before market time.
 * 
 * @author daniel
 * 
 */
public class HR10MinBeforeRunnerServiceTaskImpl extends AbstractTask {

	private final Log log = LogFactory.getLog(RunnerServiceTaskImpl.class.getSimpleName());

	private final RunnerService runnerService;
	private final CompleteMarketsCache completeMarketsCache;

	private RunnersSummary runnersSummary;

	private final BFMarketCache bfMarketCache;

	private final MarketService marketService;

	public HR10MinBeforeRunnerServiceTaskImpl(String taskName, RunnerService runnerService,
			MarketService marketService, BFMarketCache bfMarketCache, CompleteMarketsCache completeMarketsCache) {
		super(taskName);
		this.runnerService = runnerService;
		this.marketService = marketService;
		this.bfMarketCache = bfMarketCache;
		this.completeMarketsCache = completeMarketsCache;
	}

	@Override
	public void doExecute() {
		Collection<MarketData> marketData = bfMarketCache.getObjects();
		if (marketData == null) {
			marketData = new ArrayList<MarketData>();
		}
		/** Monitor HR markets that start less than 2h before market time and currently inplay Soccer markets. */
		List<MarketData> filteredMarkets = new ArrayList<MarketData>();
		DateTime now = new DateTime();
		for (MarketData market : marketData) {
			Market completeMarket = completeMarketsCache.getCompleteMarket(market.getMarketId());
			if (market.isBsbMarket() && market.getEventHierarchy().startsWith("/7/")
					&& market.getEventDate().getTime() - now.toDate().getTime() <= 1000 * 60 * 120) {
				filteredMarkets.add(market);
			}
			else if(completeMarket!=null && completeMarket.getMarketRunners().getInPlayDelay()>0 && market.getEventHierarchy().startsWith("/1/") ) {
				filteredMarkets.add(market);
			}
		}
		
		if (filteredMarkets.size() > 0) {

			List<Market> compositeMarkets = marketService.getCompositeMarkets(filteredMarkets);

			runnersSummary = runnerService.analyzeRunners(compositeMarkets, false);
			completeMarketsCache.updateCompleteMarkets(runnersSummary.getCompleteMarkets());

			if (runnersSummary.getLastException() != null) {
				throw new RuntimeException(runnersSummary.getLastException());
			}
		}
		/**If no markets were analyzed then wait 10 sec.*/
		else {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				log.error("Error",e);
			}
		}
	}

	public RunnersSummary getRunnersSummary() {
		return runnersSummary;
	}
}
