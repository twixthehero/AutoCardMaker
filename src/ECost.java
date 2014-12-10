
public enum ECost
{
	Red, Green, Blue, White;
	
	public static ECost getECost(String c)
	{
		for (ECost cost : ECost.values())
			if (c.equals(cost.toString()))
				return cost;
		
		return ECost.White;
	}
}
