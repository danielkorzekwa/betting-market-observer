package dk.bot.marketobserver.tasks.bwin;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dk.bot.bwinservice.model.BWinMarket;
import dk.bot.bwinservice.model.BWinSport;
import dk.bot.bwinservice.model.BwinSportEnum;
import dk.bot.marketobserver.model.MarketData;
import dk.bot.marketobserver.model.MarketDetailsRunner;

/**
 * Match BetFair market with BWin market
 * 
 * @author daniel
 * 
 */
public class BwinPriceHelper {

	private static final Log log = LogFactory.getLog(BwinPriceHelper.class.getSimpleName());

	public static BwinMarketPrices getBWinMarketPrices(MarketData market,
			BetFairBwinRegionEnum region, Map<BwinSportEnum, BWinSport> bwinSports) {
		if (region!= null && market.getMarketName().equals("Match Odds")) {

				BWinSport bwinSport = bwinSports.get(region.getBwinRegion().getSport());

				if (bwinSport != null) {
					List<BWinMarket> markets = bwinSport.getMarkets().get(region.getBwinRegion());

					if (markets != null) {

						for (BWinMarket bwinMarket : markets) {

							BwinMarketPrices runnerPrices = convertToBwinMarketPrices(market,bwinMarket);

							if (bwinMarket.getTeams().size() == market.getRunners().size()
									&& bwinMarket.getTeams().size() == runnerPrices.getMarketSelections().size()) {

								Calendar betFairDay = getCalendarDay(market.getEventDate());
								Calendar bWinDay = getCalendarDay(bwinMarket.getMarketTime());
								/** Markets are matched if the market time margin is less or equal than 1 day. */
								if (Math.abs(betFairDay.getTimeInMillis() - bWinDay.getTimeInMillis()) <= 1000 * 3600 * 24) {
									return runnerPrices;
								}
							}
						}
					}
				}
			}
	
		return null; // bwin market not found/matched

	}

	private static BwinMarketPrices convertToBwinMarketPrices(MarketData market,BWinMarket bwinMarket) {
		Map<Integer, Double> runnerPrices = new HashMap<Integer, Double>();

		for (MarketDetailsRunner runner : market.getRunners()) {
			Double price = bwinMarket.getRunnerOdd(runner.getSelectionName());
			if (price != null) {
				runnerPrices.put(runner.getSelectionId(), price);
			}
		}

		return new BwinMarketPrices(market.getMarketId(),runnerPrices);

	}

	/**
	 * Set all fields from HH to Milliseconds to 0.
	 * 
	 * @param date
	 * @return
	 */
	private static Calendar getCalendarDay(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);

		Calendar day = GregorianCalendar.getInstance();
		day.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		day.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		day.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
		day.set(Calendar.HOUR, 0);
		day.set(Calendar.MINUTE, 0);
		day.set(Calendar.MILLISECOND, 0);
		return day;
	}

	private static String formatDay(Calendar cal) {
		String day = "" + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.MONTH) + " " + cal.get(Calendar.YEAR);
		return day;
	}

}
