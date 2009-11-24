package dk.bot.marketobserver.tasks.bwin;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * BWin prices for Betfair market runner
 * 
 * @author daniel
 * 
 */
public class BwinMarketPrices implements Serializable{

	/**key - betfair runner selectionId, value - bwin price*/
	private Map<Integer, Double> runnerPrices;
	private final int bfMarketId;

	public BwinMarketPrices(int bfMarketId) {
		this.bfMarketId = bfMarketId;
		
	}
	/**
	 * 
	 * @param runnerPrices
	 *            key - betfair runner selectionId, value - bwin price
	 */
	public BwinMarketPrices(int bfMarketId, Map<Integer, Double> runnerPrices) {

		this.bfMarketId = bfMarketId;
		if (runnerPrices == null) {
			throw new IllegalArgumentException("The runnerPrices parameter cannot be null");
		}
		this.runnerPrices = runnerPrices;
	}
	
	public int getBfMarketId() {
		return bfMarketId;
	}



	/**
	 * 
	 * @return market runners (selections ids)
	 */
	public Collection<Integer> getMarketSelections() {
		return runnerPrices.keySet();
	}

	public Double getRunnerPrice(int selectionId) {
		if(runnerPrices!=null) {
		return runnerPrices.get(selectionId);
		}
		else {
			return null;
		}
	}

	public void addPrices(Map<Integer, Double> runnerPrices) {
		this.runnerPrices.putAll(runnerPrices);
	}

	public Map<Integer, Double> getRunnerPrices() {
		return runnerPrices;
	}
	
	@Override
	public boolean equals(Object obj) {
		return bfMarketId == ((BwinMarketPrices)obj).getBfMarketId();
	}
	@Override
	public int hashCode() {
		return bfMarketId;
	}
	
}
