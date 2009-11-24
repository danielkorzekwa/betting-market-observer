package dk.bot.marketobserver.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Represents list of MU bets. Provides methods for filtering bets, e.g. get
 * bets for market or marketRunner.
 * 
 * @author daniel
 * 
 */
public class MUBets implements Serializable{

	private final List<MUBet> muBets;

	public MUBets(List<MUBet> muBets) {
		if (muBets != null) {
			this.muBets = muBets;
		} else {
			this.muBets = new ArrayList<MUBet>();
		}

	}

	public MUBets() {
		this.muBets = new ArrayList<MUBet>();
	}

	public List<MUBet> getMuBets() {
		return muBets;
	}

	/** Get bets for market */
	public List<MUBet> getMarketMUBets(int marketId) {

		List<MUBet> marketMUBets = new ArrayList<MUBet>();
		for (MUBet bet : muBets) {
			if (bet.getMarketId() == marketId) {
				marketMUBets.add(bet);
			}
		}

		return marketMUBets;
	}

	/** Get bets for market runner */
	public MUBets getMarketRunnerMUBets(int marketId, int selectionId) {

		List<MUBet> marketRunnerMUBets = new ArrayList<MUBet>();
		for (MUBet bet : muBets) {
			if (bet.getMarketId() == marketId && bet.getSelectionId() == selectionId) {
				marketRunnerMUBets.add(bet);
			}
		}

		return new MUBets(marketRunnerMUBets);
	}

	/** Get bets for betStatus. */
	public MUBets getBets(BetStatus status) {
		List<MUBet> filteredBets = new ArrayList<MUBet>();
		for (MUBet bet : muBets) {
			if (bet.getBetStatus() == status) {
				filteredBets.add(bet);
			}
		}
		return new MUBets(filteredBets);
	}
	
	/** Get bets for betCategoryType. */
	public MUBets getBets(BetCategoryType betCategoryType) {
		List<MUBet> filteredBets = new ArrayList<MUBet>();
		for (MUBet bet : muBets) {
			if (bet.getBetCategoryType() == betCategoryType) {
				filteredBets.add(bet);
			}
		}
		return new MUBets(filteredBets);
	}

	public List<MUBet> getSPBets() {
		List<MUBet> spBets = new ArrayList<MUBet>();
		for(MUBet bet: muBets) {
			if(bet.getBetCategoryType()==BetCategoryType.L || bet.getBetCategoryType()==BetCategoryType.M) {
				spBets.add(bet);
			}
		}
		
		return spBets;
	}
	
	
	/** 0 if no bets */
	public Date getLastPlacedBetDate() {
		Date lastDate = new Date(0);

		for (MUBet bet : this.muBets) {
			if (bet.getPlacedDate().getTime() > lastDate.getTime()) {
				lastDate = bet.getPlacedDate();
			}
		}

		return lastDate;
	}

	/**
	 * 
	 * @return 0 if no bets.
	 */
	public Date getLastMatchedBetDate() {
		Date lastDate = new Date(0);

		for (MUBet bet : this.muBets) {
			if (bet.getBetStatus() == BetStatus.M && bet.getMatchedDate().getTime() > lastDate.getTime()) {
				lastDate = bet.getMatchedDate();
			}
		}

		return lastDate;
	}

	/**
	 * 
	 * @param bets
	 * @param betStatus
	 * @param betType
	 *            if null then return bets for all bet types
	 * @return
	 */
	public List<MUBet> getBets(BetStatus betStatus, BetType betType) {

		List<MUBet> filteredBets = new ArrayList<MUBet>();

		for (MUBet bet : muBets) {
			if (bet.getBetStatus() == betStatus && (betType == null || bet.getBetType() == betType)) {
				filteredBets.add(bet);
			}
		}

		return filteredBets;
	}

	/**
	 * Returns last bet based on a placedDate.
	 * 
	 * @param betType
	 * @return null if no bets for specified criteria.
	 */
	public MUBet getLastBet(BetType betType) {
		MUBet lastBet = null;

		for (MUBet bet : muBets) {
			if (bet.getBetType() == betType) {
				if (lastBet == null) {
					lastBet = bet;
				} else if (bet.getPlacedDate().getTime() > lastBet.getPlacedDate().getTime()) {
					lastBet = bet;
				}
			}
		}

		return lastBet;
	}
}
