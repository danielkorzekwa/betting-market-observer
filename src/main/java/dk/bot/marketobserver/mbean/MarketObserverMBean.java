package dk.bot.marketobserver.mbean;

import java.util.Collection;
import java.util.List;

import dk.bot.bwinservice.model.BWinMarket;
import dk.bot.marketobserver.cache.ObjectCacheInfo;
import dk.bot.marketobserver.model.MUBets;
import dk.bot.marketobserver.model.Market;
import dk.bot.marketobserver.tasks.analyzerunners.RunnersSummary;
import dk.bot.marketobserver.util.BeanStat;

/**
 * JMX mbean interface for Market Observer
 * 
 * @author daniel
 * 
 */
public interface MarketObserverMBean {

	public int getCompleteMarketsCacheMarketsAmount();

	public List<BeanStat> getScheduledTaskStat();
	
	/** Check status of scheduler: running or stopped
	 * 
	 * @return true if running
	 */
	public boolean isSchedulerRunning();
	
	/**Stop scheduler. Wait until all currently running tasks are finished.
	 * 
	 */
	public void stopScheduler();
		
	/**
	 *  Information about object caches
	 * */
	public List<ObjectCacheInfo> getObjectCacheInfo();
	
	/**Returns all bwin markets.*/
	public Collection<BWinMarket> getBwinMarkets();

	/**Get newest runners summary.*/
	public RunnersSummary getNewestRunnersSummary();
	
	/** Gets markets from cache.
	 * 
	 * @return empty markets if cache empty
	 */
	public List<Market> getMarkets();
	
	/**Return latest market with runners data.*/
	public Market getCompleteMarket(int marketId);
	
	/**Returns matched/unmatched bets*/
	public MUBets getMUBets();
	
	/** Returns a liability based on all matched bets and current probabilities.
	 * 
	 * @return
	 */
	public TotalLiability getMatchedBetsLiability();
}
