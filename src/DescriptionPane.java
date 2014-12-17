import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JEditorPane;

public class DescriptionPane extends JEditorPane
{
	private String text;
	
	public DescriptionPane(String text, int w, int h)
	{
		super("text/html", text);
		
		this.text = text;
		
		w -= 10;
		h -= 10;
		
		setMinimumSize(new Dimension(w, h));
		setPreferredSize(new Dimension(w, h));
		setSize(w, h);
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.translate(5, Card.h / 3 * 2 + 5);
		
		super.paint(g);
		
		g.translate(-5, -(Card.h / 3 * 2 + 5));
	}
}
