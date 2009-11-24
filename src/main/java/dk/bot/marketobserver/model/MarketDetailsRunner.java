package dk.bot.marketobserver.model;

import java.io.Serializable;

public class MarketDetailsRunner implements Serializable {

	private final int selectionId;
	private final String selectionName;

	public MarketDetailsRunner(int selectionId, String selectionName) {
		this.selectionId = selectionId;
		this.selectionName = selectionName;
	}

	public int getSelectionId() {
		return selectionId;
	}

	public String getSelectionName() {
		return selectionName;
	}

}
