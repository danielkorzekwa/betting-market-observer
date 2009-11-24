package dk.bot.marketobserver.tasks.racingpost;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateMidnight;

import dk.bot.marketobserver.cache.bfmarket.BFMarketCache;
import dk.bot.marketobserver.cache.racingpost.RacingPostCache;
import dk.bot.marketobserver.cache.racingpost.matchedracingpost.MatchedRacingPostCache;
import dk.bot.marketobserver.model.MarketData;
import dk.bot.marketobserver.util.AbstractTask;
import dk.bot.racingpost.RacingPost;
import dk.bot.racingpost.model.RacingPostMarket;

/**
 * Get markets from racingpost.com and put them into the cache.
 * 
 * @author daniel
 * 
 */
public class RefreshRacingpostCache extends AbstractTask {

	private final RacingPost racingPost;
	private final RacingPostCache racingPostCache;
	private final BFMarketCache bfMarketCache;
	private final MatchedRacingPostCache matchedRacingPostCache;

	public RefreshRacingpostCache(String taskName, RacingPost racingPost, BFMarketCache bfMarketCache,
			RacingPostCache racingPostCache,
			MatchedRacingPostCache matchedRacingPostCache) {
		super(taskName);
		this.racingPost = racingPost;
		this.bfMarketCache = bfMarketCache;
		this.racingPostCache = racingPostCache;
		this.matchedRacingPostCache = matchedRacingPostCache;
	}

	@Override
	public void doExecute() {
		Set<RacingPostMarket> racingPostMarkets = new HashSet<RacingPostMarket>();
		racingPostMarkets.addAll(racingPost.getMarkets(new DateMidnight().toDate()));
		racingPostMarkets.addAll(racingPost.getMarkets(new DateMidnight().plusDays(1).toDate()));

		/** Match betfair - racingpost markets */
		Set<RacingPostPrices> racingPostPricesList = new HashSet<RacingPostPrices>();
		Collection<MarketData> markets = bfMarketCache.getObjects();
		if (markets != null) {
			for (MarketData marketData : markets) {
				
					RacingPostPrices racingPostPrices = RacingPostHelper.match(marketData.getMarketId(), marketData
							.getMenuPath(), marketData.getEventDate(), marketData.getNumberOfWinners(),
							marketData.getRunners(), racingPostMarkets);
					if (racingPostPrices != null) {
						racingPostPricesList.add(racingPostPrices);
					}
				}
			
		}

		long creationTime = System.currentTimeMillis();
		matchedRacingPostCache.setObjects(racingPostPricesList, creationTime);
		racingPostCache.setObjects(racingPostMarkets, creationTime);
	}
}
