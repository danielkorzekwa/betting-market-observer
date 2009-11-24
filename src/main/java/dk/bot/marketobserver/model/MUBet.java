package dk.bot.marketobserver.model;

import java.io.Serializable;
import java.util.Date;


/**
 * Represents a bet on a betting exchange.
 * 
 * @author daniel
 * 
 */
public class MUBet implements Serializable,Comparable<MUBet>{

	    private long betId;
	    private BetStatus betStatus;
	    private BetType betType;
	    private BetCategoryType betCategoryType;
	    
	    private int marketId;
	    private int selectionId;
	    
	    private Date placedDate;
	    private Date matchedDate;
	    
	    /**Valid for SP bets only*/
	    private double bspLiability;
	    
	    private double size;
	    private double price;
	    
	    /**Optional fields*/
	    
	    /**Full market name, e.g.GB / Salis 14th Jun/ 15:40 To Be Placed*/
	    private String fullMarketName;
	    private String selectionName;
	    
		public long getBetId() {
			return betId;
		}
		public void setBetId(long betId) {
			this.betId = betId;
		}
		public BetStatus getBetStatus() {
			return betStatus;
		}
		public void setBetStatus(BetStatus betStatus) {
			this.betStatus = betStatus;
		}
		public BetType getBetType() {
			return betType;
		}
		public void setBetType(BetType betType) {
			this.betType = betType;
		}
		public int getMarketId() {
			return marketId;
		}
		public void setMarketId(int marketId) {
			this.marketId = marketId;
		}
		public int getSelectionId() {
			return selectionId;
		}
		public void setSelectionId(int selectionId) {
			this.selectionId = selectionId;
		}
		public Date getPlacedDate() {
			return placedDate;
		}
		public void setPlacedDate(Date placedDate) {
			this.placedDate = placedDate;
		}
		public Date getMatchedDate() {
			return matchedDate;
		}
		public void setMatchedDate(Date matchedDate) {
			this.matchedDate = matchedDate;
		}
		public double getSize() {
			return size;
		}
		public void setSize(double size) {
			this.size = size;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		
		public BetCategoryType getBetCategoryType() {
			return betCategoryType;
		}
		public void setBetCategoryType(BetCategoryType betCategoryType) {
			this.betCategoryType = betCategoryType;
		}
		
		public double getBspLiability() {
			return bspLiability;
		}
		public void setBspLiability(double bspLiability) {
			this.bspLiability = bspLiability;
		}
		public String getFullMarketName() {
			return fullMarketName;
		}
		public void setFullMarketName(String fullMarketName) {
			this.fullMarketName = fullMarketName;
		}
		public String getSelectionName() {
			return selectionName;
		}
		public void setSelectionName(String selectionName) {
			this.selectionName = selectionName;
		}
		public int compareTo(MUBet o) {
			if(getBetStatus()==BetStatus.M && o.getBetStatus()==BetStatus.M) {
				return o.getMatchedDate().compareTo(getMatchedDate());
			}
			else if(getBetStatus()==BetStatus.U && o.getBetStatus()==BetStatus.U){
				return o.getPlacedDate().compareTo(getPlacedDate());
			}
			else if(getBetStatus()==BetStatus.M && o.getBetStatus()==BetStatus.U){
				return -1;
			}
			else if(getBetStatus()==BetStatus.U && o.getBetStatus()==BetStatus.M){
				return 1;
			}
			else {
				return 0;
			}
			
		}
		
		@Override
		public String toString() {
			return "p/m:" +placedDate + "/" + matchedDate + ", m: " + marketId + ", s:" + selectionId + ",type: " + betType + ",status: " + betStatus + ",size: " + size + ",price:" + price;
		}
		
}
