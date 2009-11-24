package dk.bot.marketobserver.model;

/**Type of bet: back or lay
 * 
 * @author daniel
 *
 */
public enum BetType {

	B, L;

	public String value() {
		return name();
	}

	public static BetType fromValue(String v) {
		return valueOf(v);
	}
}
