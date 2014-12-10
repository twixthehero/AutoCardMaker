import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class CardPanel extends JPanel
{
	/**
	 * required serial version id
	 */
	private static final long serialVersionUID = -1444286701144383767L;
	
	private static final int rowSize = 5;
	
	private ArrayList<Card> cardList;
	
	public CardPanel()
	{
		try
		{
			cardList = new ArrayList<Card>();
			
			File cards = new File("cards.xls");
			System.out.println(cards.getAbsolutePath());
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(cards));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(1);
			HSSFRow row;
			
			//int rows = sheet.getPhysicalNumberOfRows();
			int rows = 150;
			
			for (int i = 1; i < rows; i++)
			{
				row = sheet.getRow(i);
				
				if (row.getCell(0) == null || row.getCell(0).getStringCellValue().equals("")) break;
				
				if (row != null)
				{
					HSSFCell name = row.getCell(0);
					HSSFCell cost = row.getCell(1);
					HSSFCell type = row.getCell(2);
					HSSFCell desc = row.getCell(3);
					HSSFCell flav = row.getCell(4);
					HSSFCell stat = row.getCell(5);

					String[] typePiece = type.getStringCellValue().split("/");
					String[] costPiece = cost.getStringCellValue().split("/");
					
					Cost cst = new Cost();
					
					try
					{
						for (int k = 0; k < typePiece.length; k++)
							cst.addCost(ECost.getECost(typePiece[k]), Integer.parseInt(costPiece[k]));
						
						Stat s;
						
						if (stat.getStringCellValue().split("/").length == 1)
							s = new Stat(EStat.getEStat(stat.getStringCellValue()));
						else
						{
							String[] piece = stat.getStringCellValue().split("/");
							s = new Stat(EStat.Stats, Integer.parseInt(piece[0]), Integer.parseInt(piece[1]));
						}
						
						cardList.add(new Card(name.getStringCellValue(), cst, desc.getStringCellValue(), flav.getStringCellValue(), s));
					}
					catch (Exception e)
					{
//						e.printStackTrace();
					}
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		int w = Card.w * rowSize + 40;
		int h = (cardList.size() / rowSize + 1) * Card.h;
		setSize(w, h);
		setPreferredSize(new Dimension(w, h));
	}
	
	protected void init()
	{
		for (Card c : cardList)
			c.init(getGraphics());
		
		System.out.println("Done initializing.");
	}
	
	protected void save()
	{
		File dir = new File("cards");
		
		if (!dir.exists())
			dir.mkdir();
		
		for (Card c : cardList)
			try
			{
				ImageIO.write(c.getCardImage(), "png", new FileOutputStream(new File("cards/" + c.getSaveName() + ".png")));
			}
			catch (IOException e) { e.printStackTrace(); }
		
		System.out.println("Done saving");
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		for (int i = 0; i < cardList.size(); i++)
		{
			Graphics2D g2 = (Graphics2D)g.create();
			g2.translate((Card.w + 1) * (i % rowSize), (Card.h + 1) * (i / rowSize));
			
			Card c = cardList.get(i);
			c.draw(g2);
			
			g2.dispose();
		}
	}
}
