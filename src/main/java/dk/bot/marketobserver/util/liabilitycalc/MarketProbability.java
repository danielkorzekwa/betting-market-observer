package dk.bot.marketobserver.util.liabilitycalc;

import java.util.Map;

/**Represents a market probability that is used by a liability calculator
 * 
 * 
 * @author daniel
 *
 */
public class MarketProbability {
	
	private final Map<Integer, Double> runnerProbabilities;

	/**
	 * 
	 * @param runnerProbabilities Probabilities that a runner is a winner. Key - selectionId
	 */  
	public MarketProbability(Map<Integer,Double> runnerProbabilities) {
		this.runnerProbabilities = runnerProbabilities;
	}
	
	public Double getRunnerProbability(int selectionId) {
		return runnerProbabilities.get(selectionId);
	}
}
