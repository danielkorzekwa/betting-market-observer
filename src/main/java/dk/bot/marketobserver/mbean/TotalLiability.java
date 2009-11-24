package dk.bot.marketobserver.mbean;

import java.io.Serializable;

/**Total liability based on user bets and current market probabilities.
 * 
 * @author daniel
 *
 */
public class TotalLiability implements Serializable{

	/**Number of all matched returned by betfair.getMUBets()*/
	private int allMatchedBetsNumber;
	private double allMatchedBetsLiability;
	
	private int allMatchedNotInPlayBetsNumber;
	private double allMatchedNotInPlayBetsLiability;
	public int getAllMatchedBetsNumber() {
		return allMatchedBetsNumber;
	}
	public void setAllMatchedBetsNumber(int allMatchedBetsNumber) {
		this.allMatchedBetsNumber = allMatchedBetsNumber;
	}
	public double getAllMatchedBetsLiability() {
		return allMatchedBetsLiability;
	}
	public void setAllMatchedBetsLiability(double allMatchedBetsLiability) {
		this.allMatchedBetsLiability = allMatchedBetsLiability;
	}
	public int getAllMatchedNotInPlayBetsNumber() {
		return allMatchedNotInPlayBetsNumber;
	}
	public void setAllMatchedNotInPlayBetsNumber(int allMatchedNotInPlayBetsNumber) {
		this.allMatchedNotInPlayBetsNumber = allMatchedNotInPlayBetsNumber;
	}
	public double getAllMatchedNotInPlayBetsLiability() {
		return allMatchedNotInPlayBetsLiability;
	}
	public void setAllMatchedNotInPlayBetsLiability(double allMatchedNotInPlayBetsLiability) {
		this.allMatchedNotInPlayBetsLiability = allMatchedNotInPlayBetsLiability;
	}	
}
