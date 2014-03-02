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
	private Thread animator;
	private MapModel dataMap = null;
	
	public MapView(MapModel dataMap)
	{
		super();
		this.dataMap = dataMap;
		this.setPreferredSize(new Dimension(500, 500));
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
	
	public void drawTile(Graphics2D g2d) 
	{
		BufferedImage bf = dataMap.getTileSet();
		int x = -1,
			y = -1;
		
		if(bf != null)
		{
			for(int i = 0; i < dataMap.getMap().length; i++)
			{
				for(int j = 0; j < dataMap.getMap()[i].length; j++)
				{
					x = dataMap.getMap()[i][j][0];
					y = dataMap.getMap()[i][j][1];
					if(x != -1 && y != -1)
					{
						bf = dataMap.getTile(x, y);
						System.out.println("Map TILE: " + x + " : " + y);
						g2d.drawImage(bf,(i * TILE_SIZE),(j * TILE_SIZE),TILE_SIZE,TILE_SIZE,null);
					}
				}
			}
		}	
	}
	
	public void paint(Graphics g)
	{
		
			Graphics2D g2d = (Graphics2D) g;
			super.paint(g2d);
			
			drawGrid(g2d, this.getSize().width, this.getSize().height, TILE_SIZE);
			drawTile(g2d);
			
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
