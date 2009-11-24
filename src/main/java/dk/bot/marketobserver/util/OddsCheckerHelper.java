package dk.bot.marketobserver.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dk.bot.betfairservice.model.BFMarketData;
import dk.bot.oddschecker.model.HorseWinMarket;

/**
 * Match BetFair market with OddsChecker horse win market
 * 
 * @author daniel
 * 
 */
public class OddsCheckerHelper {

	private static final Log log = LogFactory.getLog(OddsCheckerHelper.class.getSimpleName());
	
	/**
	 * 
	 * @param market
	 * @param marketDetails
	 * @param oddsCheckerHorseWinMarkets
	 * @return null if not matched
	 */
	public static HorseWinMarket matchMarket(BFMarketData market, List<HorseWinMarket> oddsCheckerHorseWinMarkets) {

		String[] eventNameSplit = market.getEventName().split(" ");
		/** Betfair eventName always in format: Sand 5th Dec */
		if (eventNameSplit.length != 3) {
			return null;
		}
		String meetingName = eventNameSplit[0];

		/** Betfair menuPath always in format: \Horse Racing\GB\Sand 5th Dec */
		if (market.getMenuPath().split("\\\\").length != 4) {
			return null;
		}

		/** compare only win market */
		if (market.getNumberOfWinners() != 1) {
			return null;
		}

		meetingName = convertMeetingName(meetingName);

		for (HorseWinMarket horseWinMarket : oddsCheckerHorseWinMarkets) {
			/** compare meetingName and meetingTime */
			if (horseWinMarket.getMeetingName().toLowerCase().startsWith(meetingName.toLowerCase())
					&& compareMeetingTimes(market.getEventDate(), horseWinMarket.getMarketTime())) {
			//	System.out.println(market.getEventName() + ":" + market.getEventDate() + ":"
			//			+ horseWinMarket.getMeetingName() + ":" + horseWinMarket.getMarketTime());
				return horseWinMarket;
			}
		}

		if (market.getEventDate().getDay() == new Date(System.currentTimeMillis()).getDay()) {
			log.info("BetFair to OddChecher not matched: " + meetingName + ":" + market.getEventDate());
		}

		return null;
	}

	/**
	 * Compare yyyy:mm:dd hh:mm
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	private static boolean compareMeetingTimes(Date time1, Date time2) {
		Calendar cal1 = GregorianCalendar.getInstance();
		cal1.setTime(time1);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);

		Calendar cal2 = GregorianCalendar.getInstance();
		cal2.setTime(time2);
		cal2.set(Calendar.SECOND, 0);
		cal2.set(Calendar.MILLISECOND, 0);

		return cal1.getTimeInMillis() == cal2.getTimeInMillis();

	}

	private static String convertMeetingName(String meetingName) {
		if (meetingName.toLowerCase().equals("extr")) {
			return "Exeter";
		}
		else if(meetingName.toLowerCase().equals("glghs")) {
			return "great-leighs";
		}
		else if(meetingName.toLowerCase().equals("sthl")) {
			return "southwell";
		}
		return meetingName;
	}
}
