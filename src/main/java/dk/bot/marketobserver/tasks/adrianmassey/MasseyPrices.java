package dk.bot.marketobserver.tasks.adrianmassey;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import dk.bot.adrianmassey.model.MasseyHRMarket;
import dk.bot.adrianmassey.model.MasseyHRRunner;
import dk.bot.marketobserver.model.MarketDetailsRunner;

/**
 * HR prices from adrianmassay.com
 * 
 * @author daniel
 * 
 */
public class MasseyPrices implements Serializable {

	private Collection<MarketDetailsRunner> bfRunners;
	private MasseyHRMarket masseyMarket;

	/** key - bf selectionId, value - price based on ratings */
	private Map<Integer, Double> masseyPrices = new HashMap<Integer, Double>();
	private final int bfMarketId;

	public MasseyPrices(int bfMarketId) {
		this.bfMarketId = bfMarketId;
	}
	
	public MasseyPrices(int bfMarketId,Collection<MarketDetailsRunner> bfRunners, MasseyHRMarket masseyMarket) {
		this.bfMarketId = bfMarketId;
		this.bfRunners = bfRunners;
		this.masseyMarket = masseyMarket;
		
		calculatePrices();
	}
	
	public int getBfMarketId() {
		return bfMarketId;
	}



	/**
	 * 
	 * @return true if massey prices are available for all betfair runners.
	 */
	public boolean areAllPricesAvailable() {
		return masseyPrices.size() == bfRunners.size();
	}

	public Double getMasseyRunnerPrice(int selectionId) {
		return masseyPrices.get(new Integer(selectionId));
	}

	/** Calculate massey prices from ratings */
	private void calculatePrices() {
		double totalRating = 0;
		for (MasseyHRRunner runner : masseyMarket.getMarketRunners()) {
			totalRating = totalRating + runner.getRating();
		}

		for (MasseyHRRunner masseyRunner : masseyMarket.getMarketRunners()) {
			if (masseyRunner.getRating() > 0) {
				for (MarketDetailsRunner bfRunner : bfRunners) {
					if (bfRunner.getSelectionName().toLowerCase().equals(masseyRunner.getRunnerName().toLowerCase())) {
						double price = 1 / (masseyRunner.getRating() / totalRating);
						masseyPrices.put(bfRunner.getSelectionId(), price);
					}
				}
			}
		}

	}
	
	@Override
	public boolean equals(Object obj) {
		return bfMarketId == ((MasseyPrices)obj).getBfMarketId();
	}
	@Override
	public int hashCode() {
		return bfMarketId;
	}
}
