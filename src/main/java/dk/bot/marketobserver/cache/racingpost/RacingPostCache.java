package dk.bot.marketobserver.cache.racingpost;

import dk.bot.marketobserver.cache.ObjectCache;
import dk.bot.racingpost.model.RacingPostMarket;

/**Cache for racing post markets.
 * 
 * @author daniel
 *
 */
public class RacingPostCache extends ObjectCache<RacingPostMarket>{

	public RacingPostCache(String cacheName,int expiryTime) {
		super(cacheName,expiryTime);
	}
}
