package dk.bot.marketobserver.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import dk.bot.betfairservice.model.BFMarketData;
import dk.bot.oddschecker.model.HorseWinMarket;

public class OddsCheckerHelperTest {

	@Test
	public void testMatchMarketMatched1() {
		Date now = new Date(System.currentTimeMillis());

		BFMarketData market = new BFMarketData();
		market.setMenuPath("\\Horse Racing\\GB\\Sand 5th Dec");
		market.setNumberOfWinners(1);
		market.setEventDate(now);

		List<HorseWinMarket> oddsCheckerHorseWinMarkets = new ArrayList<HorseWinMarket>();
		HorseWinMarket horseWinMarket = new HorseWinMarket();
		horseWinMarket.setMeetingName("sandown");
		horseWinMarket.setMarketTime(now);
		oddsCheckerHorseWinMarkets.add(horseWinMarket);

		HorseWinMarket oddsCheckerMarket = OddsCheckerHelper.matchMarket(market, oddsCheckerHorseWinMarkets);

		assertNotNull(oddsCheckerMarket);
	}

	@Test
	public void testMatchMarketNotMatchedEventName() {
		Date now = new Date(System.currentTimeMillis());

		BFMarketData market = new BFMarketData();
		market.setMenuPath("\\Horse Racing\\GB\\Reverse Forecast\\Sand (F/C) 5th Dec");
		market.setEventDate(now);

		List<HorseWinMarket> oddsCheckerHorseWinMarkets = new ArrayList<HorseWinMarket>();
		HorseWinMarket horseWinMarket = new HorseWinMarket();
		horseWinMarket.setMeetingName("sandown");
		horseWinMarket.setMarketTime(now);
		oddsCheckerHorseWinMarkets.add(horseWinMarket);

		HorseWinMarket oddsCheckerMarket = OddsCheckerHelper.matchMarket(market, oddsCheckerHorseWinMarkets);

		assertNull(oddsCheckerMarket);
	}

	@Test
	public void testMatchMarketNotMatchedMenuPath() {
		Date now = new Date(System.currentTimeMillis());

		BFMarketData market = new BFMarketData();
		market.setMenuPath("\\Horse Racing\\GB\\Sand (F/C) 5th Dec");
		market.setEventDate(now);

		List<HorseWinMarket> oddsCheckerHorseWinMarkets = new ArrayList<HorseWinMarket>();
		HorseWinMarket horseWinMarket = new HorseWinMarket();
		horseWinMarket.setMeetingName("sandown");
		horseWinMarket.setMarketTime(now);
		oddsCheckerHorseWinMarkets.add(horseWinMarket);

		HorseWinMarket oddsCheckerMarket = OddsCheckerHelper.matchMarket(market, oddsCheckerHorseWinMarkets);

		assertNull(oddsCheckerMarket);
	}

	@Test
	public void testMatchMarketNotMatchedMarketTime() {
		Date now = new Date(System.currentTimeMillis());

		BFMarketData market = new BFMarketData();
		market.setMenuPath("\\Horse Racing\\GB\\Sand 5th Dec");
		market.setNumberOfWinners(1);
		market.setEventDate(now);

		List<HorseWinMarket> oddsCheckerHorseWinMarkets = new ArrayList<HorseWinMarket>();
		HorseWinMarket horseWinMarket = new HorseWinMarket();
		horseWinMarket.setMeetingName("sandown");
		horseWinMarket.setMarketTime(new Date(now.getTime() + (1000l * 3600 * 24)));
		oddsCheckerHorseWinMarkets.add(horseWinMarket);

		HorseWinMarket oddsCheckerMarket = OddsCheckerHelper.matchMarket(market, oddsCheckerHorseWinMarkets);

		assertNull(oddsCheckerMarket);
	}

	@Test
	public void testMatchMarketNotMatchedNumOfWinners() {
		Date now = new Date(System.currentTimeMillis());

		BFMarketData market = new BFMarketData();
		market.setMenuPath("\\Horse Racing\\GB\\Sand 5th Dec");
		market.setNumberOfWinners(2);
		market.setEventDate(now);

		List<HorseWinMarket> oddsCheckerHorseWinMarkets = new ArrayList<HorseWinMarket>();
		HorseWinMarket horseWinMarket = new HorseWinMarket();
		horseWinMarket.setMeetingName("sandown");
		horseWinMarket.setMarketTime(now);
		oddsCheckerHorseWinMarkets.add(horseWinMarket);

		HorseWinMarket oddsCheckerMarket = OddsCheckerHelper.matchMarket(market, oddsCheckerHorseWinMarkets);

		assertNull(oddsCheckerMarket);
	}

}
