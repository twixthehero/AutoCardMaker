import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public interface IDesign
{
	void initAreas();
	void calcStrings(Graphics2D g, String description, String flavor);
	void draw(Graphics2D g, Card c);
	void drawName(Graphics2D g, String name);
	void drawCost(Graphics2D g, Cost cost);
	void drawImage(Graphics2D g, BufferedImage image);
	void drawText(Graphics2D g);
	void drawStat(Graphics2D g, Stat stat);
}
