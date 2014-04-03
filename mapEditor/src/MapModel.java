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

/**
 * 
 * @author Alexandre
<<<<<<< HEAD
 *
=======
 * 
>>>>>>> aa4e0ada3a5ff9d7e217aa0778d7a46c8059e534
 */
public class MapModel {
	public static final int TILE_SIZE = 25;
	private int[][][] map = new int[20][20][2];
	private BufferedImage tileSet = null;
	private int[] selectedTile = { 0, 0 };
	private boolean needUpdate = true;

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
<<<<<<< HEAD
	
	public void setSelectedTile(int x,int y)
	{
=======

	public void setSelectedTile(int x, int y) {
>>>>>>> aa4e0ada3a5ff9d7e217aa0778d7a46c8059e534
		System.out.println("Y int : " + y);
		this.selectedTile[0] = (x / TILE_SIZE);
		this.selectedTile[1] = (y / TILE_SIZE);
		System.out.println("Tile Selector: " + this.selectedTile[0] + " : "
				+ this.selectedTile[1]);
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

	public BufferedImage getTileSet() {
		return this.tileSet;
	}

	public BufferedImage getTile(int x, int y) {
		int x1 = (x * TILE_SIZE);
		int y1 = (y * TILE_SIZE);
		return this.tileSet.getSubimage(x1, y1, TILE_SIZE, TILE_SIZE);
	}

	public void loadTile() {
		JFileChooser chooser = new JFileChooser(
				"C:\\Users\\Alexandre\\Desktop\\");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"JPG,GIF,PNG Images", "jpg", "gif", "png");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String path = chooser.getSelectedFile().getPath();

			try {
				tileSet = ImageIO.read(new File(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void saveMap() {
		JFileChooser chooser = new JFileChooser("C:\\Users\\Alexandre\\Desktop");
<<<<<<< HEAD
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
	    		 
=======
		int returnVal = chooser.showSaveDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String path = chooser.getSelectedFile().getPath();
			BufferedWriter bw = null;

			try {
				File file = new File(path);

>>>>>>> aa4e0ada3a5ff9d7e217aa0778d7a46c8059e534
				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
<<<<<<< HEAD
				
				//traitement
				bw.write("[");
				for(int i = 0; i < map.length; i++)
				{
					for(int j = 0; j < map[i].length; j++)
					{
						x = map[i][j][0];
						y = map[i][j][1];
						
						bw.write("[" + x + "," + y + "]");	
					}
				}
				bw.write("]");
				
								
=======

				// traitement
				bw.write("this is a test.");

>>>>>>> aa4e0ada3a5ff9d7e217aa0778d7a46c8059e534
				bw.close();
			} catch (IOException e) {
				try {
					if (bw != null)
						bw.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public void loadMap() {
		JFileChooser chooser = new JFileChooser("C:\\Users\\Alexandre\\Desktop");
		int returnVal = chooser.showSaveDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String path = chooser.getSelectedFile().getPath();

			BufferedReader br = null;

			try {

				String sCurrentLine;

				br = new BufferedReader(new FileReader(path));

				while ((sCurrentLine = br.readLine()) != null) {
					// traitement
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null) {
						br.close();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
