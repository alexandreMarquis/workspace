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
	private int[][][] map = new int[20][20][2];
	private BufferedImage tileSet = null;
	private int[] selectedTile = { 0, 0 };
	private boolean needUpdate = true;
	private boolean isTileSetLoad = false;
	private String tileSetPath = null;

	public MapModel() {
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
		System.out.println("Y int : " + y);
		this.selectedTile[0] = (x / TILE_SIZE);
		this.selectedTile[1] = (y / TILE_SIZE);
		System.out.println("Tile Selector: " + this.selectedTile[0] + " : "	+ this.selectedTile[1]);
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
		
		if(tileSetPath == null)
		{
			returnVal = chooser.showOpenDialog(null);
		}
		else
		{
			returnVal = JFileChooser.APPROVE_OPTION;
		}
	
		if (returnVal == JFileChooser.APPROVE_OPTION) 
		{
			if(tileSetPath == null)
				tileSetPath = chooser.getSelectedFile().getPath();
			
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
				bw.write("Dimension:"+map.length+","+map[0].length+";");
				bw.newLine();
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
				
				
				//size of the map
				line = br.readLine();
				char c = line.charAt(index);
			
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
				//debug
				/*for(int i = 0; i < loadedMap.length; i++)
				{
					for(int j = 0; j < loadedMap[i].length; j++)
					{
						System.out.print("[" + loadedMap[i][j][0] + ":" + loadedMap[i][j][1]+ "]");
					}
					System.out.println("\n");
				}*/
				
				map = loadedMap.clone();
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
