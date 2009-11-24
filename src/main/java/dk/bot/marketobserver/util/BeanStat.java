package dk.bot.marketobserver.util;

import java.io.Serializable;
import java.util.Date;

/**Statistics about bean execution
 * 
 * @author daniel
 *
 */
public class BeanStat implements Serializable {

	private Date currentExecutionStart;
	/** in seconds*/
	private Long lastExecutionDuration;
	private boolean lastExecutionSuccess;
	private int successExecutions = 0;
	private int failureExecutions = 0;
	private final String beanName;
	private String lastException;
	private String lastExceptionDetails;
	private Date lastExceptionDate;
	
	public BeanStat(String beanName) {
		this.beanName = beanName;
	}
	
	public synchronized void executionStarted(Date currentExecutionStart) {
		this.currentExecutionStart = currentExecutionStart;
	}
	
	public synchronized void executionFinished(boolean success,Date currentExecutionEnd) {
		lastExecutionSuccess=success;
		lastExecutionDuration = (currentExecutionEnd.getTime() - currentExecutionStart.getTime())/1000;
		currentExecutionStart=null;
		if(success) {
			successExecutions++;
			}
		else {
			failureExecutions++;
		}
	}

	public synchronized Date getCurrentExecutionStart() {
		return currentExecutionStart;
	}

	public synchronized Long getLastExecutionDuration() {
		return lastExecutionDuration;
	}

	public synchronized boolean isLastExecutionSuccess() {
		return lastExecutionSuccess;
	}

	public synchronized int getSuccessExecutions() {
		return successExecutions;
	}

	public synchronized int getFailureExecutions() {
		return failureExecutions;
	}

	public String getBeanName() {
		return beanName;
	}

	public synchronized String getLastException() {
		return lastException;
	}

	public synchronized void setLastException(String lastException) {
		this.lastException = lastException;
	}

	public String getLastExceptionDetails() {
		return lastExceptionDetails;
	}

	public void setLastExceptionDetails(String lastExceptionDetails) {
		this.lastExceptionDetails = lastExceptionDetails;
	}

	public synchronized Date getLastExceptionDate() {
		return lastExceptionDate;
	}

	public synchronized void setLastExceptionDate(Date lastExceptionDate) {
		this.lastExceptionDate = lastExceptionDate;
	}		
}
