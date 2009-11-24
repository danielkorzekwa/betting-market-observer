package dk.bot.marketobserver.model.factory;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import dk.bot.betfairservice.model.BFMarketData;
import dk.bot.marketobserver.model.MarketData;

public class MarketDataFactoryTest {

	private BFMarketData bfMarketData;

	@Before
	public void setUp() {
		bfMarketData = new BFMarketData();
		bfMarketData.setMarketId(123665333);
		bfMarketData.setMarketName("Match Odds");
		bfMarketData.setMarketType("O");
		bfMarketData.setMarketStatus("ACTIVE");
		bfMarketData.setEventDate(new Date(100));
		bfMarketData.setMenuPath("soccer/uk/MatUtd");
		bfMarketData.setEventHierarchy("/1/344555/33");
		bfMarketData.setBetDelay("5");
		bfMarketData.setExchangeId(1);
		bfMarketData.setCountryCode("de");
		bfMarketData.setLastRefresh(new Date(34556565));
		bfMarketData.setNumberOfRunners(13);
		bfMarketData.setNumberOfWinners(3);
		bfMarketData.setTotalAmountMatched(34000);
		bfMarketData.setBsbMarket(true);
		bfMarketData.setTurningInPlay(true);
	}

	@Test
	public void testCreate() {
		MarketData marketData = MarketDataFactory.create(bfMarketData,null,null);
		
		assertEquals(bfMarketData.getMarketId(), marketData.getMarketId());
		assertEquals(bfMarketData.getMarketName(), marketData.getMarketName());
		assertEquals(bfMarketData.getMarketType(), marketData.getMarketType());
		assertEquals(bfMarketData.getMarketStatus(), marketData.getMarketStatus());
		assertEquals(bfMarketData.getEventDate(), marketData.getEventDate());
		assertEquals(bfMarketData.getMenuPath(), marketData.getMenuPath());
		assertEquals(bfMarketData.getEventHierarchy(), marketData.getEventHierarchy());
		assertEquals(bfMarketData.getCountryCode(), marketData.getCountryCode());
		assertEquals(bfMarketData.getNumberOfRunners(), marketData.getNumberOfRunners());
		assertEquals(bfMarketData.getNumberOfWinners(), marketData.getNumberOfWinners());
		assertEquals(bfMarketData.isBsbMarket(), marketData.isBsbMarket());
		assertEquals(bfMarketData.isTurningInPlay(), marketData.isTurningInPlay());

	}

}
