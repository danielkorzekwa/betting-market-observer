package dk.bot.marketobserver.cache.completemarkets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import dk.bot.marketobserver.model.Market;
import dk.bot.marketobserver.model.MarketRunner;
import dk.bot.marketobserver.model.MarketRunners;
import dk.bot.marketobserver.model.RunnerPrice;

public class CompleteMarketsCacheImplTest {

	private CompleteMarketsCacheImpl cache = new CompleteMarketsCacheImpl();
	private Map<Integer, Market> compositeMarketsMap = new HashMap<Integer, Market>();
	
	@Before
	public void setUp() {
		MarketRunners marketRunners = new MarketRunners(1, new ArrayList<MarketRunner>(),5, new Date(100));
		Market market = new Market(null,null,null,null);
		market.setMarketRunners(marketRunners);
		compositeMarketsMap.put(1, market);
		
		marketRunners = new MarketRunners(1, new ArrayList<MarketRunner>(),5, new Date(200));
		market = new Market(null,null,null,null);
		market.setMarketRunners(marketRunners);
		compositeMarketsMap.put(2, market);
		
		cache.updateCompleteMarkets(compositeMarketsMap);
	}

	@Test
	public void testGetCompleteMarket() {

		MarketRunners runners = cache.getCompleteMarket(1).getMarketRunners();
		assertEquals(100, runners.getTimestamp().getTime());
		
		runners = cache.getCompleteMarket(2).getMarketRunners();
		assertEquals(200, runners.getTimestamp().getTime());
		
		assertNull(cache.getCompleteMarket(3));
	}
	
	@Test
	public void testUpdateCompleteMarkets() {

		HashMap<Integer,Market> map = new HashMap<Integer, Market>();
		MarketRunners marketRunners = new MarketRunners(1, new ArrayList<MarketRunner>(),5, new Date(300));
		Market market= new Market(null,null,null,null);
		market.setMarketRunners(marketRunners);
		map.put(2, market);
		
		marketRunners = new MarketRunners(1, new ArrayList<MarketRunner>(),5, new Date(400));
		market= new Market(null,null,null,null);
		market.setMarketRunners(marketRunners);
		map.put(3, market);
		
		cache.updateCompleteMarkets(map);
		
		MarketRunners runners = cache.getCompleteMarket(2).getMarketRunners();
		assertEquals(300, runners.getTimestamp().getTime());
		
		runners = cache.getCompleteMarket(3).getMarketRunners();
		assertEquals(400, runners.getTimestamp().getTime());

	}
	
	/**Check last best prices before market is turned in play.*/
	@Test
	public void testUpdateCompleteMarketsDoNotLoseLastBestPrices() {
		HashMap<Integer,Market> map = new HashMap<Integer, Market>();
		
		MarketRunners marketRunners = new MarketRunners(1, new ArrayList<MarketRunner>(),5, new Date(100));
		MarketRunner marketRunner = new MarketRunner(1,0,0,0,0,0,new ArrayList<RunnerPrice>());
		marketRunner.getPrices().add(new RunnerPrice(2,10,0));
		marketRunner.getPrices().add(new RunnerPrice(2.1,0,10));
		marketRunners.getMarketRunners().add(marketRunner);
		
		Market market= new Market(null,null,null,null);
		market.setMarketRunners(marketRunners);
		map.put(2, market);
		cache.updateCompleteMarkets(map);
		
		MarketRunner runner = cache.getCompleteMarket(2).getMarketRunners().getMarketRunners().get(0);
		assertEquals(0, runner.getLastPriceToBack(),0);
		assertEquals(0, runner.getLastPriceToBack(),0);
		
		/**Update complete market in play - last best prices shouldn't be changed.*/
		marketRunners = new MarketRunners(1, new ArrayList<MarketRunner>(),5, new Date(100));
		marketRunner = new MarketRunner(1,0,0,0,0,0,new ArrayList<RunnerPrice>());
		marketRunner.getPrices().add(new RunnerPrice(1.2,10,0));
		marketRunner.getPrices().add(new RunnerPrice(1.3,0,10));
		marketRunners.getMarketRunners().add(marketRunner);
		
		market= new Market(null,null,null,null);
		market.setMarketRunners(marketRunners);
		map.put(2, market);
		cache.updateCompleteMarkets(map);
		
		runner = cache.getCompleteMarket(2).getMarketRunners().getMarketRunners().get(0);
		assertEquals(2, runner.getLastPriceToBack(),0);
		assertEquals(2.1, runner.getLastPriceToLay(),0);
		
		/**Update again complete market in play - last best prices shouldn't be changed.*/
		marketRunners = new MarketRunners(1, new ArrayList<MarketRunner>(),5, new Date(100));
		marketRunner = new MarketRunner(1,0,0,0,0,0,new ArrayList<RunnerPrice>());
		marketRunner.getPrices().add(new RunnerPrice(1.1,10,0));
		marketRunner.getPrices().add(new RunnerPrice(1.11,0,10));
		marketRunners.getMarketRunners().add(marketRunner);
		
		market= new Market(null,null,null,null);
		market.setMarketRunners(marketRunners);
		map.put(2, market);
		cache.updateCompleteMarkets(map);
		
		runner = cache.getCompleteMarket(2).getMarketRunners().getMarketRunners().get(0);
		assertEquals(2, runner.getLastPriceToBack(),0);
		assertEquals(2.1, runner.getLastPriceToLay(),0);
		
		
	}
	
	@Test
	public void testUpdateCompleteMarketsDoNotClearStartingPrices() {

		HashMap<Integer,Market> map = new HashMap<Integer, Market>();
		MarketRunners marketRunners = new MarketRunners(1, new ArrayList<MarketRunner>(),5, new Date(100));
		MarketRunner marketRunner = new MarketRunner(1,0,0,0,0,0,new ArrayList<RunnerPrice>());
		marketRunner.setNearSP(2);
		marketRunner.setFarSP(2.1);
		marketRunner.setActualSP(0);
		marketRunners.getMarketRunners().add(marketRunner);
		
		Market market= new Market(null,null,null,null);
		market.setMarketRunners(marketRunners);
		map.put(2, market);
		cache.updateCompleteMarkets(map);
		
		MarketRunner runner = cache.getCompleteMarket(2).getMarketRunners().getMarketRunners().get(0);
		assertEquals(2, runner.getNearSP(),0);
		assertEquals(2.1, runner.getFarSP(),0);
		assertEquals(0, runner.getActualSP(),0);
		
		/**Update actualSP*/
		marketRunners = new MarketRunners(1, new ArrayList<MarketRunner>(),5, new Date(100));
		marketRunner = new MarketRunner(1,0,0,0,0,0,new ArrayList<RunnerPrice>());
		marketRunner.setNearSP(0);
		marketRunner.setFarSP(0);
		marketRunner.setActualSP(2.2);
		marketRunners.getMarketRunners().add(marketRunner);
		
		market= new Market(null,null,null,null);
		market.setMarketRunners(marketRunners);
		map.put(2, market);
		cache.updateCompleteMarkets(map);
	
		runner = cache.getCompleteMarket(2).getMarketRunners().getMarketRunners().get(0);
		assertEquals(2, runner.getNearSP(),0);
		assertEquals(2.1, runner.getFarSP(),0);
		assertEquals(2.2, runner.getActualSP(),0);
		
	}
	
	@Test
	public void testGetCompleteMarkets() {
		Map<Integer, Market> markets = cache.getCompleteMarkets();
		
		assertEquals(2, markets.size());
		
		assertEquals(100, markets.get(1).getMarketRunners().getTimestamp().getTime());
		assertEquals(200, markets.get(2).getMarketRunners().getTimestamp().getTime());	
	}
	
	@Test
	public void testGetMarketsAmount() {
	assertEquals(2,cache.getMarketsAmount());
	}

}
