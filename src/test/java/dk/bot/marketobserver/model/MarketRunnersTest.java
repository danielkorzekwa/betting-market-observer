package dk.bot.marketobserver.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MarketRunnersTest {

	private MarketRunners marketRunners;

	@Before
	public void setUp() {
		List<MarketRunner> runners = new ArrayList<MarketRunner>();

		MarketRunner marketRunner;
		List<RunnerPrice> prices = new ArrayList<RunnerPrice>();
		prices.add(new RunnerPrice(1.4, 2, 0));
		marketRunner = new MarketRunner(1,0,0,0,0,0,prices);
		runners.add(marketRunner);
		
		prices = new ArrayList<RunnerPrice>();
		prices.add(new RunnerPrice(1.63, 2, 0));
		marketRunner = new MarketRunner(2,0,0,0,0,0,prices);
		runners.add(marketRunner);
		
		prices = new ArrayList<RunnerPrice>();
		prices.add(new RunnerPrice(1.11, 2, 0));
		marketRunner = new MarketRunner(3,0,0,0,0,0,prices);
		runners.add(marketRunner);
		
		prices = new ArrayList<RunnerPrice>();
		prices.add(new RunnerPrice(1.11, 2, 0));
		marketRunner = new MarketRunner(4,0,0,0,0,0,prices);
		runners.add(marketRunner);
		
		prices = new ArrayList<RunnerPrice>();
		prices.add(new RunnerPrice(1.11, 2, 0));
		marketRunner = new MarketRunner(5,0,0,0,0,0,prices);
		runners.add(marketRunner);
		
		marketRunners = new MarketRunners(1,runners,5, new Date(100));		
	}

	@Test
	public void testGetRunnerPriceToBackWeighted() {
		assertEquals(6.569,marketRunners.getRunnerPriceToBackWeighted(2),0.001);
	}

}
