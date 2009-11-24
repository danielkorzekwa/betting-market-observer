package dk.bot.marketobserver.tasks.bwin;

import dk.bot.bwinservice.model.BwinRegionEnum;


public enum BetFairBwinRegionEnum {
	
	SOCCER_EUROPE_INTERTOTTO("/1/19206074/",BwinRegionEnum.SOCCER_EUROPE_INTERTOTTO),
	
	SOCCER_WORLD_WOMAN_INTERNATIONAL("/1/1236850/",BwinRegionEnum.SOCCER_WORLD_WOMAN_INTERNATIONAL),
	
	SOCCER_UK("/1/258597/",BwinRegionEnum.SOCCER_UK),
	SOCCER_GERMANY("/1/257608/",BwinRegionEnum.SOCCER_GERMANY),
	SOCCER_ITALY("/1/241360/",BwinRegionEnum.SOCCER_ITALY),
	SOCCER_SPAIN("/1/257600/",BwinRegionEnum.SOCCER_SPAIN),
	SOCCER_AUSTRIA("/1/260828/",BwinRegionEnum.SOCCER_AUSTRIA),
	SOCCER_FINLAND("/1/485006/",BwinRegionEnum.SOCCER_FINLAND),
	SOCCER_FRANCE("/1/266830/",BwinRegionEnum.SOCCER_FRANCE),
	SOCCER_DENMARK("/1/239215/",BwinRegionEnum.SOCCER_DENMARK),
	SOCCER_GREECE("/1/292087/",BwinRegionEnum.SOCCER_GREECE),
	SOCCER_HUNGARY("/1/695516/",BwinRegionEnum.SOCCER_HUNGARY),
	SOCCER_NORWAY("/1/239251/",BwinRegionEnum.SOCCER_NORWAY),
	SOCCER_POLAND("/1/687962/",BwinRegionEnum.SOCCER_POLAND),
	SOCCER_REPIRELAND("/1/415475/",BwinRegionEnum.SOCCER_REPIRELAND),
	SOCCER_ROMANIA("/1/6004851/",BwinRegionEnum.SOCCER_ROMANIA),
	SOCCER_RUSSIA("/1/682395/",BwinRegionEnum.SOCCER_RUSSIA),
	SOCCER_SCOTLAND("/1/2695886/",BwinRegionEnum.SOCCER_SCOTLAND),
	SOCCER_CZECH("/1/504594/",BwinRegionEnum.SOCCER_CZECH),
	SOCCER_SWEDEN("/1/239244/",BwinRegionEnum.SOCCER_SWEDEN),
	SOCCER_SWISS("/1/259328/",BwinRegionEnum.SOCCER_SWISS),
	SOCCER_TURKEY("/1/272252/",BwinRegionEnum.SOCCER_TURKEY),
	SOCCER_BRAZIL("/1/268451/",BwinRegionEnum.SOCCER_BRAZIL),
	SOCCER_BELGIUM("/1/268422/",BwinRegionEnum.SOCCER_BELGIUM),
	SOCCER_NETHERLANDS("/1/268418/",BwinRegionEnum.SOCCER_NETHERLANDS),
	SOCCER_PORTUGAL("/1/269458/",BwinRegionEnum.SOCCER_PORTUGAL),
	SOCCER_ARGENTINA("/1/268452/",BwinRegionEnum.SOCCER_ARGENTINA),
	SOCCER_USA("/1/503863/",BwinRegionEnum.SOCCER_USA),
	SOCCER_MEXICO("/1/467610/",BwinRegionEnum.SOCCER_MEXICO),
	SOCCER_CROATIA("/1/677243/",BwinRegionEnum.SOCCER_CROATIA),
	SOCCER_SLOVAKIA("/1/684215/",BwinRegionEnum.SOCCER_SLOVAKIA),
	SOCCER_JAPAN("/1/264750/",BwinRegionEnum.SOCCER_JAPAN),
	SOCCER_ICELAND("/1/549333/",BwinRegionEnum.SOCCER_ICELAND),
	
	BASKETBALL_NBA("/7522/13827455/",BwinRegionEnum.BASKETBALL_NBA),
	BASKETBALL_WNBA("/7522/2141913/",BwinRegionEnum.BASKETBALL_WNBA),
	BASKETBALL_TURKEY("/7522/4626053/",BwinRegionEnum.BASKETBALL_TURKEY),
	BASKETBALL_ITALY("/7522/323832/",BwinRegionEnum.BASKETBALL_ITALY),
	//BASKETBALL_FRANCE("/7522/3924587/",BwinRegionEnum.BASKETBALL_FRANCE),
	BASKETBALL_POLAND("/7522/4519442/",BwinRegionEnum.BASKETBALL_POLAND),
	//BASKETBALL_GREECE("/7522/326617/",BwinRegionEnum.BASKETBALL_GREECE),
	BASKETBALL_SPAIN("/7522/1042900/",BwinRegionEnum.BASKETBALL_SPAIN),
	
	TENNIS_WORLD("/2/",BwinRegionEnum.TENNIS_WORLD),
	
	VOLLEYBALL_WORLD_MEN_WORLD_LEAGUE_2008("/998917/5823287/",BwinRegionEnum.VOLLEYBALL_WORLD_MEN_WORLD_LEAGUE_2008),
	
	BASEBALL_NORTH_AMERICA_MLB2008("/7511/18100540/",BwinRegionEnum.BASEBALL_NORTH_AMERICA_MLB2008),
	
	HORSERACING_GB("/7/298251/",null),
	HORSERACING_IRE("/7/298252/",null),
	HORSERACING_USA("/7/133930/",null),
	
	GREYHOUND_ALL("/4339/",null);
	
	private final String betFairEvent;
	private final BwinRegionEnum bwinRegion;
		
	BetFairBwinRegionEnum(String betFairEvent,BwinRegionEnum bwinRegion)
	{
		this.betFairEvent = betFairEvent;
		this.bwinRegion = bwinRegion;
		
	}

	public String getBetFairEvent() {
		return betFairEvent;
	}

	public BwinRegionEnum getBwinRegion() {
		return bwinRegion;
	}
	
	public static BetFairBwinRegionEnum getRegion(String eventPath) {

		for (BetFairBwinRegionEnum marketEnum : BetFairBwinRegionEnum.values()) {
			if (eventPath.startsWith(marketEnum.getBetFairEvent())) {
				return marketEnum;
			}
		}

		return null;
	}
	
	public static BetFairBwinRegionEnum getRegion(BwinRegionEnum bwinRegion) {

		for (BetFairBwinRegionEnum marketEnum : BetFairBwinRegionEnum.values()) {
			if (marketEnum.getBwinRegion().equals(bwinRegion)) {
				return marketEnum;
			}
		}

		return null;
	}
	
	/**If region is null then obtain sport name from menuPath.
	 * 
	 * @param betFairRegion
	 * @return
	 */
	public static String getSportName(BetFairBwinRegionEnum betFairRegion,String menuPath) {
		if(betFairRegion!=null) {
			return betFairRegion.name().split("_")[0];
			}
			else {
				if(menuPath!=null) {
					return menuPath.split("\\\\")[1];
				}
				else {
					return "UNKNOWN";
				}			
			}
	}
	
	/**If region is null then obtain region name from menuPath.
	 * 
	 * @param betFairRegion
	 * @param menuPath
	 * @return
	 */
	public static String getRegionName(BetFairBwinRegionEnum betFairRegion,String menuPath) {
		if(betFairRegion!=null) {
			return betFairRegion.name().substring(betFairRegion.name().indexOf("_")+1);
			}
			else {
				if(menuPath!=null) {
					return menuPath.split("\\\\")[2];
				}
				else {
					return "UNKNOWN";
				}
			}
	}
}
