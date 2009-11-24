package dk.bot.marketobserver.tasks.adrianmassey;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import dk.bot.adrianmassey.model.MasseyHRMarket;
import dk.bot.adrianmassey.model.MasseyHRRunner;
import dk.bot.marketobserver.model.MarketDetailsRunner;

public class MasseyHelperTest {

	SimpleDateFormat df = new SimpleDateFormat("dd mm yyyy HH:mm");
	private List<MasseyHRMarket> massayMarkets = new ArrayList<MasseyHRMarket>();
	
	/**Markets are matched*/
	@Test
	public void testMatch() throws ParseException {
		String bfMenuPath = "\\Horse Racing\\GB\\Bev 23rd Jun";
		Date marketTime = df.parse("23 07 2009 17:15");
		List<MarketDetailsRunner> bfRunners = new ArrayList<MarketDetailsRunner>();
		bfRunners.add(new MarketDetailsRunner(1, "Almuktahem"));
		bfRunners.add(new MarketDetailsRunner(2,"BURNS NIGHT"));
				
		MasseyHRMarket horseWinMarket = new MasseyHRMarket();
		horseWinMarket.setMarketTime(marketTime);
		horseWinMarket.setMeetingName("Beverley");
		List<MasseyHRRunner> marketRunners = new ArrayList<MasseyHRRunner>();
		horseWinMarket.setMarketRunners(marketRunners);
		marketRunners.add(new MasseyHRRunner("ALMUktahem",564));
		marketRunners.add(new MasseyHRRunner("BURNS night",345));
		massayMarkets.add(horseWinMarket);
				
		MasseyPrices match = MasseyHelper.match(10,bfMenuPath, marketTime,1,bfRunners, massayMarkets);
		
		assertEquals(true, match.areAllPricesAvailable());
		assertEquals(10, match.getBfMarketId());
		assertEquals(1.611d, match.getMasseyRunnerPrice(1),0.001);
		assertEquals(2.634d, match.getMasseyRunnerPrice(2),0.001);		
	}
	
}
