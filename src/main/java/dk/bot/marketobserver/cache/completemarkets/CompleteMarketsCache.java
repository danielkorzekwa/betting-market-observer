package dk.bot.marketobserver.cache.completemarkets;

import java.util.Map;

import dk.bot.marketobserver.model.Market;

/**
 * Keeps all completeMarkets.
 * 
 * @author daniel
 * 
 */
public interface CompleteMarketsCache {

	/**
	 * Update market in cache if already exist or add
	 * if not exist.
	 * 
	 * @param completeMarkets key - marketId, value - market runners
	 */
	public void updateCompleteMarkets(Map<Integer,Market> completeMarkets);
	
	/**Gets latest state of market.
	 * 
	 * @param marketId Market id that the market is returned for.
	 * @return null if not found
	 */
	public Market getCompleteMarket(int marketId);
	
	/**Gets latest state of markets.
	 * 
	 * @return key - marketId
	 */
	public Map<Integer,Market> getCompleteMarkets();
	
	/** Returns amount of markets in cache
	 * 
	 * @return
	 */
	public int getMarketsAmount();
	
}
