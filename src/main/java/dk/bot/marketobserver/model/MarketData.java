package dk.bot.marketobserver.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class MarketData implements Serializable{

	private int marketId;
	
	private String marketName;
	
	private String marketType;
	
	private String marketStatus;
	
	private Date eventDate;
	private Date suspendTime;
	
	private String menuPath;
	
	private String eventHierarchy;
	
	private String countryCode;
	
	private int numberOfRunners;
	
	private int numberOfWinners;
	
	private boolean bsbMarket;
	
	private boolean turningInPlay;
	
	private List<MarketDetailsRunner> runners;
	
	public MarketData() {
	}
	
	public List<MarketDetailsRunner> getRunners() {
		return runners;
	}



	public void setRunners(List<MarketDetailsRunner> runners) {
		this.runners = runners;
	}



	public Date getSuspendTime() {
		return suspendTime;
	}



	public void setSuspendTime(Date suspendTime) {
		this.suspendTime = suspendTime;
	}



	public MarketData(int marketId) {
		this.marketId = marketId;
	}
	
	public int getMarketId() {
		return marketId;
	}

	public void setMarketId(int marketId) {
		this.marketId = marketId;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	public String getMarketStatus() {
		return marketStatus;
	}

	public void setMarketStatus(String marketStatus) {
		this.marketStatus = marketStatus;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getMenuPath() {
		return menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

	public String getEventHierarchy() {
		return eventHierarchy;
	}

	public void setEventHierarchy(String eventHierarchy) {
		this.eventHierarchy = eventHierarchy;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public int getNumberOfRunners() {
		return numberOfRunners;
	}

	public void setNumberOfRunners(int numberOfRunners) {
		this.numberOfRunners = numberOfRunners;
	}

	public int getNumberOfWinners() {
		return numberOfWinners;
	}

	public void setNumberOfWinners(int numberOfWinners) {
		this.numberOfWinners = numberOfWinners;
	}

	public boolean isBsbMarket() {
		return bsbMarket;
	}

	public void setBsbMarket(boolean bsbMarket) {
		this.bsbMarket = bsbMarket;
	}

	public boolean isTurningInPlay() {
		return turningInPlay;
	}

	public void setTurningInPlay(boolean turningInPlay) {
		this.turningInPlay = turningInPlay;
	}
	
	public String getEventName() {
		String[] split = this.menuPath.split("\\\\");
		return split[split.length-1];
	}
	
	/**
	 * 
	 * @param selectionId
	 * @return Null if runner not found for selectionId
	 */
	public String getSelectionName(int selectionId) {
		for(MarketDetailsRunner runner: runners) {
			if(selectionId == runner.getSelectionId()) {
				return runner.getSelectionName();
			}
		}
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		return marketId == ((MarketData)obj).getMarketId();
	}
	@Override
	public int hashCode() {
		return marketId;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this).toString();
	}
		
}
