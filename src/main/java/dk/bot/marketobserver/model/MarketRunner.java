package dk.bot.marketobserver.model;

import java.io.Serializable;
import java.util.List;

public class MarketRunner implements Serializable{
	
	private final int selectionId;
	private final double totalAmountMatched;
	private final double lastPriceMatched;
	private double farSP;
	private double nearSP;
	private double actualSP;
	private final List<RunnerPrice> prices;
	
	/**Last best prices before market is turn in play.  0 if market is not in play.*/
	private double lastPriceToBack=0;
	private double lastPriceToLay=0;
	
	public MarketRunner(int selectionId,double totalAmountMatched,double lastPriceMatched,double farSP,double nearSP,double actualSP,List<RunnerPrice> prices) {
		this.selectionId = selectionId;
		this.totalAmountMatched = totalAmountMatched;
		this.lastPriceMatched = lastPriceMatched;
		this.farSP = farSP;
		this.nearSP = nearSP;
		this.actualSP = actualSP;
		this.prices = prices;
	}

	public int getSelectionId() {
		return selectionId;
	}

	public double getTotalAmountMatched() {
		return totalAmountMatched;
	}

	public double getLastPriceMatched() {
		return lastPriceMatched;
	}

	public double getFarSP() {
		return farSP;
	}

	public double getNearSP() {
		return nearSP;
	}
	
	public void setFarSP(double farSP) {
		this.farSP = farSP;
	}

	public void setNearSP(double nearSP) {
		this.nearSP = nearSP;
	}

	public double getActualSP() {
		return actualSP;
	}
	
	public void setActualSP(double actualSP) {
		this.actualSP = actualSP;
	}

	public List<RunnerPrice> getPrices() {
		return prices;
	}
	
	public double getTotalToBack() {
		double total=0;
		
		for (RunnerPrice price : prices) {
			total = total + price.getTotalToBack();
		}
		return total;
	}
	
	public double getTotalToLay() {
		double total=0;
		
		for (RunnerPrice price : prices) {
			total = total + price.getTotalToLay();
		}
		return total;
	}
	
	/** Returns amount of all offers to back and lay.*/
	public double getTotalToBet() {
		double total=0;
		
		for (RunnerPrice price : prices) {
			total = total + price.getTotalToLay() + price.getTotalToBack();
		}
		return total;
	}
	
	public double getTotalOnPriceToBack() {
		double bestPrice = 1.01d;
		double total = 0;

		for (RunnerPrice price : prices) {
			if (price.getTotalToBack() >= 2 && price.getPrice() > 0) {
				if (price.getPrice() >= bestPrice) {
					bestPrice = price.getPrice();
					total = price.getTotalToBack();
				}
			}
		}
		return total;
	}

	public double getPriceToBack() {
		double bestPrice = 1.01d;

		if (prices != null) {
			for (RunnerPrice price : prices) {
				if (price.getTotalToBack() >= 2 && price.getPrice() > 0) {
					if (price.getPrice() >= bestPrice) {
						bestPrice = price.getPrice();
					}
				}
			}
		}

		return bestPrice;
	}

	public double getTotalOnPriceToLay() {
		double bestPrice = 1000;
		double total = 0;

		for (RunnerPrice price : prices) {
			if (price.getTotalToLay() >= 2 && price.getPrice() > 0) {
				if (price.getPrice() <= bestPrice) {
					bestPrice = price.getPrice();
					total = price.getTotalToLay();
				}
			}
		}
		return total;
	}

	public double getPriceToLay() {
		double bestPrice = 1000;

		if (prices != null) {
			for (RunnerPrice price : prices) {
				if (price.getTotalToLay() >= 2 && price.getPrice() > 0) {
					if (price.getPrice() <= bestPrice) {
						bestPrice = price.getPrice();
					}
				}
			}
		}
		return bestPrice;
	}
	
	@Override
	public String toString() {
		return selectionId + ":" + totalAmountMatched + ":" + lastPriceMatched;
	}

	public double getLastPriceToBack() {
		return lastPriceToBack;
	}

	public void setLastPriceToBack(double lastPriceToBack) {
		this.lastPriceToBack = lastPriceToBack;
	}

	public double getLastPriceToLay() {
		return lastPriceToLay;
	}

	public void setLastPriceToLay(double lastPriceToLay) {
		this.lastPriceToLay = lastPriceToLay;
	}

	/**Get avg price based on best lay/back prices.*/
	public double getAvgPrice() {
		return 1/((1/getPriceToBack() + 1/getPriceToLay())/2);
	}
	
}
