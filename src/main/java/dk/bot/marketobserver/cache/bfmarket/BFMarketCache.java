package dk.bot.marketobserver.cache.bfmarket;

import dk.bot.marketobserver.cache.ObjectCache;
import dk.bot.marketobserver.model.MarketData;

/**Cache for Markets from betfair.com
 * 
 * @author daniel
 *
 */
public class BFMarketCache extends ObjectCache<MarketData>{

	public BFMarketCache(String cacheName,int expiryTime) {
		super(cacheName, expiryTime);
	}
}
