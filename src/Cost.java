import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class Cost
{
	private HashMap<ECost, Integer> costs;
	
	public Cost()
	{
		costs = new HashMap<ECost, Integer>();
	}
	
	public void addCost(ECost c, int amt)
	{
		costs.put(c, amt);
	}
	
	public Set<Entry<ECost, Integer>> getCosts()
	{
		return costs.entrySet();
	}
}
