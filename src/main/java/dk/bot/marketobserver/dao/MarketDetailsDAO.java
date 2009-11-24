package dk.bot.marketobserver.dao;

import dk.bot.marketobserver.model.MarketData;

/** DAO for Market Details objects
 * 
 * @author daniel
 *
 */
public interface MarketDetailsDAO {

	/**
	 * 
	 * @param marketId
	 * @return null if not found
	 */
	public MarketData findMarketDetails(int marketId);
	
	/**Replace if already exist
	 * 
	 * @param marketDetails
	 */
	public void saveMarketDetails(MarketData marketDetails);
	
}
