package dk.bot.marketobserver.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Composite class for market runners and inPlayDelay
 * 
 * @author daniel
 * 
 */
public class MarketRunners implements Serializable {

	private final int marketId;
	private final List<MarketRunner> marketRunners;
	private final int inPlayDelay;

	/** The time at which the response was received from the betfair server. */
	private final Date timestamp;

	public MarketRunners(int marketId, List<MarketRunner> marketRunners, int inPlayDelay, Date timestamp) {
		this.marketId = marketId;
		this.marketRunners = marketRunners;
		this.inPlayDelay = inPlayDelay;
		this.timestamp = timestamp;
	}

	public int getMarketId() {
		return marketId;
	}

	public List<MarketRunner> getMarketRunners() {
		return marketRunners;
	}

	public int getInPlayDelay() {
		return inPlayDelay;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * 
	 * @param selectionId
	 * @return null if runner doesn't exist for selectionId
	 */
	public MarketRunner getMarketRunner(int selectionId) {
		for (MarketRunner runner : marketRunners) {
			if (runner.getSelectionId() == selectionId) {
				return runner;
			}
		}
		return null;
	}

	/** Returns amount of all offers to back and lay. */
	public double getTotalToBet() {
		double total = 0;

		for (MarketRunner runner : marketRunners) {
			total = total + runner.getTotalToBet();
		}
		return total;
	}

	/**
	 * return value = runnerPrice * sum of probs for all runners on market
	 * 
	 * @param runnerPrice
	 * @param marketRunners
	 * @return
	 */
	public double getRunnerPriceToBackWeighted(int selectionId) {

		MarketRunner runner = getMarketRunner(selectionId);
		if (runner != null) {

			double totalProb = 0;
			for (MarketRunner marketRunner : marketRunners) {
				totalProb = totalProb + (1 / marketRunner.getPriceToBack());
			}

			return runner.getPriceToBack() * totalProb;

		} else {
			throw new RuntimeException("Market runner not found for marketId/selectionId:" + this.marketId + "/"
					+ selectionId);
		}

	}

}
