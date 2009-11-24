package dk.bot.marketobserver.marketservice;

import java.util.Collection;
import java.util.List;

import dk.bot.marketobserver.model.Market;
import dk.bot.marketobserver.model.MarketData;

/**
 * Gets the markets from betfair/bwin/...
 * 
 * @author daniel
 * 
 */
public interface MarketService {

	/** Match betting exchange markets with bwin/oddchecker,etc...
	 * 
	 */
	public List<Market> getCompositeMarkets(Collection<MarketData> markets);

}
