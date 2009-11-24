package dk.bot.marketobserver.cache.adrianmassey;

import dk.bot.adrianmassey.model.MasseyHRMarket;
import dk.bot.marketobserver.cache.ObjectCache;

/**Cache for matched markets between Betfair and AdrianMassey.
 * 
 * @author daniel
 *
 */
public class AdrianMasseyCache extends ObjectCache<MasseyHRMarket>{

	public AdrianMasseyCache(String cacheName, int expiryTime) {
		super(cacheName, expiryTime);
	}


}
