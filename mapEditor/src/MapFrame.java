import java.awt.*;

import javax.swing.*;

public class MapFrame extends JFrame{
	
	public MapFrame(MapView v,MapController c)
	{	
		super();
		this.setTitle("Map Editor");
		
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenuItem newMap = new JMenuItem("new map");
		JMenuItem loadMap = new JMenuItem("load map");
		JMenuItem saveMap = new JMenuItem("save map");
		JMenuItem loadTile = new JMenuItem("load Tile");
		
		fileMenu.add(newMap);
		fileMenu.add(loadMap);
		fileMenu.add(saveMap);
		fileMenu.add(loadTile);
		
		newMap.addActionListener(c);
		loadMap.addActionListener(c);
		saveMap.addActionListener(c);
		loadTile.addActionListener(c);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		JScrollPane js = new JScrollPane();
		js.getViewport().add(v);
		this.add(js,BorderLayout.CENTER);
		//this.setLayout(new BorderLayout());
		//this.add(v,BorderLayout.CENTER);
		this.pack();
		this.setLocationRelativeTo(null);
	}
}
