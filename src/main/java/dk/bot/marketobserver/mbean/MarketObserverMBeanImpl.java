package dk.bot.marketobserver.mbean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

import dk.bot.bwinservice.model.BWinMarket;
import dk.bot.marketobserver.cache.ObjectCacheInfo;
import dk.bot.marketobserver.cache.adrianmassey.AdrianMasseyCache;
import dk.bot.marketobserver.cache.adrianmassey.matchedadrianmassey.MatchedAdrianMasseyCache;
import dk.bot.marketobserver.cache.bfmarket.BFMarketCache;
import dk.bot.marketobserver.cache.bwin.BwinCache;
import dk.bot.marketobserver.cache.bwin.matchedbwin.MatchedBwinCache;
import dk.bot.marketobserver.cache.completemarkets.CompleteMarketsCache;
import dk.bot.marketobserver.cache.racingpost.RacingPostCache;
import dk.bot.marketobserver.cache.racingpost.matchedracingpost.MatchedRacingPostCache;
import dk.bot.marketobserver.marketservice.MarketService;
import dk.bot.marketobserver.model.BetStatus;
import dk.bot.marketobserver.model.MUBet;
import dk.bot.marketobserver.model.MUBets;
import dk.bot.marketobserver.model.Market;
import dk.bot.marketobserver.model.MarketData;
import dk.bot.marketobserver.model.MarketRunner;
import dk.bot.marketobserver.tasks.adrianmassey.RefreshAdrianMasseyCache;
import dk.bot.marketobserver.tasks.analyzerunners.HR10MinBeforeRunnerServiceTaskImpl;
import dk.bot.marketobserver.tasks.analyzerunners.RunnerServiceTaskImpl;
import dk.bot.marketobserver.tasks.analyzerunners.RunnersSummary;
import dk.bot.marketobserver.tasks.bfmarket.RefreshBFMarketsImpl;
import dk.bot.marketobserver.tasks.bwin.RefreshBwinCache;
import dk.bot.marketobserver.tasks.racingpost.RefreshRacingpostCache;
import dk.bot.marketobserver.util.BeanStat;
import dk.bot.marketobserver.util.liabilitycalc.LiabilityCalculatorImpl;
import dk.bot.marketobserver.util.liabilitycalc.MarketProbability;

@ManagedResource(objectName = "dk.flexibet.marketobserver:name=MarketObserver")
public class MarketObserverMBeanImpl implements MarketObserverMBean {

	@Resource
	private CompleteMarketsCache completeMarketsCache;

	@Resource
	private RunnerServiceTaskImpl runnerServiceTask;

	@Resource
	private HR10MinBeforeRunnerServiceTaskImpl hr10MinBeforeRunnerServiceTask;
	@Resource
	private RefreshBFMarketsImpl refreshBFMarketCacheTask;
	@Resource
	private RefreshBwinCache refreshBwinCacheTask;
	@Resource
	private RefreshRacingpostCache refreshRacingpostCacheTask;
	@Resource
	RefreshAdrianMasseyCache refreshAdrianMasseyCacheTask;

	@Resource
	private BFMarketCache bfMarketCache;
	
	@Resource
	private BwinCache bwinCache;
	@Resource
	private MatchedBwinCache matchedBwinCache;
	
	@Resource
	RacingPostCache racingPostCache;
	@Resource
	MatchedRacingPostCache matchedRacingPostCache;

	@Resource
	AdrianMasseyCache adrianMasseyCache;
	@Resource
	MatchedAdrianMasseyCache matchedAdrianMasseyCache;

	@Resource
	MarketService marketService;
	
	@Resource
	private Scheduler marketObserverScheduler;
	
	@ManagedAttribute
	public int getCompleteMarketsCacheMarketsAmount() {
		return completeMarketsCache.getMarketsAmount();
	}

	@ManagedAttribute
	public List<BeanStat> getScheduledTaskStat() {
		List<BeanStat> beanStats = new ArrayList<BeanStat>();
		beanStats.add(refreshBFMarketCacheTask.getBeanStat());
		beanStats.add(refreshBwinCacheTask.getBeanStat());
		beanStats.add(refreshRacingpostCacheTask.getBeanStat());
		beanStats.add(refreshAdrianMasseyCacheTask.getBeanStat());
		beanStats.add(runnerServiceTask.getBeanStat());
		beanStats.add(hr10MinBeforeRunnerServiceTask.getBeanStat());

		return beanStats;
	}
	
	@ManagedAttribute
	public boolean isSchedulerRunning() {
		try {
			return !marketObserverScheduler.isInStandbyMode() && !marketObserverScheduler.isShutdown();
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@ManagedOperation
	public void stopScheduler() {
		try {
			marketObserverScheduler.shutdown(true);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	@ManagedAttribute
	public List<ObjectCacheInfo> getObjectCacheInfo() {
		List<ObjectCacheInfo> cacheInfo = new ArrayList<ObjectCacheInfo>();
		cacheInfo.add(bfMarketCache.getCacheInfo());
		cacheInfo.add(bwinCache.getCacheInfo());
		cacheInfo.add(matchedBwinCache.getCacheInfo());
		cacheInfo.add(racingPostCache.getCacheInfo());
		cacheInfo.add(matchedRacingPostCache.getCacheInfo());
		cacheInfo.add(adrianMasseyCache.getCacheInfo());
		cacheInfo.add(matchedAdrianMasseyCache.getCacheInfo());
		return cacheInfo;
	}
	
	@ManagedAttribute
	public Collection<BWinMarket> getBwinMarkets() {
		return bwinCache.getObjects();
	}
	
	@ManagedAttribute
	public RunnersSummary getNewestRunnersSummary() {
		return runnerServiceTask.getRunnersSummary();
	}
	
	@ManagedAttribute
	public List<Market> getMarkets() {
		Collection<MarketData> marketData = bfMarketCache.getObjects();
		if(marketData==null) {
			marketData = new ArrayList<MarketData>();
		}
		List<Market> markets = marketService.getCompositeMarkets(marketData);
		if(markets==null) {
			markets = new ArrayList<Market>();
		}
		return markets;
	}
	
	@ManagedOperation
	@ManagedOperationParameters({@ManagedOperationParameter(name = "marketId", description = "")})
	public Market getCompleteMarket(int marketId) {
		return completeMarketsCache.getCompleteMarket(marketId);
	}
	
	@ManagedAttribute
	public MUBets getMUBets() {
		if(runnerServiceTask.getRunnersSummary()!=null) {
			return runnerServiceTask.getRunnersSummary().getMuBets();
		}
		else {
			return null;
		}
	}
	
	@ManagedAttribute
	public TotalLiability getMatchedBetsLiability() {
		Map<Integer, MarketProbability> marketProbs = new HashMap<Integer, MarketProbability>();
		for(Integer marketId: completeMarketsCache.getCompleteMarkets().keySet()) {
			Market completeMarket = completeMarketsCache.getCompleteMarket(marketId);
			
			Map<Integer, Double> runnerProbs = new HashMap<Integer, Double>();
			for (MarketRunner marketRunner : completeMarket.getMarketRunners().getMarketRunners()) {
				runnerProbs.put(marketRunner.getSelectionId(), 1 / marketRunner.getAvgPrice());
			}
			marketProbs.put(completeMarket.getMarketData().getMarketId(), new MarketProbability(runnerProbs));
		}
		
		/**Calculate liability only for bets that the probability is available.*/
		List<MUBet> allMatchedBets = new ArrayList<MUBet>();
		for(MUBet muBet: runnerServiceTask.getRunnersSummary().getMuBets().getMuBets()) {
			if(muBet.getBetStatus()==BetStatus.M && marketProbs.containsKey(muBet.getMarketId())) {
				allMatchedBets.add(muBet);
			}
		}
		List<MUBet> matchedNotInPlayBets = new ArrayList<MUBet>();
		for(MUBet muBet: runnerServiceTask.getRunnersSummary().getMuBets().getMuBets()) {
			if(muBet.getBetStatus()==BetStatus.M && marketProbs.containsKey(muBet.getMarketId()) && completeMarketsCache.getCompleteMarket(muBet.getMarketId()).getMarketRunners().getInPlayDelay()==0) {
				matchedNotInPlayBets.add(muBet);
			}
		}
		
		double allMatchedBetsLiability = new LiabilityCalculatorImpl().calculateLiability(allMatchedBets, marketProbs);
		double matchedNotInPLayBetsLiability = new LiabilityCalculatorImpl().calculateLiability(matchedNotInPlayBets, marketProbs);
		
		TotalLiability totalLiability = new TotalLiability();
		totalLiability.setAllMatchedBetsNumber(allMatchedBets.size());
		totalLiability.setAllMatchedBetsLiability(allMatchedBetsLiability);
		totalLiability.setAllMatchedNotInPlayBetsNumber(matchedNotInPlayBets.size());
		totalLiability.setAllMatchedNotInPlayBetsLiability(matchedNotInPLayBetsLiability);
		
		return totalLiability;
	}
}
