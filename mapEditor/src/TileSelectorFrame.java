import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class TileSelectorFrame extends JFrame
{
	public TileSelectorFrame(TileSelectorView v,TileSelectorController c)
	{	
		super();
		this.setTitle("Tile Selector");
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setResizable(true);
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		
		JScrollPane js = new JScrollPane();
		js.getViewport().add(v);
		this.add(js,BorderLayout.CENTER);
		this.pack();
		this.setLocationRelativeTo(null);
	}
}
