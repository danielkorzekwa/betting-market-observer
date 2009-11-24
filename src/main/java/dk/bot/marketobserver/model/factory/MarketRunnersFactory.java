package dk.bot.marketobserver.model.factory;

import java.util.ArrayList;
import java.util.List;

import dk.bot.betfairservice.model.BFMarketRunner;
import dk.bot.betfairservice.model.BFMarketRunners;
import dk.bot.betfairservice.model.BFRunnerPrice;
import dk.bot.marketobserver.model.MarketRunner;
import dk.bot.marketobserver.model.MarketRunners;
import dk.bot.marketobserver.model.RunnerPrice;

/** Covert BFMarketRunners to MarketRunners.
 * 
 * @author daniel
 *
 */
public class MarketRunnersFactory {

	/** Covert BFMarketRunners to MarketRunners.*/
	public static MarketRunners create(BFMarketRunners bfMarketRunners) {
		
		List<MarketRunner> marketRunnersList = new ArrayList<MarketRunner>(bfMarketRunners.getMarketRunners().size());
		
		for(BFMarketRunner bfMarketRunner: bfMarketRunners.getMarketRunners()) {
			List<RunnerPrice> runnerPrices = new ArrayList<RunnerPrice>(bfMarketRunner.getPrices().size());
			for(BFRunnerPrice bfRunnerPrice: bfMarketRunner.getPrices()) {
				runnerPrices.add(new RunnerPrice(bfRunnerPrice.getPrice(),bfRunnerPrice.getTotalToBack(),bfRunnerPrice.getTotalToLay()));
			}
			int selectionId = bfMarketRunner.getSelectionId();
			double totalAmountMatched = bfMarketRunner.getTotalAmountMatched();
			double lastPriceMatched = bfMarketRunner.getLastPriceMatched();
			double farSP = bfMarketRunner.getFarSP();
			double nearSP = bfMarketRunner.getNearSP();
			double actualSP = bfMarketRunner.getActualSP();
			
			marketRunnersList.add(new MarketRunner(selectionId,totalAmountMatched,lastPriceMatched,farSP,nearSP,actualSP,runnerPrices));
		}
		
		MarketRunners marketRunners = new MarketRunners(bfMarketRunners.getMarketId(),marketRunnersList,bfMarketRunners.getInPlayDelay(),bfMarketRunners.getTimestamp());
		return marketRunners;
		
	}
}
