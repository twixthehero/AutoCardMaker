import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Design implements IDesign
{
	protected static Polygon nameArea;
	protected static Polygon costArea;
	protected static Polygon imageArea;
	protected static Polygon textArea;
	protected static Polygon statArea;
	
	protected int w;
	protected int h;
	
	protected List<String> pieces;
	protected List<Integer> xs;
	protected List<String> flavorPieces;
	
	public Design(int w, int h)
	{
		this.w = w;
		this.h = h;
	}
	
	@Override
	public void initAreas()
	{
		nameArea = new Polygon(new int[]{0, w, w, 0}, new int[]{0, 0, 25, 25}, 4);
		costArea = new Polygon(new int[]{w - 45, w, w}, new int[]{25, 25, 25 + 45}, 3);
		imageArea = new Polygon(new int[]{0, w - 45, w, w, 0}, new int[]{25, 25, 25 + 45, 25 + h / 2, 25 + h / 4 * 3}, 5);
		textArea = new Polygon(new int[]{0, w, w, 0}, new int[]{h / 4 * 3, h / 2, h, h}, 4);
	}
	
	@Override
	public void calcStrings(Graphics2D g, String description, String flavor)
	{
		pieces = new ArrayList<String>();
		xs = new ArrayList<Integer>();
		
		StringBuilder sb = new StringBuilder(description);
		FontMetrics fm = g.getFontMetrics();
		
		float mod = ((h / 4f * 3 + 25) - (h / 2f + 25)) / (float)w;
		float width = w / 5;
		
		while (sb.length() != 0)
		{
			String n = StringUtils.wrap(sb.toString(), fm, (int)width).get(0);
			pieces.add(n);
			xs.add((int)width);
			sb = new StringBuilder(sb.substring(pieces.get(pieces.size() - 1).length()));
			
			width += mod * 125;
			
			if (width > w) width = w;
		}
	}
	
	@Override
	public void draw(Graphics2D g, Card c)
	{
		g.setColor(Color.white);
		g.fillRect(0, 0, w, h);

		drawImage(g, c.getImage());
		drawName(g, c.getName());
		drawCost(g, c.getCost());
		drawStat(g, c.getStat());
		drawText(g);
	}
	
	@Override
	public void drawCost(Graphics2D g, Cost c)
	{
		g.setColor(Color.green);
		g.fillPolygon(costArea);
	}
	
	@Override
	public void drawStat(Graphics2D g, Stat stat)
	{
		g.setColor(Color.yellow);
		g.fillPolygon(statArea);
	}
	
	@Override
	public void drawName(Graphics2D g, String name)
	{
		g.setColor(Color.orange);
		g.fillPolygon(nameArea);
		g.setColor(Color.black);
		g.drawString(name, 0, 12);
	}
	
	@Override
	public void drawImage(Graphics2D g, BufferedImage image)
	{
		g.setClip(imageArea);
		
		if (image != null)
			g.drawImage(image, 0, 0, w, h, null);
		else
		{
			g.setColor(Color.cyan);
			g.fillRect(0, 0, w, h);
		}
		
		g.setClip(null);
	}
	
	@Override
	public void drawText(Graphics2D g)
	{
		g.setColor(Color.gray);
		g.fillPolygon(textArea);
		
		g.setColor(Color.black);
		
		int y = h / 2 + 50;
		
		for (int i = 0; i < pieces.size(); i++)
		{
			g.drawString(pieces.get(i), w - xs.get(i), y);
			y += 15;
		}
	}
}
