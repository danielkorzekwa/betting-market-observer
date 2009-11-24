package dk.bot.marketobserver.tasks.adrianmassey;

import java.util.Collection;
import java.util.Date;

import dk.bot.adrianmassey.model.MasseyHRMarket;
import dk.bot.marketobserver.model.MarketDetailsRunner;

/**Match BetFair and Massey markets.
 * 
 * @author daniel
 *
 */
public class MasseyHelper {

	/**
	 * 
	 * @return Null if not matched
	 */
	public static MasseyPrices match(int bfMarketId,String bfMenuPath, Date bfMarketTime,int bfNumOnWinners,Collection<MarketDetailsRunner> bfRunners,Collection<MasseyHRMarket> massayMarkets) {
		/** Betfair eventName always in format: Sand 5th Dec */
		String[] split = bfMenuPath.split("\\\\");
		String eventName = split[split.length-1];
		String[] eventNameSplit = eventName.split(" ");
		if (eventNameSplit.length != 3) {
			return null;
		}
		String meetingName = eventNameSplit[0];

		/** Betfair menuPath always in format: \Horse Racing\GB\Sand 5th Dec */
		if (bfMenuPath.split("\\\\").length != 4) {
			return null;
		}

		/** compare only win market */
		if (bfNumOnWinners != 1) {
			return null;
		}
		
		for(MasseyHRMarket masseyMarket: massayMarkets) {
			if(masseyMarket.getMeetingName().toLowerCase().startsWith(meetingName.toLowerCase()) && masseyMarket.getMarketTime().getTime()==bfMarketTime.getTime()) {
				MasseyPrices masseyPrices = new MasseyPrices(bfMarketId,bfRunners, masseyMarket);
				return masseyPrices;
			}
		}
		
		/**bf and massey markets not matched*/
		return null;
	}
}
