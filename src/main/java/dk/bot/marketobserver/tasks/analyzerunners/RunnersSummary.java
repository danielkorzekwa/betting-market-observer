package dk.bot.marketobserver.tasks.analyzerunners;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import dk.bot.marketobserver.model.MUBets;
import dk.bot.marketobserver.model.Market;

/**
 * Summary of the runners analysis.
 * 
 * @author daniel
 * 
 */
public class RunnersSummary implements Serializable{

	private MUBets muBets;

	/** True if runners analysis finished successfully. */
	private boolean status;
	
	/**When market runners were analyzed (end time).*/
	private Date timeStamp;
	
	/** Analyzed markets with runners. Key - marketId*/
	private Map<Integer,Market> completeMarkets;
	
	private Throwable lastException;
	private Date lastExceptionDate;
	
	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public MUBets getMuBets() {
		return muBets;
	}

	public void setMuBets(MUBets muBets) {
		this.muBets = muBets;
	}

	public int getAnalyzedMarketsAmount() {
		return completeMarkets.size();
	}
	
	public int getAnalyzedMarketsInPlayAmount() {
		int amount = 0;
		for(Market completeMarket : this.completeMarkets.values()) {
			if(completeMarket.getMarketRunners().getInPlayDelay()>0) {
				amount++;
			}
		}
		
		return amount;
	}

	public int getAnalyzedRunnersAmount() {
		int amount=0;
		for(Market completeMarket: this.completeMarkets.values()) {
			amount = amount + completeMarket.getMarketRunners().getMarketRunners().size();
		}
		
		return amount;
	}


	
	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public Map<Integer, Market> getCompleteMarkets() {
		return completeMarkets;
	}

	public void setCompleteMarkets(Map<Integer, Market> completeMarkets) {
		this.completeMarkets = completeMarkets;
	}

	public Throwable getLastException() {
		return lastException;
	}

	public void setLastException(Throwable lastException) {
		this.lastException = lastException;
	}

	public Date getLastExceptionDate() {
		return lastExceptionDate;
	}

	public void setLastExceptionDate(Date lastExceptionDate) {
		this.lastExceptionDate = lastExceptionDate;
	}
	
}
