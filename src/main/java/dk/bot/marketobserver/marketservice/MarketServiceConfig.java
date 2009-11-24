package dk.bot.marketobserver.marketservice;

import java.util.List;

import dk.bot.marketobserver.model.MarketFilter;

/** Configuration data for the market service.
 * 
 * @author daniel
 *
 */
public class MarketServiceConfig {

	private int startInHoursFrom;
	private int startInHoursTo;
	private int exchangeId;
	private String marketStatus;
	private String marketType;

	private List<MarketFilter> marketFilters;

	public int getStartInHoursFrom() {
		return startInHoursFrom;
	}

	public void setStartInHoursFrom(int startInHoursFrom) {
		this.startInHoursFrom = startInHoursFrom;
	}

	public int getStartInHoursTo() {
		return startInHoursTo;
	}

	public void setStartInHoursTo(int startInHoursTo) {
		this.startInHoursTo = startInHoursTo;
	}

	public int getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(int exchangeId) {
		this.exchangeId = exchangeId;
	}

	public String getMarketStatus() {
		return marketStatus;
	}

	public void setMarketStatus(String marketStatus) {
		this.marketStatus = marketStatus;
	}

	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	public List<MarketFilter> getMarketFilters() {
		return marketFilters;
	}

	public void setMarketFilters(List<MarketFilter> marketFilters) {
		this.marketFilters = marketFilters;
	}
	
}
