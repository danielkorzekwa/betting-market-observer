package dk.bot.marketobserver.runnerservice;

import java.util.Set;

import dk.bot.marketobserver.model.MUBets;
import dk.bot.marketobserver.model.Market;
import dk.bot.marketobserver.model.MarketData;

/**Listen for new market data
 * 
 * @author daniel
 *
 */
public interface MarketRunnerListener {

	/**Called when new market prices are available.*/
	public void onMarketPrices(Market completeMarket);
	
	/**Called when new muBets data is available*/
	public void onMUBetsData(MUBets muBets);
	
	/**Called when new list of active markets is available.*/
	public void onMarketDiscovery(Set<MarketData> markets);
}
