package dk.bot.marketobserver.dao.postgres;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import dk.bot.marketobserver.dao.MarketDetailsDAO;
import dk.bot.marketobserver.model.MarketData;
import dk.bot.marketobserver.model.MarketDetailsRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/daobeans.xml","file:src/test/resources/spring/ds-test.xml" })
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@TransactionConfiguration(transactionManager = "txManager")
@Transactional
public class PostgresMarketDetailsDAOIntegrationTest {

	@Resource(name="marketDetailsDao")
	private MarketDetailsDAO dao;

	@Test
	public void testFindSaveMarketDetails() {

		MarketData marketDetails = getMarketDetails(new Random().nextInt());

		assertNull(dao.findMarketDetails(marketDetails.getMarketId()));

		dao.saveMarketDetails(marketDetails);

		MarketData findMarketDetails = dao.findMarketDetails(marketDetails.getMarketId());

		assertEquals(marketDetails.getMarketId(), findMarketDetails.getMarketId());
		assertEquals(marketDetails.getMenuPath(),findMarketDetails.getMenuPath());
		assertEquals(marketDetails.getEventDate(), findMarketDetails.getEventDate());
		assertEquals(marketDetails.getSuspendTime(), findMarketDetails.getSuspendTime());
		assertEquals(marketDetails.getNumberOfWinners(), findMarketDetails.getNumberOfWinners());
		assertEquals(marketDetails.getNumberOfRunners(), findMarketDetails.getNumberOfRunners());

		
		assertEquals(marketDetails.getRunners().get(0).getSelectionId(), findMarketDetails.getRunners().get(0)
				.getSelectionId());
		assertEquals(marketDetails.getRunners().get(0).getSelectionName(), findMarketDetails.getRunners().get(0)
				.getSelectionName());
		assertEquals(marketDetails.getRunners().get(1).getSelectionId(), findMarketDetails.getRunners().get(1)
				.getSelectionId());
		assertEquals(marketDetails.getRunners().get(1).getSelectionName(), findMarketDetails.getRunners().get(1)
				.getSelectionName());
	}

	/**
	 * MarketDetails are replaced if already exists
	 * @throws InterruptedException 
	 */
	@Test
	public void testSaveMarketDetailsAlreadyExists() throws InterruptedException {

		int marketId = new Random().nextInt();
		
		MarketData marketDetails = getMarketDetails(marketId);
		dao.saveMarketDetails(marketDetails);
		MarketData findMarketDetails = dao.findMarketDetails(marketId);
		assertEquals(marketDetails.getEventDate(), findMarketDetails.getEventDate());

		Thread.sleep(100);
		
		MarketData marketDetails2 = getMarketDetails(marketId);
		dao.saveMarketDetails(marketDetails2);
		MarketData findMarketDetails2 = dao.findMarketDetails(marketId);
		assertFalse(marketDetails.getEventDate().equals(findMarketDetails2.getEventDate()));
	
	}

	private MarketData getMarketDetails(int marketId) {
		
		GregorianCalendar marketTime = (GregorianCalendar) GregorianCalendar.getInstance();
		marketTime.setTimeInMillis(System.currentTimeMillis());

		GregorianCalendar marketSuspendTime = (GregorianCalendar) GregorianCalendar.getInstance();
		marketSuspendTime.setTimeInMillis(System.currentTimeMillis() + 100000);

		List<MarketDetailsRunner> marketRunners = new ArrayList<MarketDetailsRunner>();
		marketRunners.add(new MarketDetailsRunner(1,"name1"));
		marketRunners.add(new MarketDetailsRunner(2,"name2"));
		MarketData marketDetails = new MarketData();
		marketDetails.setMarketId(marketId);
		marketDetails.setMenuPath("English soccer/Premiership");
		marketDetails.setEventDate(marketTime.getTime());
		marketDetails.setSuspendTime(marketSuspendTime.getTime());
		marketDetails.setRunners(marketRunners);
		marketDetails.setNumberOfWinners(2);
		marketDetails.setNumberOfRunners(6);
		
		return marketDetails;
	}

}
