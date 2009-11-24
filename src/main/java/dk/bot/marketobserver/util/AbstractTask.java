package dk.bot.marketobserver.util;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;


/**Provide task execution statistics.
 * 
 * @author daniel
 *
 */
public abstract class AbstractTask {

	private BeanStat beanStat;
	
	public AbstractTask(String taskName) {
		beanStat = new BeanStat(taskName);
	}
	
	public void execute() {
		boolean status = true;
		try {
			beanStat.executionStarted(new Date(System.currentTimeMillis()));
			doExecute();
		} catch (Exception e) {
			status = false;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				throw new RuntimeException(e);
			}
			beanStat.setLastException(ExceptionUtils.getMessage(e));
			beanStat.setLastExceptionDetails(ExceptionUtils.getFullStackTrace(e));
			beanStat.setLastExceptionDate(new Date(System.currentTimeMillis()));
		} finally {
			beanStat.executionFinished(status, new Date(System.currentTimeMillis()));
		}

	}
	
	public abstract void doExecute();
	
	
	/** Get statistics about service execution.*/
	public BeanStat getBeanStat() {
		return beanStat;
	}
}
