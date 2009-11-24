package dk.bot.marketobserver.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MarketRunnerTest {

	private MarketRunner marketRunner;
	
	@Before
	public void setUp() {
		List<RunnerPrice> prices = new ArrayList<RunnerPrice>();
		
		prices.add(new RunnerPrice(1.01,2,3));
		prices.add(new RunnerPrice(1.02,3,4));
		
		marketRunner = new MarketRunner(1,10,2,2.1,2.2,0,prices);
		
	}
	
	@Test
	public void testGetTotalToBet() {
		assertEquals(12, marketRunner.getTotalToBet(),0);
	}
	
	@Test
	public void testGetAvgPrice() {
		assertEquals(1.0149,marketRunner.getAvgPrice(),0.0001);
	}

}
