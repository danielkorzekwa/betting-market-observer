package dk.bot.marketobserver.util;

import java.util.List;

import dk.bot.marketobserver.model.MarketFilter;
import dk.bot.marketobserver.tasks.bwin.BwinMarketPrices;

/**
 * Check if market is matched by filters.
 * 
 * @author daniel
 * 
 */
public class MarketFilterMatcher {

	/**
	 * @param market
	 *            eventHierarchy
	 * @param marketName
	 * @param bwinPrices
	 * @return True if at least one filter is matched
	 */
	public static boolean match(String eventPath, String marketName, BwinMarketPrices bwinPrices,boolean inPlay,
			List<MarketFilter> marketFilters) {
		boolean matched = false;
		if (marketFilters != null && marketFilters.size() > 0) {
			for (MarketFilter filter : marketFilters) {
				if(!eventPath.startsWith(filter.getMarketPath())) continue;
				if(filter.getMarketName() != null && !marketName.equals(filter.getMarketName())) continue;
				if(filter.isBwinMatched() && bwinPrices == null) continue;
				if(filter.isInPlay() && !inPlay) continue;
				matched=true;
				break;
			}
		} 
		return matched;
	}
	
	/**
	 * @param market
	 *            eventHierarchy
	 * @param marketName
	 * @param bwinPrices
	 * @return True if at least one filter is matched
	 */
	public static boolean match(String eventPath, String marketName,List<MarketFilter> marketFilters) {
		boolean matched = false;
		if (marketFilters != null && marketFilters.size() > 0) {
			for (MarketFilter filter : marketFilters) {
				if(!eventPath.startsWith(filter.getMarketPath())) continue;
				if(filter.getMarketName() != null && !marketName.equals(filter.getMarketName())) continue;
				matched=true;
				break;
			}
		} 
		return matched;
	}
}
