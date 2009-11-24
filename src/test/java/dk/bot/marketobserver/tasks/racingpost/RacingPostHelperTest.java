package dk.bot.marketobserver.tasks.racingpost;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import dk.bot.marketobserver.model.MarketDetailsRunner;
import dk.bot.racingpost.model.RacingPostMarket;
import dk.bot.racingpost.model.RacingPostRunner;

public class RacingPostHelperTest {

	SimpleDateFormat df = new SimpleDateFormat("dd mm yyyy HH:mm");
	private List<RacingPostMarket> racingPostMarkets = new ArrayList<RacingPostMarket>();
	
	/**Markets are matched
	 * @throws ParseException */
	@Test
	public void testMatch() throws ParseException {
		String bfMenuPath = "\\Horse Racing\\GB\\Bev 23rd Jun";
		Date marketTime = df.parse("23 07 2009 17:15");
		List<MarketDetailsRunner> bfRunners = new ArrayList<MarketDetailsRunner>();
		bfRunners.add(new MarketDetailsRunner(1, "Almuktahem"));
		bfRunners.add(new MarketDetailsRunner(2,"BURNS NIGHT"));
				
		List<RacingPostRunner> marketRunners = new ArrayList<RacingPostRunner>();
		marketRunners.add(new RacingPostRunner("ALMUktahem","6/4"));
		marketRunners.add(new RacingPostRunner("BURNS night","2/1"));
		RacingPostMarket horseWinMarket = new RacingPostMarket("Beverley",marketTime,marketRunners);
		
		racingPostMarkets.add(horseWinMarket);
				
		RacingPostPrices match = RacingPostHelper.match(10,bfMenuPath, marketTime,1,bfRunners, racingPostMarkets);
		
		assertEquals(true, match.areAllPricesAvailable());
		assertEquals(10, match.getBfMarketId());
		assertEquals(2.5d, match.getRacingPostRunnerPrice(1),0.001);
		assertEquals(3d, match.getRacingPostRunnerPrice(2),0.001);	
	}

}
