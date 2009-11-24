package dk.bot.marketobserver.dao.postgres;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import dk.bot.marketobserver.dao.MarketDetailsDAO;
import dk.bot.marketobserver.model.MarketData;
import dk.bot.marketobserver.model.MarketDetailsRunner;
import dk.bot.marketobserver.util.BotException;

public class PostgresMarketDetailsDAO extends SimpleJdbcDaoSupport implements MarketDetailsDAO {

	
	public MarketData findMarketDetails(int marketId) {
		String SQL_FIND_MARKET_DETAILS = "select * from market_details where market_id=?";
		String SQL_FIND_MARKET_DETAILS_RUNNER = "select * from market_details_runner where market_id=?";

		ParameterizedRowMapper<MarketData> MarketDataMapper = getMarketDataMapper();
		List<MarketData> MarketDataList = getSimpleJdbcTemplate().query(SQL_FIND_MARKET_DETAILS,
				MarketDataMapper, marketId);

		if (MarketDataList.size() == 0) {
			return null;
		} else if (MarketDataList.size() == 1) {

			MarketData MarketData = MarketDataList.get(0);

			ParameterizedRowMapper<MarketDetailsRunner> runnerMapper = getRunnerMapper();
			List<MarketDetailsRunner> runners = getSimpleJdbcTemplate().query(SQL_FIND_MARKET_DETAILS_RUNNER,
					runnerMapper, marketId);
			
			MarketData.getRunners().addAll(runners);

			return MarketData;

		} else {
			throw new BotException("More than one MarketData exists for market_id: " + marketId);
		}

	}

	private ParameterizedRowMapper<MarketData> getMarketDataMapper() {
		ParameterizedRowMapper<MarketData> MarketDataMapper = new ParameterizedRowMapper<MarketData>() {

			public MarketData mapRow(ResultSet rs, int rowNum) throws SQLException {
				int marketId = rs.getInt("market_id");
				String menuPath = rs.getString("menu_path");
				Date marketTime = rs.getTimestamp("market_time");
				Date marketSuspendTime = rs.getTimestamp("market_suspend_time");
				String sportName = rs.getString("sport_name");
				String regionName = rs.getString("region_name");
				int numOfWinners = rs.getInt("num_of_winners");
				int numOfRunners = rs.getInt("num_of_runners");
				
				MarketData marketData = new MarketData();
				marketData.setMarketId(marketId);
				marketData.setMenuPath(menuPath);
				marketData.setEventDate(marketTime);
				marketData.setSuspendTime(marketSuspendTime);
				marketData.setRunners(new ArrayList<MarketDetailsRunner>());
				marketData.setNumberOfWinners(numOfWinners);
				marketData.setNumberOfRunners(numOfRunners);

				return marketData;
			}

		};
		return MarketDataMapper;
	}

	private ParameterizedRowMapper<MarketDetailsRunner> getRunnerMapper() {
		ParameterizedRowMapper<MarketDetailsRunner> runnerMapper = new ParameterizedRowMapper<MarketDetailsRunner>() {

			public MarketDetailsRunner mapRow(ResultSet rs, int rowNum) throws SQLException {

				int selectionId = rs.getInt("selection_id");
				String selectionName = rs.getString("selection_name");

				MarketDetailsRunner MarketDataRunner = new MarketDetailsRunner(selectionId, selectionName);

				return MarketDataRunner;
			}

		};

		return runnerMapper;
	}

	@Transactional
	public void saveMarketDetails(MarketData marketData) {
		String SQL_SAVE_MARKET_DETAILS = "insert into market_details(market_id,menu_path,market_time,market_suspend_time,sport_name,region_name,num_of_winners,num_of_runners) values (?,?,?,?,?,?,?,?)";
		String SQL_SAVE_MARKET_DETAILS_RUNNER = "insert into market_details_runner(market_id,selection_id,selection_name) values(?,?,?)";
		String SQL_DROP_MARKET_DETAILS = "delete from market_details where market_id=?";
		
		/** MarketData are replaced if already exists*/
		MarketData findMarketData = findMarketDetails(marketData.getMarketId());
		if(findMarketData!=null) {
			getSimpleJdbcTemplate().update(SQL_DROP_MARKET_DETAILS, marketData.getMarketId());
		}
		
		getSimpleJdbcTemplate().update(SQL_SAVE_MARKET_DETAILS, marketData.getMarketId(),marketData.getMenuPath(),marketData.getEventDate(),marketData.getSuspendTime(),"none","none",marketData.getNumberOfWinners(),marketData.getNumberOfRunners());
		
		for(MarketDetailsRunner runner : marketData.getRunners()) {
			getSimpleJdbcTemplate().update(SQL_SAVE_MARKET_DETAILS_RUNNER, marketData.getMarketId(),runner.getSelectionId(),runner.getSelectionName());
		}
	}

}
