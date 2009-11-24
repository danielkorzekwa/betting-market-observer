package dk.bot.marketobserver.cache.racingpost.matchedracingpost;

import dk.bot.marketobserver.cache.ObjectCache;
import dk.bot.marketobserver.tasks.racingpost.RacingPostPrices;

/**Cache for matched markets between Betfair and AdrianMassey.
 * 
 * @author daniel
 *
 */
public class MatchedRacingPostCache extends ObjectCache<RacingPostPrices>{

	public MatchedRacingPostCache(String cacheName,int expiryTime) {
		super(cacheName,expiryTime);
	}
}
