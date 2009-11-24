package dk.bot.marketobserver.marketservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dk.bot.marketobserver.cache.adrianmassey.matchedadrianmassey.MatchedAdrianMasseyCache;
import dk.bot.marketobserver.cache.bfmarket.BFMarketCache;
import dk.bot.marketobserver.cache.bwin.matchedbwin.MatchedBwinCache;
import dk.bot.marketobserver.cache.racingpost.matchedracingpost.MatchedRacingPostCache;
import dk.bot.marketobserver.model.Market;
import dk.bot.marketobserver.model.MarketData;
import dk.bot.marketobserver.tasks.adrianmassey.MasseyPrices;
import dk.bot.marketobserver.tasks.bwin.BetFairBwinRegionEnum;
import dk.bot.marketobserver.tasks.bwin.BwinMarketPrices;
import dk.bot.marketobserver.tasks.racingpost.RacingPostPrices;

public class MarketServiceImpl implements MarketService {

	private final Log log = LogFactory.getLog(MarketServiceImpl.class.getSimpleName());

	private MatchedBwinCache matchedBwinCache;
	private MatchedRacingPostCache matchedRacingPostCache;
	private MatchedAdrianMasseyCache matchedAdrianMasseyCache;

	public void setMatchedBwinCache(MatchedBwinCache matchedBwinCache) {
		this.matchedBwinCache = matchedBwinCache;
	}

	public void setMatchedRacingPostCache(MatchedRacingPostCache matchedRacingPostCache) {
		this.matchedRacingPostCache = matchedRacingPostCache;
	}

	public void setMatchedAdrianMasseyCache(MatchedAdrianMasseyCache matchedAdrianMasseyCache) {
		this.matchedAdrianMasseyCache = matchedAdrianMasseyCache;
	}

	/** Match betting exchange markets with bwin/oddchecker,etc...
	 * 
	 */
	public List<Market> getCompositeMarkets(Collection<MarketData> markets) {
		
		List<Market> compositeMarkets = new ArrayList<Market>();
		/** match betfair markets with bwin markets */
		for (MarketData marketData : markets) {
			
			BetFairBwinRegionEnum region = BetFairBwinRegionEnum.getRegion(marketData.getEventHierarchy());
						
			if(marketData.getRunners()!=null) {
			BwinMarketPrices bwinPrices = matchedBwinCache.getObject(new BwinMarketPrices(marketData.getMarketId()));
			MasseyPrices masseyPrices = matchedAdrianMasseyCache.getObject(new MasseyPrices(marketData.getMarketId()));
			RacingPostPrices racingPostPrices = matchedRacingPostCache.getObject(new RacingPostPrices(marketData.getMarketId()));

			Market market = new Market(marketData, region, bwinPrices, null);
			market.setMasseyPrices(masseyPrices);
			market.setRacingPostPrices(racingPostPrices);
			compositeMarkets.add(market);
			}
			else {
				log.error("MarketDetails object is null.");
			}
		}

		return compositeMarkets;
	}
}
