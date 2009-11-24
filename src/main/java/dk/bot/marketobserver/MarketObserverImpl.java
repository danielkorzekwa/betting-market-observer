package dk.bot.marketobserver;

import java.util.Map;

import javax.sql.DataSource;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dk.bot.betfairservice.BetFairService;
import dk.bot.bwinservice.BWinService;
import dk.bot.marketobserver.cache.completemarkets.CompleteMarketsCache;
import dk.bot.marketobserver.marketservice.MarketServiceConfig;
import dk.bot.marketobserver.model.Market;
import dk.bot.marketobserver.runnerservice.MarketRunnerListener;
import dk.bot.marketobserver.runnerservice.RunnerService;
import dk.bot.marketobserver.tasks.bfmarket.RefreshBFMarketsImpl;

public class MarketObserverImpl implements MarketObserver {

	private final ClassPathXmlApplicationContext baseContext;
	private final ClassPathXmlApplicationContext context;
	
	private final RunnerService runnerService;

	private final CompleteMarketsCache completeMarketsCache;
			
	private RefreshBFMarketsImpl refreshBFMarketCacheTask;
	private Scheduler marketObserverScheduler;
	private final MarketRunnerListener marketRunnerListener;
	private final MarketServiceConfig marketServiceConfig;
	
	public MarketObserverImpl(BetFairService betFairService, BWinService bwinService, DataSource datasource,MarketRunnerListener marketRunnerListener,MarketServiceConfig marketServiceConfig) {
		this.marketRunnerListener = marketRunnerListener;
		this.marketServiceConfig = marketServiceConfig;
		baseContext = new ClassPathXmlApplicationContext(new String[]{});
		baseContext.getBeanFactory().registerSingleton("betFairService", betFairService);
		baseContext.getBeanFactory().registerSingleton("bwinService", bwinService);
		baseContext.getBeanFactory().registerSingleton("dataSource", datasource);
		context = new ClassPathXmlApplicationContext(new String[]{"spring-marketobserver.xml","spring-marketobserver-tasks.xml"},baseContext);
		
		runnerService = (RunnerService)context.getBean("runnerService");
		completeMarketsCache = (CompleteMarketsCache)context.getBean("completeMarketsCache");
		
		
		refreshBFMarketCacheTask = (RefreshBFMarketsImpl)context.getBean("refreshBFMarketCacheTask");
		
		marketObserverScheduler = (Scheduler)context.getBean("marketObserverScheduler");
	}

	public Map<Integer, Market> getCompleteMarkets() {
		return completeMarketsCache.getCompleteMarkets();
	}
	
	public void start() {
		refreshBFMarketCacheTask.init(marketServiceConfig,marketRunnerListener);
		runnerService.setMarketRunnerListener(marketRunnerListener,marketServiceConfig.getMarketFilters());
		
		try {
			marketObserverScheduler.start();
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
}
