import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.*;

/**
 * 
 * @author Alexandre
 *
 */
public class MapView extends JPanel implements Runnable {

	public static final int DELAY = 17;
	public static final int TILE_SIZE = 25;
	public static final int DEFAULT_WIDTH_JPANEL = 500;
	public static final int DEFAULT_HEIGHT_JPANEL = 500;
	
	private int widthJPanel = 0;;
	private int heigthJPanel = 0;
	
	private Thread animator;
	private MapModel dataMap = null;
	private BufferedImage [][] arrayOfBufferedTile = null;
	
	public MapView(MapModel dataMap)
	{
		super();
		this.dataMap = dataMap;
		this.setPreferredSize(new Dimension(dataMap.getMap().length * TILE_SIZE, dataMap.getMap()[0].length * TILE_SIZE));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.requestFocus();
	}
	
	public void addNotify()
	{
		super.addNotify();
		init();
	}
	
	public void init()
	{
		if(animator == null)
		{
			animator= new Thread(this);
			animator.start();
		}
	}
	
	public void drawGrid(Graphics2D g2d,int width,int height,int tileSize)
	{
		g2d.setColor(Color.white);
		
		for(int i = TILE_SIZE; i < width; i += tileSize)
		{
			g2d.drawLine(i, 0, i, height);
		}
		
		for(int i = TILE_SIZE; i < height; i += tileSize)
		{
			g2d.drawLine(0, i, width, i);
		}
	}
	public static BufferedImage [][] convertBufferedTileSetToArrayOfBufferedTile(BufferedImage bf)
	{
		BufferedImage [][] arrayTileImages = new BufferedImage[bf.getWidth()/TILE_SIZE][bf.getHeight()/TILE_SIZE];
		int m = 0,
			n = 0;
		for(int i = 0; i < bf.getWidth(); i += TILE_SIZE)
		{
			for(int j = 0; j < bf.getHeight(); j += TILE_SIZE)
			{
				if(m == arrayTileImages.length) 
				{
					m = 0;
					System.out.println("m: " + m);
				}
				
				if(n == arrayTileImages[m].length) 
				{
					n = 0;
					System.out.println("n: " + n);
				}
				
				BufferedImage tile = bf.getSubimage(i, j, TILE_SIZE, TILE_SIZE);
				BufferedImage newImage = new BufferedImage(
				        tile.getWidth(), tile.getHeight(),
				        BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = newImage.createGraphics();
				g.drawImage(tile, 0, 0, null);
				g.dispose();
				arrayTileImages[m][n] = newImage;
				n++;
			}
			m++;
			n = 0;
		}
		return arrayTileImages;
	}
	public void drawTile(Graphics2D g2d) 
	{
		//on load l'image au complet et l'on draw seulement de x1 -> x2 / y1 ->y2 
		BufferedImage bf = dataMap.getTileSet();
		int x = -1,
			y = -1;
		
		if(bf != null)
		{
			if(arrayOfBufferedTile == null)
			{
				arrayOfBufferedTile = convertBufferedTileSetToArrayOfBufferedTile(bf);
			}
			
			for(int i = 0; i < dataMap.getMap().length; i++)
			{
				for(int j = 0; j < dataMap.getMap()[i].length; j++)
				{
					x = dataMap.getMap()[i][j][0];
					y = dataMap.getMap()[i][j][1];
					if(x != -1 && y != -1)
					{
						bf = null;
						g2d.drawImage(arrayOfBufferedTile[x/TILE_SIZE][y/TILE_SIZE], (i * TILE_SIZE),(j * TILE_SIZE),TILE_SIZE,TILE_SIZE, null);
					}
				}
			}
		}
	}
	
	public void drawSelectionRectangle(Graphics2D g2d)
	{
		g2d.setColor(Color.red);
		Rectangle r = this.dataMap.getSelectionRectangle();
		if(r != null)
		{
			g2d.draw(r);
		}
		else
		{
			drawGrid(g2d, this.getSize().width, this.getSize().height, TILE_SIZE);
			drawTile(g2d);
		}
	}
	
	public void paint(Graphics g)
	{
			Graphics2D g2d = (Graphics2D) g;
			super.paint(g2d);
			
			drawGrid(g2d, this.getSize().width, this.getSize().height, TILE_SIZE);
			drawTile(g2d);
			drawSelectionRectangle(g2d);
			
			Toolkit.getDefaultToolkit().sync();
			
			g2d.dispose();
	}
	
	public void animationCycle()
	{
		//System.out.println("JPanel x: " + this.getSize().width);
		//System.out.println("JPanel y: " + this.getSize().height);
	}

	@Override
	public void run() 
	{
		long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) 
        {
            animationCycle();
            if(dataMap.getNeedToBeUpdate())
            repaint();
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) 
                sleep = 2;
            try 
            {
                Thread.sleep(sleep);
            } 
            catch (InterruptedException e) 
            {
                System.out.println("interrupted");
            }
            
            beforeTime = System.currentTimeMillis();
        }	
	}
	
	public void addListner(MapController c)
	{
		this.addMouseListener(c);
		this.addMouseMotionListener(c);
		this.addKeyListener(c);
	}
}
