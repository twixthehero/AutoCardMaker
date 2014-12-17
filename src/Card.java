import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.text.html.HTMLDocument;

public class Card
{
//	public static final int w = 152;
//	public static final int h = 233;
	public static final int w = 152 * 2;
	public static final int h = 233 * 2;
//	public static final int w = 240;
//	public static final int h = 332;
	
	private static BufferedImage[] sorcery;
	private static BufferedImage[] creature;
	private static BufferedImage[] instant;
	private static BufferedImage[] training;
	
	private IDesign design;
	
	private String name;
	private Cost cost;
	private String description;
	private String flavor;
	private Stat stat;
	
	private BufferedImage image;
	
	public static JFrame FRAME = new JFrame();
	private DescriptionPane dp;
	private FlavorPane fp;
	
	static
	{
		sorcery = new BufferedImage[4];
		creature = new BufferedImage[4];
		instant = new BufferedImage[4];
		training = new BufferedImage[4];
		
		try
		{
			sorcery[0] = ImageIO.read(new File("images/Sorcery/Red_Sorcery.png"));
			sorcery[1] = ImageIO.read(new File("images/Sorcery/Green_Sorcery.png"));
			sorcery[2] = ImageIO.read(new File("images/Sorcery/Blue_Sorcery.png"));
			sorcery[3] = ImageIO.read(new File("images/Sorcery/White_Sorcery.png"));
			
			creature[0] = ImageIO.read(new File("images/Creature/Red_Creature.png"));
			creature[1] = ImageIO.read(new File("images/Creature/Green_Creature.png"));
			creature[2] = ImageIO.read(new File("images/Creature/Blue_Creature.png"));
			creature[3] = ImageIO.read(new File("images/Creature/White_Creature.png"));
			
			instant[0] = ImageIO.read(new File("images/Instant/Red_Instant.png"));
			instant[1] = ImageIO.read(new File("images/Instant/Green_Instant.png"));
			instant[2] = ImageIO.read(new File("images/Instant/Blue_Instant.png"));
			instant[3] = ImageIO.read(new File("images/Instant/White_Instant.png"));
			
			training[0] = ImageIO.read(new File("images/Training/Red_Training.png"));
			training[1] = ImageIO.read(new File("images/Training/Green_Training.png"));
			training[2] = ImageIO.read(new File("images/Training/Blue_Training.png"));
			training[3] = ImageIO.read(new File("images/Training/White_Training.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public Card(String name, Cost cost, String description, String flavor, Stat stat)
	{
		this.name = name;
		this.cost = cost;
		this.description = description;
		this.flavor = flavor;
		this.stat = stat;
		
//		dp = new DescriptionPane(description, Card.w, Card.h / 3 - 45);
//		fp = new FlavorPane(flavor, Card.w - 90, 45);
//		dp.setBackground(Color.blue);
//		fp.setBackground(Color.red);
//		
//		FRAME.add(dp);
//		FRAME.add(fp);
//		
//		try
//		{
//			String bodyRule = "body { font-family: " + Design30_2.iFont.getFamily() + "; " + "font-size: " + Design30_2.iFont.getSize() + "pt; }";
//			((HTMLDocument)fp.getDocument()).getStyleSheet().addRule(bodyRule);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
		
		design = new Design30_2(w, h);
	}
	
	@Override
	public String toString()
	{
		return "Card[name='" + name + "',cost=" + cost.toString() + ",stat=" + stat.toString() + "]";
	}
	
	public String getName() { return name; }
	public Cost getCost() { return cost; }
	public String getDescription() { return description; }
	public String getFlavor() { return flavor; }
	public Stat getStat() { return stat; }
	
	public BufferedImage getImage()
	{
		switch (stat.getStatType())
		{
		case Stats: return creature[getImageIndex()];
		case Training: return training[getImageIndex()];
		case Sorcery: return sorcery[getImageIndex()];
		case Instant: return instant[getImageIndex()];
		}
		
		return image;
	}
	
	private int getImageIndex()
	{
		//if (cost.getCosts().size() == 1)
		{
			Iterator<Entry<ECost, Integer>> i = cost.getCosts().iterator();
			
			Entry<ECost, Integer> e = i.next();
			
			switch (e.getKey())
			{
			case Red: return 0;
			case Green: return 1;
			case Blue: return 2;
			case White: return 3;
			}
			
			return -1;
		}
		//else
		//{
		//	
		//}
	}
	
	public String getSaveName()
	{
		return name.toLowerCase().replaceAll(" ", "");
	}
	
	/**
	 * Gets a BufferedImage containing the drawn card
	 * @return
	 */
	public BufferedImage getCardImage()
	{
		BufferedImage res = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = res.createGraphics();
		draw(g);
		g.dispose();
		
		return res;
	}
	
	protected void init(Graphics go)
	{
		design.initAreas();
		design.calcStrings((Graphics2D)go, description, flavor);
	}
	
	protected void draw(Graphics2D g)
	{
		design.draw(g, this);
//		design.draw(g, this, dp, fp);
	}
}
