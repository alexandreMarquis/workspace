import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.html.HTMLEditorKit.Parser;

/**
 * 
 * @author Alexandre
 * 
 */
public class MapModel {
	public static final int TILE_SIZE = 25;
	public static final int DEFAULT_MAP_SIZE_WIDTH = 20;
	public static final int DEFAULT_MAP_SIZE_HEIGHT= 20;
	
	private int[][][] map = null;
	private BufferedImage tileSet = null;
	private int[] selectedTile = { 0, 0 };
	private boolean needUpdate = true;
	private boolean isTileSetLoad = false;
	private String tileSetPath = "";
	private Rectangle selectionRectangle = null;
	
	public MapModel() {
		map = new int[DEFAULT_MAP_SIZE_WIDTH][DEFAULT_MAP_SIZE_HEIGHT][2];
		init();
	}

	public void init() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j][0] = -1;
				map[i][j][1] = -1;
			}
		}
	}

	public void addTile(int x, int y) {
		int x1 = (x / TILE_SIZE);
		int y1 = (y / TILE_SIZE);
		map[x1][y1] = this.selectedTile.clone();
	}

	public void setSelectedTile(int x, int y) {
		this.selectedTile[0] = (x / TILE_SIZE);
		this.selectedTile[1] = (y / TILE_SIZE);
	}
	
	public void setSelectedTile(int[][][] selectedTiles)
	{
		for (int i = 0; i < selectedTiles.length; i++) {
			for (int j = 0; j < selectedTiles[i].length; j++) {

			}
		}
	}
	
	public void setTileSetLoad(boolean b) {
		isTileSetLoad = b;
	}

	public boolean getNeedToBeUpdate() {
		boolean ntbu = this.needUpdate;

		if (this.needUpdate) {
			this.needUpdate = false;
		}

		return ntbu;
	}

	public void setNeedToBeUpdate(boolean ntbu) {
		this.needUpdate = ntbu;
	}

	public int[][][] getMap() {
		return map;
	}
	
	public boolean isTileSetLoad() {
		return isTileSetLoad;
	}

	public BufferedImage getTileSet() {
		return this.tileSet;
	}
	
	public void setSelectionRectangle(int x1, int y1, int x2, int y2, boolean setItNull) {
		
		if(setItNull)
		{
			this.selectionRectangle = null;
		}
		else
		{
			if(x1 > x2)
			{
				int temp = x1;
				x1 = x2;
				x2 = temp;
			}
			
			if(y1 > y2)
			{
				int temp = y1;
				y1 = y2;
				y2 = temp;
			}
			
			if(this.selectionRectangle == null)
			{
				this.selectionRectangle = new Rectangle(x1, y1, (x2 - x1), (y2 - y1));
			}
			else
			{
				this.selectionRectangle.setLocation(x1, y1);
				this.selectionRectangle.setSize((x2 - x1), (y2 - y1));
			}
		}
	}
	
	public Rectangle getSelectionRectangle() {
		return this.selectionRectangle;
	}

	public BufferedImage getTile(int x, int y) {
		int x1 = (x * TILE_SIZE);
		int y1 = (y * TILE_SIZE);
		return this.tileSet.getSubimage(x1, y1, TILE_SIZE, TILE_SIZE);
	}

	public void loadTile() {
		int returnVal;
		JFileChooser chooser = new JFileChooser("C:\\Users\\Alexandre\\Desktop\\");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG,GIF,PNG Images", "jpg", "gif", "png");
		chooser.setFileFilter(filter);
		
		if(tileSetPath == "")
		{
			returnVal = chooser.showOpenDialog(null);
		}
		else
		{
			returnVal = JFileChooser.APPROVE_OPTION;
		}
	
		if (returnVal == JFileChooser.APPROVE_OPTION) 
		{
			if(tileSetPath == "")
			{
				tileSetPath = chooser.getSelectedFile().getPath();
			}
			
			try 
			{
				tileSet = ImageIO.read(new File(tileSetPath));
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void saveMap() {
		JFileChooser chooser = new JFileChooser("C:\\Users\\Alexandre\\Desktop");

	    int returnVal = chooser.showSaveDialog(null);
	    
	    if(returnVal == JFileChooser.APPROVE_OPTION) 
	    {
	    	String path = chooser.getSelectedFile().getPath();
	    	BufferedWriter bw = null;
	    	int x = 0,
	    		y = 0;
	    	try 
	    	{
	    		File file = new File(path);
	    		 
				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
				
				//le path du tileSet
				bw.write("TileSetPath:"+this.tileSetPath+";");
				bw.newLine();
				
				
				//dimension de la map
				bw.write("Dimension:"+map.length+","+map[0].length+";");
				bw.newLine();
				
				//la map
				bw.write("[");
				for(int i = 0; i < map.length; i++)
				{
					for(int j = 0; j < map[i].length; j++)
					{
						x = map[i][j][0];
						y = map[i][j][1];
						
						bw.write("[" + x + "," + y + "]");	
					}
					
					if(map.length - 1 != i)
						bw.newLine();
				}
				bw.write("]");
				bw.close();
				
			} 
	    	catch (IOException e) 
	    	{
				try 
				{
					if (bw != null)
						bw.close();
				} 
				catch (IOException ex) 
				{
					ex.printStackTrace();
				}
			}
		}
	}

	public void loadMap() {	
		JFileChooser chooser = new JFileChooser("C:\\Users\\Alexandre\\Desktop");
		int returnVal = chooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String path = chooser.getSelectedFile().getPath();

			BufferedReader br = null;

			try 
			{
				String line = "",
					   strX = "",
					   strY = "";
				int x = 0,
					y = 0,
					index = 0;
				
				br = new BufferedReader(new FileReader(path));
				//load tileSetPath
				line = br.readLine();
				char c = line.charAt(index);
				
				while(c != ':')
				{
					c = line.charAt(++index);
				}
				
				while(line.charAt(index) != ';')
				{
					c = line.charAt(++index);
					if(c != ';')
						tileSetPath += c;
				}
				
				index = 0;
				
				//load size of the map
				line = br.readLine();
				c = line.charAt(index);
			
				while(c != ':')
				{
					c = line.charAt(++index);
				}
				
				while(line.charAt(index) != ',')
				{
					c = line.charAt(++index);
					if(c != ',')
						strX += c;
				}
				
				while(line.charAt(index) != ';')
				{
					c = line.charAt(++index);
					if(c != ';')
						strY += c;
				}
				
				x = Integer.parseInt(strX);
				y = Integer.parseInt(strY);
				
				int[][][] loadedMap = new int[x][y][2];
				x = 0;
				y = 0;
				strX = "";
				strY = "";
				
				//load map
				while ((line = br.readLine()) != null) 
				{
					int i = 0;
					c = line.charAt(i);
					
					while(i < line.length() - 1)
					{
						strX = "";
						strY = "";
						
						while(line.charAt(i) == '[')
						{
							i++;
						}
						
						while(line.charAt(i) != ',')
						{
							if(line.charAt(i) != ',')
								strX += line.charAt(i);
							i++;
						}
						
						while(line.charAt(i) != ']')
						{
							if(line.charAt(i) != ']' && line.charAt(i) != ',')
								strY += line.charAt(i);
							i++;
						}
						
						int [] value = {Integer.parseInt(strX),Integer.parseInt(strY)};
						loadedMap[x][y] = value.clone();
						y++;
						i++;
					}
					x++;
					y = 0;
				}
				
				map = loadedMap.clone();
				
				this.loadTile();
				if(this.getTileSet() != null)
				{
					TileSelectorView v = new TileSelectorView(this);
					TileSelectorController c1 = new TileSelectorController(this);
					v.addListner(c1);
					TileSelectorFrame f = new TileSelectorFrame(v,c1);
					this.setTileSetLoad(true);
				}
				
				setNeedToBeUpdate(true);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			} 
			finally 
			{
				try 
				{
					if (br != null)
						br.close();
				} 
				catch (IOException ex) 
				{
					ex.printStackTrace();
				}
			}
		}
	}
}
