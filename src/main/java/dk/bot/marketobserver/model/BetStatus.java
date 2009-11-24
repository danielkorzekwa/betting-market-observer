package dk.bot.marketobserver.model;

/** Represents bet status, e.g. matched, unmatched, for details see BetFair betStatus.
 * 
 * @author daniel
 *
 */
public enum BetStatus {

	C, L, M, MU, S, U, V;

	public String value() {
		return name();
	}

	public static BetStatus fromValue(String v) {
		return valueOf(v);
	}

}
