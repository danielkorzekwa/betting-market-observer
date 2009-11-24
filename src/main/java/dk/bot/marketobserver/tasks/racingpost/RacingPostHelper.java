package dk.bot.marketobserver.tasks.racingpost;

import java.util.Collection;
import java.util.Date;

import dk.bot.marketobserver.model.MarketDetailsRunner;
import dk.bot.racingpost.model.RacingPostMarket;

/**Match BetFair and RacingPost markets.
 * 
 * @author daniel
 *
 */
public class RacingPostHelper {

	/**
	 * 
	 * @return Null if not matched
	 */
	public static RacingPostPrices match(int bfMarketId,String bfMenuPath, Date bfMarketTime,int bfNumOnWinners,Collection<MarketDetailsRunner> bfRunners,Collection<RacingPostMarket> racingPostMarkets) {
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
		
		for(RacingPostMarket racingPostMarket: racingPostMarkets) {
			if(racingPostMarket.getMeetingName().toLowerCase().startsWith(meetingName.toLowerCase()) && racingPostMarket.getMarketTime().getTime()==bfMarketTime.getTime()) {
				RacingPostPrices racingPostPrices = new RacingPostPrices(bfMarketId,bfRunners, racingPostMarket);
				return racingPostPrices;
			}
		}
		
		/**bf and racingpost markets not matched*/
		return null;
	}
}
