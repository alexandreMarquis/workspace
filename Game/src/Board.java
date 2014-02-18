
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class Board extends JPanel implements Runnable {

	public static final int DELAY = 17;
	private Thread animator;
	private ArrayList<MyObject> objOnTheMap = new ArrayList<MyObject>();
	private boolean isRunning = true;
	//public float i = 5.5f;
	
	public Board()
	{
		super();
		this.addKeyListener(new MyKeyListener());
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
	}
	
	public void addNotify()
	{
		super.addNotify();
		init();
	}
	
	public void init()
	{	
		objOnTheMap.add(new Human(objOnTheMap,100,100,Human.DEFAULT_IMAGE_PATH,100));
		objOnTheMap.add(new Escapee(objOnTheMap,150,150,Escapee.DEFAULT_IMAGE_PATH,100));
		objOnTheMap.add(new Zombi(objOnTheMap,200,200,Zombi.DEFAULT_IMAGE_PATH,25));
		objOnTheMap.add(new Zombi(objOnTheMap,200,220,Zombi.DEFAULT_IMAGE_PATH,25));
		objOnTheMap.add(new Zombi(objOnTheMap,200,240,Zombi.DEFAULT_IMAGE_PATH,25));
		objOnTheMap.add(new Zombi(objOnTheMap,200,260,Zombi.DEFAULT_IMAGE_PATH,25));
		objOnTheMap.add(new Zombi(objOnTheMap,200,280,Zombi.DEFAULT_IMAGE_PATH,25));
		objOnTheMap.add(new Zombi(objOnTheMap,300,300,Zombi.DEFAULT_IMAGE_PATH,25));
		objOnTheMap.add(new Zombi(objOnTheMap,300,220,Zombi.DEFAULT_IMAGE_PATH,25));
		objOnTheMap.add(new Zombi(objOnTheMap,300,240,Zombi.DEFAULT_IMAGE_PATH,25));
		objOnTheMap.add(new Zombi(objOnTheMap,300,260,Zombi.DEFAULT_IMAGE_PATH,25));
		objOnTheMap.add(new Zombi(objOnTheMap,300,280,Zombi.DEFAULT_IMAGE_PATH,25));
		objOnTheMap.add(new Zombi(objOnTheMap,300,300,Zombi.DEFAULT_IMAGE_PATH,25));
		objOnTheMap.add(new Zombi(objOnTheMap,300,210,Zombi.DEFAULT_IMAGE_PATH,25));
		objOnTheMap.add(new Zombi(objOnTheMap,220,200,Zombi.DEFAULT_IMAGE_PATH,25));
		objOnTheMap.add(new Zombi(objOnTheMap,240,200,Zombi.DEFAULT_IMAGE_PATH,25));

		/*for(int x = 0; x<500;x+=5)
		{
			for(int y = 0; y <= 500;y+=5)
			{
					if(x == 0|| x == 495 || y == 0 || y == 470)
					{
						System.out.println(x + " : " + y);
						objOnTheMap.add(new Wall(x, y, Human.DEFAULT_IMAGE_PATH));
					}
			}
		}*/
		
		if(animator == null)
		{
			animator= new Thread(this);
			animator.start();
		}
	}
	public Graphics2D warfog(Graphics2D g2d,ObjectWithVision eyes,MyObject env)
	{
		g2d.setColor(Color.white);
   		if(eyes.objectSeen.contains(env))
   		{
   			float distance,fogIntencity,coeficient;
   			double x,y;
   			
   			x = Math.floor(Math.pow((env.getX() - eyes.getX()),2));
   			y = Math.floor(Math.pow((env.getY() - eyes.getY()),2));
   			
   			//pythagore
   			distance = (float) Math.sqrt(x + y);
   			distance = Math.abs(distance);
			coeficient = 9f/eyes.getRangeVision();
   			fogIntencity = 9-(distance * coeficient); 
   			
   			if(fogIntencity < 0 || Double.isNaN(fogIntencity))
   			{
   				fogIntencity = 0;
   			}
   			
   			if (fogIntencity > 10)
   			{
   				fogIntencity = 10;
   			}
   			
   			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,fogIntencity * 0.1f));
   		}
   		else
   		{
   			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0));
   		}
        
        ((Graphics)g2d).drawImage(env.getImage(),(int)env.getX(),(int)env.getY(),this);
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1));
        ((Graphics)g2d).drawImage(eyes.getImage(),(int)eyes.getX(),(int)eyes.getY(),this); 
        
		return g2d;
	}
	
	public void drawObj(Graphics g)
	{
		for(MyObject o:objOnTheMap)
		{	
			if(o.getClass() != Wall.class)
			{
				Escapee e = (Escapee)objOnTheMap.get(1);
				Human hu = (Human)objOnTheMap.get(0);
				//x1,y1,x2,y2
				g.drawOval((int)hu.getX() - (int)hu.getRangeVision(), (int)hu.getY() - (int)hu.getRangeVision(),
						   (int)hu.getX() + (int)hu.getRangeVision(), (int)hu.getY() + (int)hu.getRangeVision());
				
				Zombi z = (Zombi)objOnTheMap.get(2);
				//System.out.println(z.objectSeen);
				g.drawImage(e.getImage(), (int)e.getX(), (int)e.getY(), this);		
				Graphics2D g2d = (Graphics2D) g;
				g2d = warfog(g2d, e,o);
				g2d = warfog(g2d, hu, o);
			}
		}
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		drawObj(g);
		
		Toolkit.getDefaultToolkit().sync();
	    g.dispose();
	}
	
	public void animationCycle()
	{
		for(MyObject o:objOnTheMap)
		{
			o.update();
		}
	}

	@Override
	public void run() 
	{
		long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (isRunning) 
        {
            animationCycle();
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

	private class MyKeyListener extends KeyAdapter
	{
		public void keyReleased(KeyEvent event)
		{
			((Human)objOnTheMap.get(0)).keyReleased(event);
		}
		
		public void keyPressed(KeyEvent event)
		{
			if(event.getKeyChar() == 32)
			{
				isRunning = !isRunning;
			}
			
			((Human)objOnTheMap.get(0)).keyPressed(event);
		}
		
	}
}
