
public class Stat
{
	private EStat stat;
	private int att = -1;
	private int def = -1;
	
	public Stat(EStat stat)
	{
		this.stat = stat;
	}
	
	public Stat(EStat stat, int at, int df)
	{
		this.stat = stat;
		att = at;
		def = df;
	}
	
	public EStat getStatType() { return stat; }
	public int getAttack() { return att; }
	public int getDefense() { return def; }
	
	@Override
	public String toString()
	{
		if (stat == EStat.Stats)
			return att + "/" + def;
		else
			return stat.toString().substring(0, 1);
	}
}
