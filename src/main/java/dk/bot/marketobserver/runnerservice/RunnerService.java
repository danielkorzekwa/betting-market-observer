package dk.bot.marketobserver.runnerservice;

import java.util.List;

import dk.bot.marketobserver.model.Market;
import dk.bot.marketobserver.model.MarketFilter;
import dk.bot.marketobserver.tasks.analyzerunners.RunnersSummary;

/** Analyze runners on markets and place bets
 * 
 * @author daniel
 *
 */
public interface RunnerService {

	/**
	 * 
	 * @param markets markets to analyze.
	 * @param getAllBets if true the all muBets are obtained from betting exchange.  If false, then bets for analyzed markets are obtained.
	 */
	public RunnersSummary analyzeRunners(List<Market> markets, boolean getAllBets);
	
	/**Must be called before service can be used.*/
	public void setMarketRunnerListener(MarketRunnerListener marketRunnerListener,List<MarketFilter> marketFilters);
}
