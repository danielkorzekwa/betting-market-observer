package dk.bot.marketobserver.model;

import dk.bot.marketobserver.util.BotException;

/**
 * Defines markets that should be analyzed my market observer.
 *
 * 
 * @author daniel
 * 
 */
public class MarketFilter {

	/**Descriptive name of the filter, e.g. soccer or horse racing*/
	private final String filterName;
	
	/**
	 * The full hierarchy of Event IDs leading to the specified market,
	 * including the final Market Id. Format: /[sportId]/[eventId1]/[eventId2]/.../[eventIdn]/[marketId]
	 */
	private final String marketPath;
	
	/** Filter by market name, e.g. Match Odds*/
	private final String marketName;
	
	/**If true then betfair+bwin market must be matched*/
	private final boolean bwinMatched;
	
	private final boolean inPlay;

	public MarketFilter(String filterName,String marketPath,String marketName,boolean bwinMatched,boolean inPlay) {
		this.filterName = filterName;
		this.marketPath = marketPath;
		this.marketName = marketName;
		this.bwinMatched = bwinMatched;
		this.inPlay=inPlay;
	}
	/**
	 * First eventId from the marketPath
	 * 
	 * @return null if marketPath == null
	 */
	public Integer getEventTypeId() {
		if (marketPath != null) {
			String[] marketPathArray = marketPath.split("/");
			if (marketPathArray.length < 2) {
				throw new BotException("Wrong market path.");

			} else {
				String eventTypeId = marketPathArray[1];
				try {
					return Integer.parseInt(eventTypeId);
				} catch (NumberFormatException e) {
					throw new BotException("Event type id is not a number: " + eventTypeId + ".");
				}
			}
		} else {
			return null;
		}
	}
	
	public String getFilterName() {
		return filterName;
	}
	public String getMarketPath() {
		return marketPath;
	}
	public String getMarketName() {
		return marketName;
	}
	public boolean isBwinMatched() {
		return bwinMatched;
	}
	public boolean isInPlay() {
		return inPlay;
	}
	
	
}
