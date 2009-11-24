package dk.bot.marketobserver.model.factory;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dk.bot.betfairservice.model.BFMarketDetailsRunner;
import dk.bot.marketobserver.model.MarketDetailsRunner;

public class MarketDetailsRunnerFactoryTest {

	private List<BFMarketDetailsRunner> marketRunners;
	
	@Before
	public void setUp() {
		marketRunners = new ArrayList<BFMarketDetailsRunner>();
		marketRunners.add(new BFMarketDetailsRunner(234,"Arsenal"));
		marketRunners.add(new BFMarketDetailsRunner(653,"Wigan"));
		
	}
	
	@Test
	public void testCreate() {
		List<MarketDetailsRunner> marketDetailRunners = MarketDetailsRunnerFactory.create(marketRunners);
		assertEquals(marketRunners.size(), marketDetailRunners.size());
		
		for(int i=0;i<marketDetailRunners.size();i++) {
			BFMarketDetailsRunner bfRunnerDetails = marketRunners.get(i);
			MarketDetailsRunner runnerDetails = marketDetailRunners.get(i);
			
			assertEquals(bfRunnerDetails.getSelectionId(),runnerDetails.getSelectionId());
			assertEquals(bfRunnerDetails.getSelectionName(),runnerDetails.getSelectionName());
		}
		
	}
	
}