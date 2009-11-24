package dk.bot.marketobserver.model.factory;

import java.util.ArrayList;
import java.util.List;

import dk.bot.betfairservice.model.BFMUBet;
import dk.bot.marketobserver.model.BetCategoryType;
import dk.bot.marketobserver.model.BetStatus;
import dk.bot.marketobserver.model.BetType;
import dk.bot.marketobserver.model.MUBet;

/** Coverts BFMUBet to MUBet.
 * 
 * @author daniel
 *
 */
public class MUBetFactory {

	public static List<MUBet> create(List<BFMUBet> bfMUBets) {
		ArrayList<MUBet> muBets = new ArrayList<MUBet>(bfMUBets.size());
		for(BFMUBet bfMUBet : bfMUBets) {
			MUBet muBet = new MUBet();
			muBet.setBetId(bfMUBet.getBetId());
			muBet.setBetStatus(BetStatus.fromValue(bfMUBet.getBetStatus().value()));
			muBet.setBetType(BetType.fromValue(bfMUBet.getBetType().value()));
			muBet.setBetCategoryType(BetCategoryType.fromValue(bfMUBet.getBetCategoryType().value()));
			muBet.setMarketId(bfMUBet.getMarketId());
			muBet.setSelectionId(bfMUBet.getSelectionId());
			muBet.setPlacedDate(bfMUBet.getPlacedDate());
			muBet.setMatchedDate(bfMUBet.getMatchedDate());
			muBet.setBspLiability(bfMUBet.getBspLiability());
			muBet.setSize(bfMUBet.getSize());
			muBet.setPrice(bfMUBet.getPrice());
			
			muBets.add(muBet);
		}
		return muBets;
	}
}
