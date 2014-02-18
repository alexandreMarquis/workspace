import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Human extends Zombi implements Survivor{
	public final static String DEFAULT_IMAGE_PATH = "C:/Users/Alexandre Marquis-A/Desktop/xml/Game/src/white.png";
	private final static int DEFAULT_RANGE_VISION = 50;
	private final static double START_X = 250;
	private final static double START_Y = 250;
	public ArrayList<MyObject> env;
	private boolean infected = false;
	
	//test;
	public int variation = 1;
	public int random = (int)(Math.random() * (120 - 50) + 50);
	
	public Human(ArrayList<MyObject> env)
	{	
		this(env,START_X, START_Y,DEFAULT_IMAGE_PATH,DEFAULT_RANGE_VISION);
	}
	
	public Human(ArrayList<MyObject> env,double pX,double pY,String path,int pRangeVisison)
	{
		super(env, pX,pY,path,pRangeVisison);
		this.env = env;	
	}
	
	public void update()
	{	
		//to delete
		if(x > 500)
		{
			x = 0;
		} else if (x < 0)
		{
			x = 500;
		}
		
		if(y > 500)
		{
			y = 0;
		} else if (y < 0)
		{
			y = 500;
		}
		////////////
		objectSeen = look(env);
		//injured();
		if(isInfected())
		{
			this.setImage(Zombi.DEFAULT_IMAGE_PATH);
		}
		
		this.x += dx;
		this.y += dy;
	}
	
	public void injured()
	{
		//si le joueur à perdu beaucoup de point de vie, baisser son range de de vision minimum 
		if(this.rangeVision == random)
		{
			random = (int)(Math.random() * (120 - 70) + 70);
			//System.out.println("New random: " + random); 
		}
		else if(rangeVision == 120 || rangeVision == 70)
		{
			//System.out.println("variation");
			variation *= -1;
			rangeVision += variation;
		}
		else
		{
			rangeVision += variation;
			this.setVision(rangeVision);
			//System.out.println("Range: " + rangeVision);
		}

	}
	
	public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
        {
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT)
        {
            dx = 2;
        }
        
        if (key == KeyEvent.VK_UP)
        {
            dy = -2;
        }

        if (key == KeyEvent.VK_DOWN)
        {
            dy = 2;
        }

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
        {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT)
        {
            dx = 0;
        }
        
        if (key == KeyEvent.VK_UP)
        {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN)
        {
            dy = 0;
        }
    }
    
	@Override
	public void bitten(Boolean infect) {
		this.infected = infect;
	}

	@Override
	public boolean isInfected() {
		// TODO Auto-generated method stub
		return this.infected;
	}

}
