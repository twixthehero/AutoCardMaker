
public enum EStat
{
	Stats, Instant, Sorcery, Training;
	
	public static EStat getEStat(String s)
	{
		for (EStat stat : EStat.values())
			if (s.equals(stat.toString()))
				return stat;
		
		return EStat.Stats;
	}
}
