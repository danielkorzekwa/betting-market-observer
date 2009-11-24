package dk.bot.marketobserver.runnerservice;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.betfair.publicapi.types.exchange.v5.MarketLite;

import dk.bot.betfairservice.BetFairService;
import dk.bot.betfairservice.model.BFBetStatus;
import dk.bot.betfairservice.model.BFMUBet;
import dk.bot.betfairservice.model.BFMarketRunners;
import dk.bot.marketobserver.cache.completemarkets.CompleteMarketsCache;
import dk.bot.marketobserver.model.MUBet;
import dk.bot.marketobserver.model.MUBets;
import dk.bot.marketobserver.model.Market;
import dk.bot.marketobserver.model.MarketFilter;
import dk.bot.marketobserver.model.MarketRunners;
import dk.bot.marketobserver.model.factory.MUBetFactory;
import dk.bot.marketobserver.model.factory.MarketRunnersFactory;
import dk.bot.marketobserver.tasks.analyzerunners.RunnersSummary;
import dk.bot.marketobserver.util.MarketFilterMatcher;

public class RunnerServiceImpl implements RunnerService {

	private BetFairService betFairService;
	private MarketRunnerListener marketRunnerListener;
	private List<MarketFilter> marketFilters;
	private CompleteMarketsCache completeMarketsCache;

	public RunnerServiceImpl(BetFairService betFairService, CompleteMarketsCache completeMarketsCache) {
		this.betFairService = betFairService;
		this.completeMarketsCache = completeMarketsCache;
	}

	public void setMarketRunnerListener(MarketRunnerListener marketRunnerListener, List<MarketFilter> marketFilters) {
		this.marketRunnerListener = marketRunnerListener;
		this.marketFilters = marketFilters;
	}

	public RunnersSummary analyzeRunners(List<Market> markets, boolean getAllBets) {
		if (marketRunnerListener == null || marketFilters == null) {
			throw new IllegalStateException("Service not configured.");
		}

		boolean currentExecutionSuccess = true;

		RunnersSummary runnersSummary = new RunnersSummary();

		/** Analyzed market runners. Key - marketId */
		Map<Integer, Market> completMarketsMap = new HashMap<Integer, Market>();

		MUBets muBets = null;
		if (getAllBets) {
			List<BFMUBet> bfMUbets = betFairService.getMUBets(BFBetStatus.MU);
			muBets = convertBets(bfMUbets);
			marketRunnerListener.onMUBetsData(muBets);
		}

		/** analyze markets */
		for (Market market : markets) {

			try {
				List<MUBet> marketMUBets=null;
				if (muBets != null) {
					marketMUBets = muBets.getMarketMUBets(market.getMarketData().getMarketId());
				}
				else {
					List<BFMUBet> bfMUBetsForMarket = betFairService.getMUBets(BFBetStatus.MU,market.getMarketData().getMarketId());
					MUBets muBetsForMarket = convertBets(bfMUBetsForMarket);
					marketRunnerListener.onMUBetsData(muBetsForMarket);
					marketMUBets = muBetsForMarket.getMuBets();
				}
				
				boolean marketMatched = MarketFilterMatcher.match(market.getMarketData().getEventHierarchy(), market
						.getMarketData().getMarketName(), market.getBwinPrices(), market.getMarketData().isTurningInPlay(),marketFilters);

				if (marketMatched || marketMUBets.size() > 0) {

					int marketId = market.getMarketData().getMarketId();
					BFMarketRunners bfMarketRunners = betFairService.getMarketRunners(marketId);
					MarketRunners marketRunners = null;
					if (bfMarketRunners != null) {
						marketRunners = MarketRunnersFactory.create(bfMarketRunners);
					}

					/**
					 * Analyze market with runners only and totalToBet on market >0. If no runners then market is
					 * closed/suspended.
					 */
					if (marketRunners != null && marketRunners.getMarketRunners().size() >= 2
							&& marketRunners.getTotalToBet() > 0) {

						market.setMarketRunners(marketRunners);
						market.setMarketMUBets(marketMUBets);

						completMarketsMap.put(marketId, market);

						if (market.getHorseWinMarket() != null) {
							// List<HorseWinMarketRunner> oddsCheckerRunners =
							// oddsCheckerService
							// .getHorseWinMarkerRunners(marketAllData.getHorseWinMarket().getMarketUrl());
							// marketAllData.setOddsCheckerRunners(oddsCheckerRunners);
						}
						marketRunnerListener.onMarketPrices(market);
					}

				}
			} catch (Exception e) {
				runnersSummary.setLastException(e);
				runnersSummary.setLastExceptionDate(new Date(System.currentTimeMillis()));
				currentExecutionSuccess = false;
			}
		}

		runnersSummary.setTimeStamp(new Date(System.currentTimeMillis()));
		runnersSummary.setStatus(currentExecutionSuccess);
		runnersSummary.setMuBets(muBets);
		runnersSummary.setCompleteMarkets(completMarketsMap);
		return runnersSummary;

	}
	
	private MUBets convertBets(List<BFMUBet> bfMUbets) {
		List<MUBet> muBetsList = MUBetFactory.create(bfMUbets);
		/** set selectionName and fullMarkerNAme */
		for (MUBet muBet : muBetsList) {
			Market completeMarket = completeMarketsCache.getCompleteMarket(muBet.getMarketId());
			if (completeMarket != null) {
				muBet.setFullMarketName(completeMarket.getFullMarketName());
				muBet.setSelectionName(completeMarket.getMarketData().getSelectionName(muBet.getSelectionId()));
			}

		}

		MUBets muBets = new MUBets(muBetsList);
		return muBets;
	}
}
