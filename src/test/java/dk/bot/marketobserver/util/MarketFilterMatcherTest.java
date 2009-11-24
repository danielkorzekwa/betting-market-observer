package dk.bot.marketobserver.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import dk.bot.marketobserver.model.MarketFilter;
import dk.bot.marketobserver.tasks.bwin.BwinMarketPrices;

public class MarketFilterMatcherTest {

	private MarketFilterMatcher matched = new MarketFilterMatcher();
	
	List<MarketFilter> marketFilters = new ArrayList<MarketFilter>();
	
	@Test
	public void testMatchMarketPathMatched() {
		marketFilters.add(new MarketFilter("filterName","/1/",null,false,false));
		
		assertEquals(true,matched.match("/1/43245", "Match Odds", null,false,marketFilters));
	}
	
	@Test
	public void testMatchMarkePathtNotMatched() {
		marketFilters.add(new MarketFilter("filterName","/2/",null,false,false));
		
		assertEquals(false,matched.match("/1/43245", "Match Odds", null,false,marketFilters));
	}
	
	@Test
	public void testMatchMarketNameMatched() {
		marketFilters.add(new MarketFilter("filterName","/1/","Match Odds",false,false));
		
		assertEquals(true,matched.match("/1/43245", "Match Odds", null,false,marketFilters));
	}
	
	@Test
	public void testMatchMarketNameNotMatched() {
		marketFilters.add(new MarketFilter("filterName","/1/","Winner",false,false));
		
		assertEquals(false,matched.match("/1/43245", "Match Odds", null,false,marketFilters));
	}
	
	@Test
	public void testMatchBwinMatched() {
		marketFilters.add(new MarketFilter("filterName","/1/","Match Odds", true,false));
		
		assertEquals(true,matched.match("/1/43245", "Match Odds", new BwinMarketPrices(1,new HashMap<Integer, Double>()),false,marketFilters));
	}
	
	@Test
	public void testMatchBwinNotMatched() {
		marketFilters.add(new MarketFilter("filterName","/1/","Match Odds", true,false));
		
		assertEquals(false,matched.match("/1/43245", "Match Odds", null,false,marketFilters));
	}
	
	@Test
	public void testMatchFiltersNullNotMatched() {
		
		assertEquals(false,matched.match("/1/43245", "Match Odds", new BwinMarketPrices(1,new HashMap<Integer, Double>()),false,null));
	}
	
	@Test
	public void testMatchFiltersEmptyNotMatched() {
		
		assertEquals(false,matched.match("/1/43245", "Match Odds", new BwinMarketPrices(1,new HashMap<Integer, Double>()),false,marketFilters));
	}

}
