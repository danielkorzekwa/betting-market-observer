package dk.bot.marketobserver;

import java.util.Map;

import dk.bot.marketobserver.model.Market;

/**Main bean of a market observer
 * 
 * @author daniel
 *
 */
public interface MarketObserver {

	/**Gets latest state of markets from complete markets cache.
	 * 
	 * @return key - marketId
	 */
	public Map<Integer,Market> getCompleteMarkets();
	
	/**Start service*/
	public void start();
	
}
