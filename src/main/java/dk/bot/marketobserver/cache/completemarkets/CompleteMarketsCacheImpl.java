package dk.bot.marketobserver.cache.completemarkets;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import dk.bot.marketobserver.model.Market;
import dk.bot.marketobserver.model.MarketRunner;

public class CompleteMarketsCacheImpl implements CompleteMarketsCache {

	private Map<Integer, Market> completeMarketsMap = new HashMap<Integer, Market>();

	private ReentrantLock lock = new ReentrantLock();

	public void updateCompleteMarkets(Map<Integer, Market> completeMarkets) {
		lock.lock();
		try {
			for (Integer marketId : completeMarkets.keySet()) {

				Market newMarket = completeMarkets.get(marketId);

				/**
				 * Do not lose near/far SP and last best prices before market is
				 * turn in play.
				 */
				Market previousMarket = completeMarketsMap.get(marketId);
				if (previousMarket != null) {

					for (MarketRunner runner : newMarket.getMarketRunners().getMarketRunners()) {

						MarketRunner previousRunner = previousMarket.getMarketRunners().getMarketRunner(
								runner.getSelectionId());

						if (previousRunner != null) {

							/**
							 * Do not lose near/far SP and last best prices when
							 * market is turn in play.
							 */
							if (runner.getActualSP() > 0) {
								runner.setNearSP(previousRunner.getNearSP());
								runner.setFarSP(previousRunner.getFarSP());
							}

							/**
							 * Do not lose last best prices before market when
							 * turned in play.
							 */
							if (newMarket.getMarketRunners().getInPlayDelay() > 0) {
								if (previousRunner.getLastPriceToBack() == 0 || previousRunner.getLastPriceToLay() == 0) {
									runner.setLastPriceToBack(previousRunner.getPriceToBack());
									runner.setLastPriceToLay(previousRunner.getPriceToLay());
								} else {
									runner.setLastPriceToBack(previousRunner.getLastPriceToBack());
									runner.setLastPriceToLay(previousRunner.getLastPriceToLay());
								}
							}
						}
					}
				}

				completeMarketsMap.put(marketId, newMarket);

			}
		} finally {
			lock.unlock();
		}

	}

	public Market getCompleteMarket(int marketId) {
		lock.lock();
		try {
			return completeMarketsMap.get(marketId);
		} finally {
			lock.unlock();
		}
	}

	public Map<Integer, Market> getCompleteMarkets() {
		lock.lock();
		try {
			Map<Integer, Market> marketsCopy = new HashMap<Integer, Market>(completeMarketsMap.size());

			for (Integer marketId : completeMarketsMap.keySet()) {
				marketsCopy.put(marketId, completeMarketsMap.get(marketId));
			}

			return marketsCopy;
		} finally {
			lock.unlock();
		}
	}

	public int getMarketsAmount() {
		lock.lock();
		try {
			return completeMarketsMap.size();
		} finally {
			lock.unlock();
		}
	}

}
