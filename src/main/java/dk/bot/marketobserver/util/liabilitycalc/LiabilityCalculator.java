package dk.bot.marketobserver.util.liabilitycalc;

import java.util.List;
import java.util.Map;

import dk.bot.marketobserver.model.MUBet;

/**
 * Calculates liability based on bets and current probabilities
 * 
 * @author daniel
 * 
 */
public interface LiabilityCalculator {

	/**Calculates total liability for all bets based on a given market probabilities
	 * 
	 * @param muBets
	 * @param marketProbabilities key - marketId
	 * @return
	 */
	public double calculateLiability(List<MUBet> muBets, Map<Integer,MarketProbability> marketProbabilities);
}
