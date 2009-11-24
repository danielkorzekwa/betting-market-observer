package dk.bot.marketobserver.cache.bwin.matchedbwin;

import dk.bot.marketobserver.cache.ObjectCache;
import dk.bot.marketobserver.tasks.bwin.BwinMarketPrices;

/**Cache for matched markets between Betfair and Bwin.
 * 
 * @author daniel
 *
 */
public class MatchedBwinCache extends ObjectCache<BwinMarketPrices>{

	public MatchedBwinCache(String cacheName,int expiryTime) {
		super(cacheName,expiryTime);
	}
}
