package dk.bot.marketobserver.cache.adrianmassey.matchedadrianmassey;

import dk.bot.marketobserver.cache.ObjectCache;
import dk.bot.marketobserver.tasks.adrianmassey.MasseyPrices;

public class MatchedAdrianMasseyCache extends ObjectCache<MasseyPrices>{

	public MatchedAdrianMasseyCache(String cacheName, int expiryTime) {
		super(cacheName, expiryTime);
	}
}
