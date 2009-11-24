package dk.bot.marketobserver.util;

/**
 * Bot runtime exception - should be thrown in case of critical error
 * 
 * @author daniel
 * 
 */
public class BotException extends RuntimeException {

	public BotException(String message) {
		super(message);
	}
	
	public BotException(String message, Throwable t) {
		super(message,t);
	}
}
