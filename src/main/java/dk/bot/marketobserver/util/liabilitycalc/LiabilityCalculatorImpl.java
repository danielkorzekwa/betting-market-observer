package dk.bot.marketobserver.util.liabilitycalc;

import java.util.List;
import java.util.Map;

import dk.bot.marketobserver.model.BetStatus;
import dk.bot.marketobserver.model.BetType;
import dk.bot.marketobserver.model.MUBet;

/**
 * Calculates liability based on bets and current probabilities
 * 
 * @author daniel
 * 
 */
public class LiabilityCalculatorImpl implements LiabilityCalculator{

	/**Calculates total liability for all bets based on a given market probabilities
	 * 
	 * @param muBets
	 * @param marketProbabilities
	 * @return
	 */
	public double calculateLiability(List<MUBet> muBets, Map<Integer,MarketProbability> marketProbabilities) {
		double totalLiability=0;
		for(MUBet muBet: muBets) {
			if(muBet.getBetStatus()!=BetStatus.M) {
				continue;
			}
			MarketProbability marketProb = marketProbabilities.get(muBet.getMarketId());
			if(marketProb==null) {
				throw new IllegalArgumentException("No probability available for marketId: " + muBet.getMarketId());
			}
			Double runnerProb = marketProb.getRunnerProbability(muBet.getSelectionId());
			if(runnerProb==null) {
				throw new IllegalArgumentException("No probability available for marketId/selectionId: " + muBet.getMarketId() + "/" + muBet.getSelectionId());
			}
			
			double winnerProfit,loserProfit;
			if(muBet.getBetType()==BetType.B) {
				winnerProfit = muBet.getSize()*(muBet.getPrice()-1);
				loserProfit = -muBet.getSize();
				
			}
			else if(muBet.getBetType()==BetType.L) {
				winnerProfit = -muBet.getSize()*(muBet.getPrice()-1);
				loserProfit = muBet.getSize();
			}
			else {
				throw new IllegalArgumentException("Not support bet type:" + muBet.getBetType());
			}
			double betLiability = winnerProfit*runnerProb + loserProfit*(1-runnerProb);
			totalLiability+=betLiability;
		}
		return totalLiability;
	}

}
