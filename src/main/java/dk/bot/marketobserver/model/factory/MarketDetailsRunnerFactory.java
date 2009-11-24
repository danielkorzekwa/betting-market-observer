package dk.bot.marketobserver.model.factory;

import java.util.ArrayList;
import java.util.List;

import dk.bot.betfairservice.model.BFMarketDetails;
import dk.bot.betfairservice.model.BFMarketDetailsRunner;
import dk.bot.marketobserver.model.MarketDetailsRunner;

/**
 * Covert BFMarketDetails to MarketDetails.
 * 
 * @author daniel
 * 
 */
public class MarketDetailsRunnerFactory {

	public static List<MarketDetailsRunner> create(List<BFMarketDetailsRunner> bfMarketDetails) {

		List<MarketDetailsRunner> runnerDetailsList = new ArrayList<MarketDetailsRunner>(bfMarketDetails.size());
		for (BFMarketDetailsRunner bfRunnerDetails : bfMarketDetails) {
			MarketDetailsRunner marketDetailsRunner = new MarketDetailsRunner(bfRunnerDetails.getSelectionId(),
					bfRunnerDetails.getSelectionName());
			runnerDetailsList.add(marketDetailsRunner);
		}
		
		return runnerDetailsList;
	}
}
