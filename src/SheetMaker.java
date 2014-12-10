import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SheetMaker
{
	private final int START_X = 37;
	private final int START_Y = 48;
	
	private final int DIF_X = 37;
	private final int DIF_Y = 49;
	
	private BufferedImage background;
	
	private File cardDir = new File("cards/");
	private ArrayList<BufferedImage> cards;
	
	private File sheetsDir = new File("sheets/");
	private ArrayList<BufferedImage> sheets;
	
	public SheetMaker()
	{
		loadBackground();
		loadCards();
		
		createSheets();
		saveSheets();
	}
	
	private void loadBackground()
	{
		try
		{
			background = ImageIO.read(new File("bordersDouble.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void loadCards()
	{
		cards = new ArrayList<BufferedImage>();
		
		File[] c = cardDir.listFiles();
		
		for (File f : c)
			try
			{
				cards.add(ImageIO.read(f));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
	}
	
	private void createSheets()
	{
		int numSheets = cards.size() / 8;
		
		if (cards.size() % 8 != 0)
			numSheets++;
		
		sheets = new ArrayList<BufferedImage>();
		
		BufferedImage tmp;
		
		for (int i = 0; i < numSheets; i++)
		{
			tmp = new BufferedImage(background.getWidth(), background.getHeight(), BufferedImage.TYPE_INT_ARGB);
			tmp.createGraphics().drawImage(background, 0, 0, null);
			sheets.add(tmp);
		}
		
		for (BufferedImage img : sheets)
		{
			int x = START_X * 2;
			
			for (int i = 0; i < 4; i++)
			{
				if (cards.isEmpty()) break;
				
				Graphics g = img.createGraphics();
//				g.drawImage(cards.remove(0), x + i * Card.w / 2, START_Y, Card.w / 2, Card.h / 2, null);
//				g.drawImage(cards.remove(0), x + i * Card.w / 2, START_Y + Card.h / 2 + DIF_Y, Card.w / 2, Card.h / 2, null);
				g.drawImage(cards.remove(0), x + i * Card.w, START_Y * 2, Card.w, Card.h, null);
				
				if (cards.isEmpty()) break;
				g.drawImage(cards.remove(0), x + i * Card.w, START_Y * 2 + Card.h + DIF_Y * 2, Card.w, Card.h, null);
				g.dispose();
				
				x += DIF_X * 2;
			}
		}
	}
	
	private void saveSheets()
	{
		if (!sheetsDir.exists())
			sheetsDir.mkdir();
		
		for (int i = 0; i < sheets.size(); i++)
		{
			try
			{
				ImageIO.write(sheets.get(i), "png", new File("sheets/sheet" + i + ".png"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) { new SheetMaker(); }
}
