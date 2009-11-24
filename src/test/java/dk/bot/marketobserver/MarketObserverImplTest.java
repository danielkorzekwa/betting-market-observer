package dk.bot.marketobserver;

import java.io.IOException;

import javax.sql.DataSource;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

import dk.bot.betfairservice.BetFairService;
import dk.bot.bwinservice.BWinService;
import dk.bot.marketobserver.marketservice.MarketServiceConfig;
import dk.bot.marketobserver.runnerservice.MarketRunnerListener;

@RunWith(JMock.class)
public class MarketObserverImplTest {

	Mockery context = new JUnit4Mockery();
	
	private BetFairService betfairService = context.mock(BetFairService.class);
	private BWinService bwinService = context.mock(BWinService.class);
	private MarketRunnerListener marketRunnerListener = context.mock(MarketRunnerListener.class);
	private DataSource datasource = context.mock(DataSource.class);
	
	@Test
	public void test() throws InterruptedException, IOException {
		MarketObserverImpl marketObserver = new MarketObserverImpl(betfairService,bwinService,datasource,marketRunnerListener,new MarketServiceConfig());
		
		marketObserver.start();
		
		Thread.sleep(3000);
	
	}
}
