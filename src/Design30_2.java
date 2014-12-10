import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class Design30_2 extends Design
{
	private static final boolean blackWhite = true;
	
	private static final int lineMax = 5;
	private static final int widthMod = 25;
	
	//2 cost
	private static Polygon cost1;
	private static Polygon cost2;
	
	//3 cost
	private static Polygon costRed;
	private static Polygon costGreen;
	private static Polygon costBlue;
	
	private static Point costAverage;
	private int nameAverage;
	private int statAverage;
	
	private static Font font;
	private static Font iFont;
	private static Color clear = new Color(1f, 1f, 1f, 0f);
	private static Color gold = new Color(1f, 215 / 255f, 0, 1f);
	private static Color back = new Color(174 / 255f, 135 / 255f, 99 / 255f, 1f);
	private static Color border = new Color(138 / 255f, 105 / 255f, 74 / 255f, 1f);
	private static BasicStroke stroke = new BasicStroke(5);
	
	private boolean centered = true;
	private int descLineCount;
	private int flavLineCount;
	private int lineIndex;
	private int drawX;
	
	private int midY;
	
	public Design30_2(int w, int h)
	{
		super(w, h);
		
		if (blackWhite)
		{
			back = Color.white;
			border = Color.black;
		}
	}
	
	@Override
	public void initAreas()
	{
		costArea = new Polygon(new int[]{w - 60, w, w}, new int[]{0, 0, 30}, 3);
		nameArea = new Polygon(new int[]{0, w - 60, w, 0}, new int[]{0, 0, 30, 30}, 4);
		textArea = new Polygon(new int[]{0, w, w, 90, 0}, new int[]{h / 3 * 2, h / 3 * 2, h, h, h - 45}, 5);
		statArea = new Polygon(new int[]{0, 90, 0}, new int[]{h - 45, h, h}, 3);
		imageArea = new Polygon(new int[]{0, w, w, 0}, new int[]{30, 30, h / 3 * 2, h / 3 * 2}, 4);
		
		midY = textArea.ypoints[2] - textArea.ypoints[1];
		
		cost1 = new Polygon(new int[]{w - 60, w, w - 30}, new int[]{0, 0, 15}, 3);
		cost2 = new Polygon(new int[]{w - 30, w, w}, new int[]{15, 0, 30}, 3);
		
		costRed = new Polygon(new int[]{w, w - 20, w}, new int[]{0, 20, 30}, 3);
		costGreen = new Polygon(new int[]{w, w - 40, w - 20}, new int[]{0, 10, 20}, 3);
		costBlue = new Polygon(new int[]{w, w - 60, w - 40}, new int[]{0, 0, 10}, 3);
		
		//calc point for drawing the cost
		int sumx = 0;
		int sumy = 0;
		for (int i : costArea.xpoints)
			sumx += i;
		for (int i : costArea.ypoints)
			sumy += i;
		
		int x = sumx / costArea.npoints;
		int y = sumy / costArea.npoints;
		
		costAverage = new Point(x, y);
		
		sumx = 0;
		for (int i : nameArea.xpoints)
			sumx += i;
		
		nameAverage = sumx / nameArea.npoints;
		
		sumx = 0;
		for (int i : statArea.ypoints)
			sumx += i;
		
		statAverage = sumx / statArea.npoints;
	}
	
	@Override
	public void calcStrings(Graphics2D g, String description, String flavor)
	{
		pieces = new ArrayList<String>();
		xs = new ArrayList<Integer>();
		flavorPieces = new ArrayList<String>();
		
		StringBuilder sb = new StringBuilder(description);
		
		if (font == null)
			font = g.getFont().deriveFont(16f);
		
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		
		descLineCount = 0;
		int width = w - 14;
		
		while (sb.length() != 0)
		{
			String n = StringUtils.wrap(sb.toString(), fm, width).get(0);
			pieces.add(n);
			sb = new StringBuilder(sb.substring(pieces.get(pieces.size() - 1).length()).trim());
//			System.out.println(width + " | '" + sb.toString() + "'");
			descLineCount++;
			
			if (descLineCount > lineMax)
				width -= widthMod;
		}
		
		if (iFont == null)
			iFont = g.getFont().deriveFont(Font.ITALIC, 16f);
		
		g.setFont(iFont);
		fm = g.getFontMetrics();
		sb = new StringBuilder(flavor);
		
		while (sb.length() != 0)
		{
			String n = StringUtils.wrap(sb.toString(), fm, width).get(0);
			flavorPieces.add(n);
			sb = new StringBuilder(sb.substring(flavorPieces.get(flavorPieces.size() - 1).length()).trim());
			flavLineCount++;
			if (flavLineCount > lineMax)
				width -= widthMod;
		}
		
		if (descLineCount + flavLineCount >= lineMax)
		{
			centered = false;
		}
	}
	
	@Override
	public void draw(Graphics2D g, Card c)
	{
		g.setColor(back);
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
		Set<Entry<ECost, Integer>> costs = c.getCosts();
		
		Entry<ECost, Integer> cost = costs.iterator().next();
		ECost k = cost.getKey();
		
		if (costs.size() == 1)
		{
			switch (k)
			{
			case Red:
				g.setColor(Color.red);
				break;
			case Green:
				g.setColor(Color.green);
				break;
			case Blue:
				g.setColor(new Color(0, 195 / 255f, 1f));
				break;
			default:
				g.setColor(Color.white);
				break;
			}
			
			g.fillPolygon(costArea);
			
			drawBorder((Graphics2D)g);
			
			g.setColor(Color.black);
			g.drawString("" + cost.getValue(), costAverage.x, costAverage.y + 7);
		}
		else
		{
			Iterator<Entry<ECost, Integer>> it;
			
			switch (costs.size())
			{
			case 2:
				it = costs.iterator();
				
				g.setColor(getColor(it.next().getKey()));
				g.fillPolygon(cost1);
				g.setColor(getColor(it.next().getKey()));
				g.fillPolygon(cost2);
				
				drawBorder((Graphics2D)g);

				it = costs.iterator();
				g.setColor(Color.black);
				g.drawString("" + it.next().getValue(), costAverage.x - 10, costAverage.y + 4);
				g.drawString("" + it.next().getValue(), costAverage.x + 6, costAverage.y + 11);
				break;
			case 3:
				g.setColor(Color.red);
				g.fillPolygon(costRed);
				g.setColor(Color.green);
				g.fillPolygon(costGreen);
				g.setColor(Color.blue);
				g.fillPolygon(costBlue);
				
				drawBorder((Graphics2D)g);

				it = costs.iterator();
				g.setColor(Color.black);
				g.drawString("" + it.next().getValue(), costAverage.x, costAverage.y + 7);
				g.drawString("" + it.next().getValue(), costAverage.x, costAverage.y + 7);
				g.drawString("" + it.next().getValue(), costAverage.x, costAverage.y + 7);
				break;
			}
		}
	}
	
	private void drawBorder(Graphics2D g)
	{
		g.setColor(border);
		g.setStroke(stroke);
		g.drawPolygon(costArea);
	}
	
	private Color getColor(ECost c)
	{
		switch (c)
		{
		case Red: return Color.red;
		case Green: return Color.green;
		case Blue: return new Color(0, 191 / 255f, 1f);
		}
		
		return Color.white;
	}
	
	@Override
	public void drawName(Graphics2D g, String name)
	{
		g.setColor(border);
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(stroke);
		g2.drawPolygon(nameArea);
		
		g.setColor(Color.black);
		g.setFont(font.deriveFont(Font.BOLD, font.getSize()));
		FontMetrics fm = g.getFontMetrics();
		g.drawString(name, nameAverage - fm.stringWidth(name) / 2, nameArea.ypoints[0] + fm.getHeight());
	}
	
	@Override
	public void drawStat(Graphics2D g, Stat stat)
	{
		g.setColor(gold);
		g.fillPolygon(statArea);
		
		g.setColor(border);
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(stroke);
		g.drawPolygon(statArea);
		
		g.setColor(Color.black);
		
		if (stat.toString().equals("I"))
			g.drawString(stat.toString(), statArea.xpoints[0] + 10, statAverage + 5);
//		else if (stat.toString().equals("S"))
//			g.drawString(stat.toString(), statArea.xpoints[0], statAverage + 5);
		else
			g.drawString(stat.toString(), statArea.xpoints[0] + 5, statAverage + 5);
	}
	
	@Override
	public void drawText(Graphics2D g)
	{
		g.setColor(border);
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(stroke);
		g2.drawPolygon(textArea);
		
		g.setColor(Color.black);
		g.setFont(font);
		
		if (pieces == null || flavorPieces == null) return;
		
		int y = h / 3 * 2 + 15;
		drawX = 5;
		lineIndex = 0;
		
		if (pieces.size() + flavorPieces.size() + 1 < 7) //center the text
		{
			y = textArea.ypoints[1] + (textArea.ypoints[2] - textArea.ypoints[1]) / 2;
//			System.out.println(y);
			y -= (17 * (pieces.size() + flavorPieces.size())) / 2;
//			System.out.println(y);
			
			for (int i = 0; i < pieces.size(); i++)
			{
				g.drawString(pieces.get(i), drawX, y);
				y += 17;
				lineIndex++;
				
				if (lineIndex > lineMax)
					drawX += widthMod;
			}
			
			y += 17;
			g.setFont(iFont);
			
			for (int i = 0; i < flavorPieces.size(); i++)
			{
				g.drawString(flavorPieces.get(i), drawX, y);
				y += 17;
				lineIndex++;
				
				if (lineIndex > lineMax)
					drawX += widthMod;
			}
		}
		else if (pieces.size() + flavorPieces.size() + 1 < 10) //gap between flavor and desc
		{
			for (int i = 0; i < pieces.size(); i++)
			{
				g.drawString(pieces.get(i), drawX, y);
				y += 17;
				lineIndex++;
				
				if (lineIndex > lineMax)
					drawX += widthMod;
			}
			
			y += 17;
			g.setFont(iFont);
			
			for (int i = 0; i < flavorPieces.size(); i++)
			{
				g.drawString(flavorPieces.get(i), drawX, y);
				y += 17;
				lineIndex++;
				
				if (lineIndex > lineMax)
					drawX += widthMod;
			}
		}
		else if (pieces.size() + flavorPieces.size() < 10) //no gap between flavor and desc
		{
			for (int i = 0; i < pieces.size(); i++)
			{
				g.drawString(pieces.get(i), drawX, y);
				y += 17;
				lineIndex++;
				
				if (lineIndex > lineMax)
					drawX += widthMod;
			}
			
			g.setFont(iFont);
			
			for (int i = 0; i < flavorPieces.size(); i++)
			{
				g.drawString(flavorPieces.get(i), drawX, y);
				y += 17;
				lineIndex++;
				
				if (lineIndex > lineMax)
					drawX += widthMod;
			}
		}
		else //dont draw the flavor text
		{
			for (int i = 0; i < pieces.size(); i++)
			{
				g.drawString(pieces.get(i), drawX, y);
				y += 17;
				lineIndex++;
				
				if (lineIndex > lineMax)
					drawX += widthMod;
			}
		}
	}
	
	@Override
	public void drawImage(Graphics2D g, BufferedImage image)
	{
		g.setColor(border);
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(stroke);
		g2.drawPolygon(imageArea);
		g2.drawImage(image, 0, 0, null);
	}
}
