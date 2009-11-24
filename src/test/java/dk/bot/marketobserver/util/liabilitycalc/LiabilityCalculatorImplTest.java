package dk.bot.marketobserver.util.liabilitycalc;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import dk.bot.marketobserver.model.BetStatus;
import dk.bot.marketobserver.model.BetType;
import dk.bot.marketobserver.model.MUBet;

public class LiabilityCalculatorImplTest {

	private LiabilityCalculator calc = new LiabilityCalculatorImpl();
	
	private List<MUBet> muBets = new ArrayList<MUBet>();
	private Map<Integer,MarketProbability> marketProbs = new HashMap<Integer, MarketProbability>();
	
	@Test
	public void testCalculateLiabilityNoProbChangedLiabilityIsZero() {
		muBets.add(createBet(1,1,BetType.B,2, 3));
			
		Map<Integer, Double> runnerProbs = new HashMap<Integer, Double>();
		runnerProbs.put(1,1/3d);
		marketProbs.put(1, new MarketProbability(runnerProbs));
		
		assertEquals(0,calc.calculateLiability(muBets, marketProbs),0.00001);
	}
	
	@Test
	public void testCalculateLiabilityProbChangedLiabilityOverZero() {
		muBets.add(createBet(1,1,BetType.B,2, 3));
			
		Map<Integer, Double> runnerProbs = new HashMap<Integer, Double>();
		runnerProbs.put(1,1/2d);
		marketProbs.put(1, new MarketProbability(runnerProbs));
		
		assertEquals(1,calc.calculateLiability(muBets, marketProbs),0.00001);
	}
	
	@Test
	public void testCalculateLiabilityProbChangedLiabilityBelowZero() {
		muBets.add(createBet(1,1,BetType.B,2, 3));
			
		Map<Integer, Double> runnerProbs = new HashMap<Integer, Double>();
		runnerProbs.put(1,1/4d);
		marketProbs.put(1, new MarketProbability(runnerProbs));
		
		assertEquals(-0.5,calc.calculateLiability(muBets, marketProbs),0.00001);
	}
	
	@Test
	public void testCalculateLiabilityTwoBetsNoProbChangedLiabilityIsZero() {
		muBets.add(createBet(1,1,BetType.B,2, 3));
		muBets.add(createBet(1,2,BetType.L,2, 3));
			
		Map<Integer, Double> runnerProbs = new HashMap<Integer, Double>();
		runnerProbs.put(1,1/3d);
		runnerProbs.put(2,1/3d);
		marketProbs.put(1, new MarketProbability(runnerProbs));
		
		assertEquals(0,calc.calculateLiability(muBets, marketProbs),0.00001);
	}
	
	@Test
	public void testCalculateLiabilityTwoBetsProbChangedLiabilityOverZero() {
		muBets.add(createBet(1,1,BetType.B,2, 3));
		muBets.add(createBet(1,2,BetType.B,2, 3));
			
		Map<Integer, Double> runnerProbs = new HashMap<Integer, Double>();
		runnerProbs.put(2,1/2d);
		runnerProbs.put(1,1/2d);
		marketProbs.put(1, new MarketProbability(runnerProbs));
		
		assertEquals(2,calc.calculateLiability(muBets, marketProbs),0.00001);
	}
	
	@Test
	public void testCalculateLiabilityTwoBetsProbChangedLiabilityBelowZero() {
		muBets.add(createBet(1,1,BetType.L,2, 3));
		muBets.add(createBet(1,2,BetType.L,2, 3));
			
		Map<Integer, Double> runnerProbs = new HashMap<Integer, Double>();
		runnerProbs.put(1,1/2d);
		runnerProbs.put(2,1/2d);
		marketProbs.put(1, new MarketProbability(runnerProbs));
		
		assertEquals(-2,calc.calculateLiability(muBets, marketProbs),0.00001);
	}

	private MUBet createBet(int marketId, int selectionId,BetType betType, double size, double price) {
		MUBet bet = new MUBet();
		bet.setMarketId(marketId);
		bet.setSelectionId(selectionId);
		bet.setBetStatus(BetStatus.M);
		bet.setBetType(betType);
		bet.setSize(size);
		bet.setPrice(price);
		
		return bet;
	}
}
