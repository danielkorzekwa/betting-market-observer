package dk.bot.marketobserver.model.factory;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dk.bot.betfairservice.model.BFMarketRunner;
import dk.bot.betfairservice.model.BFMarketRunners;
import dk.bot.betfairservice.model.BFRunnerPrice;
import dk.bot.marketobserver.model.MarketRunner;
import dk.bot.marketobserver.model.MarketRunners;
import dk.bot.marketobserver.model.RunnerPrice;

public class MarketRunnersFactoryTest {

	private BFMarketRunners bfMarketRunners;
	
	@Before
	public void setUp() {
		List<BFMarketRunner> marketRunners = new ArrayList<BFMarketRunner>();
		marketRunners.add(createBFMarketRunner(10));
		marketRunners.add(createBFMarketRunner(11));
		bfMarketRunners = new BFMarketRunners(2,marketRunners ,5,new Date(200));
	}
	
	@Test
	public void testCreate() {
		MarketRunners marketRunners = MarketRunnersFactory.create(bfMarketRunners);
		
		/**Check marketRunners*/
		assertEquals(bfMarketRunners.getMarketId(), marketRunners.getMarketId());
		assertEquals(bfMarketRunners.getInPlayDelay(), marketRunners.getInPlayDelay());
		assertEquals(bfMarketRunners.getTimestamp().getTime(), marketRunners.getTimestamp().getTime());
		assertEquals(bfMarketRunners.getMarketRunners().size(), marketRunners.getMarketRunners().size());
		
		/**Check marketRunners list*/
		for(int i=0;i<bfMarketRunners.getMarketRunners().size();i++) {
			BFMarketRunner bfMarketRunner = bfMarketRunners.getMarketRunners().get(i);
			MarketRunner marketRunner = marketRunners.getMarketRunners().get(i);
			
			assertEquals(bfMarketRunner.getSelectionId(),marketRunner.getSelectionId());
			assertEquals(bfMarketRunner.getTotalAmountMatched(),marketRunner.getTotalAmountMatched(),0);
			assertEquals(bfMarketRunner.getLastPriceMatched(),marketRunner.getLastPriceMatched(),0);
			assertEquals(bfMarketRunner.getFarSP(),marketRunner.getFarSP(),0);
			assertEquals(bfMarketRunner.getNearSP(),marketRunner.getNearSP(),0);
			assertEquals(bfMarketRunner.getActualSP(),marketRunner.getActualSP(),0);
			assertEquals(bfMarketRunner.getPrices().size(),marketRunner.getPrices().size());
			
			for(int j=0;j<bfMarketRunner.getPrices().size();j++) {
				BFRunnerPrice bfRunnerPrice = bfMarketRunner.getPrices().get(j);
				RunnerPrice runnerPrice = marketRunner.getPrices().get(j);
				
				assertEquals(bfRunnerPrice.getPrice(),runnerPrice.getPrice(),0);
				assertEquals(bfRunnerPrice.getTotalToBack(),runnerPrice.getTotalToBack(),0);
				assertEquals(bfRunnerPrice.getTotalToLay(),runnerPrice.getTotalToLay(),0);
			}
		}
		
	}

	private BFMarketRunner createBFMarketRunner(int selectionId) {
		List<BFRunnerPrice> prices = new ArrayList<BFRunnerPrice>();
		prices.add(new BFRunnerPrice(3,4,5));
		prices.add(new BFRunnerPrice(6,7,8));
		
		BFMarketRunner bfMarketRunner = new BFMarketRunner(selectionId,20,30,2.1,2.2,2.3,prices);
		return bfMarketRunner;
	}
}
