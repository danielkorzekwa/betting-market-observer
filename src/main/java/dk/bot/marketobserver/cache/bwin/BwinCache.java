package dk.bot.marketobserver.cache.bwin;

import dk.bot.bwinservice.model.BWinMarket;
import dk.bot.marketobserver.cache.ObjectCache;

/**Cache for Bwin markets
 * 
 * @author daniel
 *
 */
public class BwinCache extends ObjectCache<BWinMarket>{

	/**
	 * 
	 * @param expireTime Expire time in seconds
	 */
	public BwinCache(String cacheName,int expiryTime) {
		super(cacheName,expiryTime);
	}
	
}
