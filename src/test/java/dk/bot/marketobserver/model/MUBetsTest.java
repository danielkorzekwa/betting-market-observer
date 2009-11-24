package dk.bot.marketobserver.model;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MUBetsTest {

	private MUBets muBets = new MUBets();
	
	@Before
	public void setUp() {
		muBets.getMuBets().add(createMUBet(1,1,BetCategoryType.E,BetType.B,BetStatus.M, new Date(1000),new Date(6000)));
		muBets.getMuBets().add(createMUBet(1,2,BetCategoryType.E,BetType.L,BetStatus.U,new Date(3000),new Date(5000)));
		muBets.getMuBets().add(createMUBet(2,1,BetCategoryType.E,BetType.L,BetStatus.U,new Date(2000),new Date(4000)));
		muBets.getMuBets().add(createMUBet(3,1,BetCategoryType.M,BetType.L,BetStatus.U,new Date(2000),new Date(4000)));
		muBets.getMuBets().add(createMUBet(3,2,BetCategoryType.L,BetType.L,BetStatus.U,new Date(2000),new Date(4000)));
	}
	
	@Test
	public void MUBets() {
		MUBets bets = new MUBets(null);
		assertEquals(0,bets.getMuBets().size());
	}
	
	@Test
	public void testGetMuBets() {
		assertEquals(5, muBets.getMuBets().size());
	}
	
	@Test
	public void testGetMarketMUBets() {
		assertEquals(2, muBets.getMarketMUBets(1).size());
		assertEquals(1, muBets.getMarketMUBets(2).size());
		assertEquals(0, muBets.getMarketMUBets(4).size());
	}

	@Test
	public void testGetMarketRunnerMUBets() {
		assertEquals(1, muBets.getMarketRunnerMUBets(1, 1).getMuBets().size());
		assertEquals(0, muBets.getMarketRunnerMUBets(1, 3).getMuBets().size());
	}

	@Test
	public void testGetBetsBetStatus() {
		assertEquals(1, muBets.getBets(BetStatus.M).getMuBets().size());
		assertEquals(4, muBets.getBets(BetStatus.U).getMuBets().size());
	}
	
	@Test
	public void testGetBetsBetCategoryType() {
		assertEquals(3, muBets.getBets(BetCategoryType.E).getMuBets().size());
		assertEquals(1, muBets.getBets(BetCategoryType.M).getMuBets().size());
		assertEquals(1, muBets.getBets(BetCategoryType.L).getMuBets().size());
		
	}
	
	@Test
	public void testGetSPBets() {
		List<MUBet> spBets = muBets.getSPBets();
		assertEquals(2,spBets.size());
		assertEquals(true, spBets.get(0).getBetCategoryType()==BetCategoryType.M);
		assertEquals(true, spBets.get(1).getBetCategoryType()==BetCategoryType.L);
		
	}

	@Test
	public void testGetLastPlacedBetDate() {
		assertEquals(3000, muBets.getLastPlacedBetDate().getTime());
	}
	@Test
	public void testGetLastPlacedBetDateNoBets() {
		muBets.getMuBets().clear();
		assertEquals(0, muBets.getLastPlacedBetDate().getTime());
	}

	@Test
	public void testGetLastMatchedBetDate() {
		assertEquals(6000, muBets.getLastMatchedBetDate().getTime());
	}
	@Test
	public void testGetLastMatchedBetDateNoBets() {
		muBets.getMuBets().clear();
		assertEquals(0, muBets.getLastMatchedBetDate().getTime());
	}

	@Test
	public void testGetBetsBetStatusBetType() {
		assertEquals(1, muBets.getBets(BetStatus.M,BetType.B).size());
		assertEquals(0, muBets.getBets(BetStatus.M,BetType.L).size());
		assertEquals(1, muBets.getBets(BetStatus.M,null).size());
		assertEquals(0, muBets.getBets(BetStatus.U,BetType.B).size());
		assertEquals(4, muBets.getBets(BetStatus.U,BetType.L).size());
		assertEquals(4, muBets.getBets(BetStatus.U,null).size());
		
	}

	@Test
	public void testGetLastBet() {
		assertEquals(1000, muBets.getLastBet(BetType.B).getPlacedDate().getTime());
		
		muBets.getMuBets().get(0).setBetType(BetType.L);
		assertEquals(3000, muBets.getLastBet(BetType.L).getPlacedDate().getTime());
	}
	
	@Test
	public void testGetLastBetNoBets() {
		muBets.getMuBets().clear();
		assertEquals(null, muBets.getLastBet(BetType.B));
	}
	
	private MUBet createMUBet(int marketId, int selectionId,BetCategoryType betCategoryType,BetType betType,BetStatus status,Date placedDate,Date matchedDate) {
		MUBet muBet = new MUBet();
		muBet.setMarketId(marketId);
		muBet.setSelectionId(selectionId);
		muBet.setBetCategoryType(betCategoryType);
		muBet.setBetType(betType);
		muBet.setBetStatus(status);
		muBet.setPlacedDate(placedDate);
		muBet.setMatchedDate(matchedDate);
		
		return muBet;
	}

}
