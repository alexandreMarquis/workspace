import java.util.ArrayList;

import javax.swing.*;


public class Escapee extends Zombi implements Survivor{
	public final static String DEFAULT_IMAGE_PATH = "C:/Users/Alexandre Marquis-A/Desktop/xml/Game/src/yellow.png";
	private final static int DEFAULT_RANGE_VISION = 300; 
	private final static double START_X = 250;
	private final static double START_Y = 250;
	public ArrayList<MyObject> env;//put private
	private MyObject middle = null;//put private
	private MyObject toFlee = null;//put private
	private MyObject nearest = null;//put private
	private boolean tracked = false;
	private boolean infected = false;
	
	public Escapee(ArrayList<MyObject> env)
	{	
		this(env,START_X, START_Y,DEFAULT_IMAGE_PATH,DEFAULT_RANGE_VISION);
	}
	
	public Escapee(ArrayList<MyObject> env,double pX,double pY,String path,int pRangeVisison)
	{
		super(env, pX,pY,path,pRangeVisison);
		this.env = env;
	}
	
	public void update()
	{	
		objectSeen = look(env);
		
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
		
		if(!isInfected())
		{
			escape(look(env));
			x -= dx;
			y += dy;
		}
		else
		{
			this.setImage(Zombi.DEFAULT_IMAGE_PATH);
			actLikeAZombi(env);
			x += dx;
			y -= dy;
		}
		
		
		
		/*x = dx;
		y -= dy;*/
	}
	
	public void escape(ArrayList<MyObject> arraytoFlee)
	{
		//variable utiliser pour trouver le point milieux
		double m1x = 0;
		double m1y = 0;
		double m2x = 0;
		double m2y = 0;
		
		//variable utiliser pour trouver le point le plus proche
		double ppx = Double.MAX_VALUE;
		double ppy = Double.MAX_VALUE;
		
		//System.out.println(arraytoFlee);		
		//trouver le premier point milieux
		for(MyObject o:arraytoFlee)
		{
			m1x += o.getX();
			m1y += o.getY();
		}
		
		m1x = m1x / arraytoFlee.size();
		m1y = m1y / arraytoFlee.size();
		
		middle = new InanimateTestObject(m1x, m1y,"green");
		
		//trouver le point le plus proche de l'evader 
		for(MyObject o:arraytoFlee)
		{
			if(o.getClass() != this.getClass())
			{
				double x = o.getX() - this.getX();
				double y = o.getY() - this.getY();
				x = x < 0 ? -x:x;
				y = y < 0 ? -y:y;
				
				if((x + y) < (ppx + ppy))
				{
					nearest = o;
					ppx = x;
					ppy = y;
				}
			}
		}
		
		//calculer le point a fuire (point milieu) entre l'evader le premier point milieu et le point le plus proche
		arraytoFlee.clear();
		arraytoFlee.add(this);
		arraytoFlee.add(middle);
		arraytoFlee.add(nearest);
		
		for(MyObject o:arraytoFlee)
		{
			if(o != null)
			{
				tracked = true;
				m2x += o.getX();
				m2y += o.getY();
			}
			else
			{
				tracked = false;	
			}
		}
		
		if(tracked)
		{
			m2x = m2x / arraytoFlee.size();
			m2y = m2y / arraytoFlee.size();
			
			toFlee = new InanimateTestObject(m2x, m2y,"blue");
			double dtx = toFlee.getX() - this.getX();
			double dty = toFlee.getY() - this.getY();
			double angle = 0;
			
			//Calcule de l'angle entre l'objet courant (le tracker) et l'objet recu en parametre (le traquer)
			angle = (Math.atan2(dty, dtx) * 180 / Math.PI) * -1;
			
			//operation magique qui rend le deplacement plus fluide
			angle = angle/55;
			
			dx = Math.cos(angle)*1;
			dy = Math.sin(angle)*1;
		}
		middle = null;
		nearest = null;
	}

	@Override
	public void bitten(Boolean infect) {
		// TODO Auto-generated method stub
		this.infected = infect;
	}

	@Override
	public boolean isInfected() {
		// TODO Auto-generated method stub
		return this.infected;
	}


}
 