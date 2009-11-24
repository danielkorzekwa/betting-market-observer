package dk.bot.marketobserver.runnerservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import dk.bot.betfairservice.BetFairService;
import dk.bot.betfairservice.model.BFBetCategoryType;
import dk.bot.betfairservice.model.BFBetStatus;
import dk.bot.betfairservice.model.BFBetType;
import dk.bot.betfairservice.model.BFMUBet;
import dk.bot.marketobserver.cache.completemarkets.CompleteMarketsCache;
import dk.bot.marketobserver.marketservice.MarketService;
import dk.bot.marketobserver.model.MUBets;
import dk.bot.marketobserver.model.Market;
import dk.bot.marketobserver.model.MarketData;
import dk.bot.marketobserver.model.MarketDetailsRunner;
import dk.bot.marketobserver.model.MarketFilter;
import dk.bot.marketobserver.tasks.bwin.BetFairBwinRegionEnum;
import dk.bot.marketobserver.tasks.bwin.BwinMarketPrices;
import dk.bot.oddschecker.model.HorseWinMarket;

@RunWith(JMock.class)
public class RunnerServiceImplTest {

	private RunnerServiceImpl runnerService;

	private Mockery mockery = new JUnit4Mockery();
	private final BetFairService betFairService = mockery.mock(BetFairService.class);
	private final MarketRunnerListener marketRunnerListener = mockery.mock(MarketRunnerListener.class);
	private final CompleteMarketsCache completeMarketsCache = mockery.mock(CompleteMarketsCache.class);
	
	final List<BFMUBet> muBets = new ArrayList<BFMUBet>();

	private List<MarketFilter> marketFilters = new ArrayList<MarketFilter>();

	@Before
	public void setUp() {
		runnerService = new RunnerServiceImpl(betFairService, completeMarketsCache);

		runnerService.setMarketRunnerListener(marketRunnerListener, marketFilters);

		mockery.checking(new Expectations() {
			{
				allowing(completeMarketsCache).getCompleteMarket(1);
				one(betFairService).getMUBets(BFBetStatus.MU);
				will(returnValue(muBets));
				one(marketRunnerListener).onMUBetsData(with(any(MUBets.class)));
			}
		});
	}

	/** Check if horse racing market is analyzed */
	@Test
	public void testAnalyzeRunnersHorseRacingRunnersIsAnalyzed() {
		marketFilters.add(new MarketFilter("filterName", "/7/", "Winner", false,false));

		final List<Market> markets = new ArrayList<Market>();
		markets.add(createMarket(1, "Winner", "/7/44322", BetFairBwinRegionEnum.HORSERACING_GB, null, null));

		mockery.checking(new Expectations() {
			{
				one(betFairService).getMarketRunners(1);
			}
		});

		runnerService.analyzeRunners(markets,true);
	}

	/** Check if soccer market with bwin prices is analyzed */
	@Test
	public void testAnalyzeRunnersSoccerWithBwinPricesIsAnalyzed() {

		marketFilters.add(new MarketFilter("filterName", "/1/", "Match Odds", true,false));

		final List<Market> markets = new ArrayList<Market>();
		markets.add(createMarket(1, "Match Odds", "/1/3352", BetFairBwinRegionEnum.SOCCER_CZECH, new BwinMarketPrices(
				1, new HashMap<Integer, Double>()), null));
		
		mockery.checking(new Expectations() {
			{
				one(betFairService).getMarketRunners(1);
			}
		});

		runnerService.analyzeRunners(markets,true);
	}

	/** Check if soccer market without bwin prices is not analyzed */
	@Test
	public void testAnalyzeRunnersSoccerWithoutBwinPricesIsNotAnalyzed() {
		marketFilters.add(new MarketFilter("filterName", "/1/", "Match Odds", true,false));

		final List<Market> markets = new ArrayList<Market>();
		markets.add(createMarket(1, "Match Odds", "/1/3352", BetFairBwinRegionEnum.SOCCER_CZECH, null, null));

		runnerService.analyzeRunners(markets,true);
	}

	/** Check if any market with bets is analyzed */
	@Test
	public void testAnalyzeRunnersAnyMarketWithBetsIsAnalyzed() {

		muBets.add(createBet(1));
		
		final List<Market> markets = new ArrayList<Market>();
		markets.add(createMarket(1, "MatchOdds", "/676575/54657", BetFairBwinRegionEnum.BASEBALL_NORTH_AMERICA_MLB2008,
				null, null));
		
		mockery.checking(new Expectations() {
			{
				one(betFairService).getMarketRunners(1);
			}
		});

	
		runnerService.analyzeRunners(markets,true);
	}

	private Market createMarket(int marketId, String marketName, String eventPath,
			BetFairBwinRegionEnum betfairBwinRegion, BwinMarketPrices bwinPrices, HorseWinMarket horseWinMarket) {
		MarketData marketData = new MarketData();
		marketData.setMarketId(marketId);
		marketData.setMarketName(marketName);
		marketData.setEventHierarchy(eventPath);
		Market market = new Market(marketData, betfairBwinRegion, bwinPrices, horseWinMarket);
		return market;
	}

	private BFMUBet createBet(int marketId) {
		BFMUBet bet = new BFMUBet();
		bet.setMarketId(marketId);
		bet.setBetCategoryType(BFBetCategoryType.E);
		bet.setBetType(BFBetType.L);
		bet.setBetStatus(BFBetStatus.M);
		return bet;
	}
}