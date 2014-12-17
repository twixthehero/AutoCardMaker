import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JEditorPane;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class FlavorPane extends JEditorPane
{
	private String text;
	
	public FlavorPane(String text, int w, int h)
	{
		super("text/html", "<em>" + text + "</em>");
		
		this.text = text;
		
		w -= 10;
		h -= 10;
		
		setMinimumSize(new Dimension(w, h));
		setPreferredSize(new Dimension(w, h));
		setSize(w, h);
		
		StyledDocument doc = (StyledDocument)getDocument();
		Element e = doc.getParagraphElement(0);
//		System.out.println(e + " | " + e.getEndOffset() + " | " + e.getStartOffset());
		
		MutableAttributeSet mas = new SimpleAttributeSet();
		StyleConstants.setLineSpacing(mas, 0f);
		
		doc.setParagraphAttributes(0, e.getEndOffset() - e.getStartOffset(), mas, false);
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.translate(95, Card.h - 45);
		
		super.paint(g);
		
		g.translate(-95, -(Card.h - 45));
	}
}
