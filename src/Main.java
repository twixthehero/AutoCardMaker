import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Main extends JFrame
{
	/**
	 * required serial version id
	 */
	private static final long serialVersionUID = -3968859671924164532L;
	
	private CardPanel cp;
	
	public Main()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280, 720);
		setTitle("InterMagicka Card Maker");
		
		cp = new CardPanel();
		JScrollPane jsp = new JScrollPane(cp);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(jsp);
		
		pack();
		setVisible(true);
		
		cp.init();
		cp.save();
	}
	
	public static void main(String[] args) { new Main(); }
}
