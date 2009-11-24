package dk.bot.marketobserver.model.factory;

import java.util.Date;
import java.util.List;

import dk.bot.betfairservice.model.BFMarketData;
import dk.bot.marketobserver.model.MarketData;
import dk.bot.marketobserver.model.MarketDetailsRunner;


/** Covert BFMarketData to MarketData.
 * 
 * @author daniel
 *
 */
public class MarketDataFactory {

	/** Covert BFMarketData to MarketData.*/
	public static MarketData create(BFMarketData bfMarketData, Date suspendTime,List<MarketDetailsRunner> marketRunners) {

		MarketData marketData = new MarketData();
		marketData.setMarketId(bfMarketData.getMarketId());
		marketData.setMarketName(bfMarketData.getMarketName());
		marketData.setMarketType(bfMarketData.getMarketType());
		marketData.setMarketStatus(bfMarketData.getMarketStatus());
		marketData.setEventDate(bfMarketData.getEventDate());
		marketData.setMenuPath(bfMarketData.getMenuPath());
		marketData.setEventHierarchy(bfMarketData.getEventHierarchy());
		marketData.setCountryCode(bfMarketData.getCountryCode());
		marketData.setNumberOfRunners(bfMarketData.getNumberOfRunners());
		marketData.setNumberOfWinners(bfMarketData.getNumberOfWinners());
		marketData.setBsbMarket(bfMarketData.isBsbMarket());
		marketData.setTurningInPlay(bfMarketData.isTurningInPlay());
		
		marketData.setSuspendTime(suspendTime);
		marketData.setRunners(marketRunners);
		
		return marketData;

	}
}
