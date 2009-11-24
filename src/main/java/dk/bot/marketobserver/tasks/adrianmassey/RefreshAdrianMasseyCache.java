package dk.bot.marketobserver.tasks.adrianmassey;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateMidnight;

import dk.bot.adrianmassey.AdrianMassey;
import dk.bot.adrianmassey.model.MasseyHRMarket;
import dk.bot.marketobserver.cache.adrianmassey.AdrianMasseyCache;
import dk.bot.marketobserver.cache.adrianmassey.matchedadrianmassey.MatchedAdrianMasseyCache;
import dk.bot.marketobserver.cache.bfmarket.BFMarketCache;
import dk.bot.marketobserver.model.MarketData;
import dk.bot.marketobserver.util.AbstractTask;

/**
 * Get markets from adrianmassey.com and put into the cache.
 * 
 * @author daniel
 * 
 */
public class RefreshAdrianMasseyCache extends AbstractTask {

	private final AdrianMassey adrianMassey;
	private final AdrianMasseyCache adrianMasseyCache;
	private final BFMarketCache bfMarketCache;
	private final MatchedAdrianMasseyCache matchedAdrianMasseyCache;

	public RefreshAdrianMasseyCache(String taskName, AdrianMassey adrianMassey, BFMarketCache bfMarketCache,
			AdrianMasseyCache adrianMasseyCache,
			MatchedAdrianMasseyCache matchedAdrianMasseyCache) {
		super(taskName);
		this.adrianMassey = adrianMassey;
		this.bfMarketCache = bfMarketCache;
		this.adrianMasseyCache = adrianMasseyCache;
		this.matchedAdrianMasseyCache = matchedAdrianMasseyCache;
	}

	@Override
	public void doExecute() {
		Set<MasseyHRMarket> massayMarkets = new HashSet<MasseyHRMarket>();
		massayMarkets.addAll(adrianMassey.getMarkets(new DateMidnight().toDate()));
		massayMarkets.addAll(adrianMassey.getMarkets(new DateMidnight().plusDays(1).toDate()));

		/** Match betfair - massey markets */
		Set<MasseyPrices> masseyPricesList = new HashSet<MasseyPrices>();
		Collection<MarketData> markets = bfMarketCache.getObjects();
		if (markets != null) {
			for (MarketData marketData : markets) {
				
					MasseyPrices masseyPrices = MasseyHelper.match(marketData.getMarketId(), marketData.getMenuPath(),
							marketData.getEventDate(), marketData.getNumberOfWinners(), marketData.getRunners(),
							massayMarkets);
					if (masseyPrices != null) {
						masseyPricesList.add(masseyPrices);
					}
				
			}
		}
		long creationTime = System.currentTimeMillis();
		adrianMasseyCache.setObjects(massayMarkets, creationTime);
		matchedAdrianMasseyCache.setObjects(masseyPricesList, creationTime);

	}
}
