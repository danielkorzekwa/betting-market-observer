package dk.bot.marketobserver.model;

import java.io.Serializable;


/** List of available offers to back/lay on runner
 * 
 * @author daniel
 *
 */
public class RunnerPrice implements Serializable{

	private final double price;

	private final double totalToBack;
	
	private final double totalToLay;

	public RunnerPrice(double price,double totalToBack,double totalToLay) {
		this.price=price;
		this.totalToBack=totalToBack;
		this.totalToLay=totalToLay;
	}

	public double getPrice() {
		return price;
	}

	public double getTotalToBack() {
		return totalToBack;
	}

	public double getTotalToLay() {
		return totalToLay;
	}
}
