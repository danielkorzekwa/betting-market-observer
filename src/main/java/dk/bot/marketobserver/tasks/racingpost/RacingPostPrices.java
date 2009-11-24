package dk.bot.marketobserver.tasks.racingpost;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import dk.bot.marketobserver.model.MarketDetailsRunner;
import dk.bot.racingpost.model.RacingPostMarket;
import dk.bot.racingpost.model.RacingPostRunner;

/**
 * HR prices from racingpost.com
 * 
 * @author daniel
 * 
 */
public class RacingPostPrices implements Serializable{

	private Collection<MarketDetailsRunner> bfRunners;
	private RacingPostMarket racingPostMarket;

	/** key - bf selectionId, value - decimal price from RacingPost.*/
	private Map<Integer, Double> racingPostPrices = new HashMap<Integer, Double>();
	private final int bfMarketId;
	
	public RacingPostPrices(int bfMarketId) {
		this.bfMarketId = bfMarketId;
	}
	
	public RacingPostPrices(int bfMarketId,Collection<MarketDetailsRunner> bfRunners, RacingPostMarket racingPostMarket) {
		this.bfMarketId = bfMarketId;
		this.bfRunners = bfRunners;
		this.racingPostMarket = racingPostMarket;
		
		calculatePrices();
	}

	private void calculatePrices() {
		for (RacingPostRunner racingPostRunner : racingPostMarket.getMarketRunners()) {
			if (racingPostRunner.getForcastPrice()!=null) {
				for (MarketDetailsRunner bfRunner : bfRunners) {
					if (bfRunner.getSelectionName().toLowerCase().equals(racingPostRunner.getSelectionName().toLowerCase())) {
						racingPostPrices.put(bfRunner.getSelectionId(), racingPostRunner.getForcastPrice());
					}
				}
			}
		}
	}

	/**
	 * 
	 * @return true if racing post prices are available for all betfair runners.
	 */
	public boolean areAllPricesAvailable() {
		return racingPostPrices.size() == bfRunners.size();
	}

	/**Null if not exist*/
	public Double getRacingPostRunnerPrice(int selectionId) {
		return racingPostPrices.get(new Integer(selectionId));
	}
	
	public int getBfMarketId() {
		return bfMarketId;
	}

	@Override
	public boolean equals(Object obj) {
		return bfMarketId == ((RacingPostPrices)obj).getBfMarketId();
	}
	@Override
	public int hashCode() {
		return bfMarketId;
	}

}
