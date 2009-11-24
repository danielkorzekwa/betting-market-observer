package dk.bot.marketobserver.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.math.util.MathUtils;
import org.junit.Before;
import org.junit.Test;

public class CompleteMarketTest {

	private Market completeMarket;
	
	@Before
	public void setUp() {
	List<MarketRunner> runners = new ArrayList<MarketRunner>();
		
		MarketRunner marketRunner = new MarketRunner(1,1.2,0,0,0,0,new ArrayList<RunnerPrice>());
		runners.add(marketRunner);
		
		marketRunner = new MarketRunner(2,1.5,0,0,0,0,new ArrayList<RunnerPrice>());
		runners.add(marketRunner);
		
		marketRunner = new MarketRunner(3,0.1,0,0,0,0,new ArrayList<RunnerPrice>());
		runners.add(marketRunner);
		
		MarketRunners marketRunners = new MarketRunners(1,runners,0, new Date(0));
		
		completeMarket = new Market(null,null,null,null);
		completeMarket.setMarketRunners(marketRunners);
	}
	
	@Test
	public void testGetMarketTotalAmountMatched() {
		
		assertEquals(2.8, MathUtils.round(completeMarket.getMarketTotalAmountMatched(),2),0);
	}

}
