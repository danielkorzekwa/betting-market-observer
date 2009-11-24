package dk.bot.marketobserver.model.factory;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dk.bot.betfairservice.model.BFBetCategoryType;
import dk.bot.betfairservice.model.BFBetStatus;
import dk.bot.betfairservice.model.BFBetType;
import dk.bot.betfairservice.model.BFMUBet;
import dk.bot.marketobserver.model.MUBet;

public class MUBetFactoryTest {

	List<BFMUBet> bfMUBets;
	
	@Before
	public void setUp() {
		bfMUBets = new ArrayList<BFMUBet>();
		bfMUBets.add(createMUBet(88));
		bfMUBets.add(createMUBet(89));
	}
	
	@Test
	public void testCreate() {
		List<MUBet> muBets = MUBetFactory.create(bfMUBets);
		
		assertEquals(bfMUBets.size(), muBets.size());
		
		for(int i=0;i<muBets.size();i++) {
			BFMUBet bfMUBet = bfMUBets.get(i);
			MUBet muBet = muBets.get(i);
			
			assertEquals(bfMUBet.getBetId(),muBet.getBetId());
			assertEquals(bfMUBet.getBetStatus().value(),muBet.getBetStatus().value());
			assertEquals(bfMUBet.getBetType().value(),muBet.getBetType().value());
			assertEquals(bfMUBet.getBetCategoryType().value(),muBet.getBetCategoryType().value());
			assertEquals(bfMUBet.getMarketId(),muBet.getMarketId());
			assertEquals(bfMUBet.getSelectionId(),muBet.getSelectionId());
			assertEquals(bfMUBet.getPlacedDate(),muBet.getPlacedDate());
			assertEquals(bfMUBet.getMatchedDate(),muBet.getMatchedDate());
			assertEquals(bfMUBet.getBspLiability(),muBet.getBspLiability(),0);
			assertEquals(bfMUBet.getSize(),muBet.getSize(),0);
			assertEquals(bfMUBet.getPrice(),muBet.getPrice(),0);
		}
	}
	
	private BFMUBet createMUBet(long betId) {
		BFMUBet bfMUBet = new BFMUBet();
		bfMUBet.setBetId(betId);
		bfMUBet.setBetStatus(BFBetStatus.MU);
		bfMUBet.setBetType(BFBetType.B);
		bfMUBet.setBetCategoryType(BFBetCategoryType.M);
		bfMUBet.setMarketId(100);
		bfMUBet.setSelectionId(200);
		bfMUBet.setPlacedDate(new Date(600));
		bfMUBet.setMatchedDate(new Date(700));
		bfMUBet.setBspLiability(2.34d);
		bfMUBet.setSize(5.3d);
		bfMUBet.setPrice(1.17d);
		
		return bfMUBet;
	}

}
