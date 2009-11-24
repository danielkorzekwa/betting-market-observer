package dk.bot.marketobserver.model;


/**
 * Represents Exchange or SP bet
 * 
 * @author daniel
 * 
 */
public enum BetCategoryType {

	E, L, M, NONE;

	public String value() {
		return name();
	}

	public static BetCategoryType fromValue(String v) {
		return valueOf(v);
	}

}
