package dk.bot.marketobserver.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import dk.bot.marketobserver.tasks.adrianmassey.MasseyPrices;
import dk.bot.marketobserver.tasks.bwin.BetFairBwinRegionEnum;
import dk.bot.marketobserver.tasks.bwin.BwinMarketPrices;
import dk.bot.marketobserver.tasks.racingpost.RacingPostPrices;
import dk.bot.oddschecker.model.HorseWinMarket;

/**
 * Domain model for betfair/bwin/... market.
 * 
 * @author daniel
 * 
 */
public class Market implements Serializable {

	private final MarketData marketData;
	
	private MarketRunners marketRunners;
	private List<MUBet> marketMUBets;
	
	private final BwinMarketPrices bwinPrices;
	private final HorseWinMarket horseWinMarket;
	private final BetFairBwinRegionEnum region;
	private MasseyPrices masseyPrices;
	private RacingPostPrices racingPostPrices;

	public Market(MarketData marketData, BetFairBwinRegionEnum region,
			BwinMarketPrices bwinPrices, HorseWinMarket horseWinMarket) {
		this.marketData = marketData;
		this.region = region;
		this.bwinPrices = bwinPrices;
		this.horseWinMarket = horseWinMarket;
	}
	
	public MarketRunners getMarketRunners() {
		return marketRunners;
	}
	
	public void setMarketRunners(MarketRunners marketRunners) {
		this.marketRunners = marketRunners;
	}
	
	public List<MUBet> getMarketMUBets() {
		return marketMUBets;
	}



	public void setMarketMUBets(List<MUBet> marketMUBets) {
		this.marketMUBets = marketMUBets;
	}



	public RacingPostPrices getRacingPostPrices() {
		return racingPostPrices;
	}



	public void setRacingPostPrices(RacingPostPrices racingPostPrices) {
		this.racingPostPrices = racingPostPrices;
	}



	public MasseyPrices getMasseyPrices() {
		return masseyPrices;
	}

	public void setMasseyPrices(MasseyPrices masseyPrices) {
		this.masseyPrices = masseyPrices;
	}

	public MarketData getMarketData() {
		return marketData;
	}

	public BwinMarketPrices getBwinPrices() {
		return bwinPrices;
	}

	public HorseWinMarket getHorseWinMarket() {
		return horseWinMarket;
	}

	public BetFairBwinRegionEnum getRegion() {
		return region;
	}

	/** Returns market name as displayed in account statement and bets module. */
	public String getFullMarketName() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

		String fullMarketName;
		String[] menuPathArray = getMarketData().getMenuPath().split("\\\\");
		if (menuPathArray.length >= 4) {
			fullMarketName = menuPathArray[2] + " / " + menuPathArray[menuPathArray.length - 1] + "/ "
					+ simpleDateFormat.format(getMarketData().getEventDate()) + " "
					+ getMarketData().getMarketName();
		} else {
			fullMarketName = getMarketData().getMenuPath() + "/ " + getMarketData().getMarketName();
		}

		return fullMarketName;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this).toString();
	}
	
	/**
	 * 
	 * @return total amount matched on market
	 */
	public double getMarketTotalAmountMatched() {
		if(marketRunners==null) {
			throw new IllegalStateException("Market runners are not set.");
		}
		
		double total = 0;

		for (MarketRunner runner : marketRunners.getMarketRunners()) {
			total = total + runner.getTotalAmountMatched();
		}

		return total;
	}
}
