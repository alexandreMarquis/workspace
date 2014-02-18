import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class Zombi extends ObjectWithVision {
	public final static String DEFAULT_IMAGE_PATH = "C:/Users/Alexandre Marquis-A/Desktop/xml/Game/src/red.png";
	private final static int DEFAULT_RANGE_VISION = 50;
	private final static double START_X = 250;
	private final static double START_Y = 250;
	private ArrayList<MyObject> env;
	protected Survivor target = null;
	//modifier les constructeur pour les images
	public Zombi(ArrayList<MyObject> env)
	{	
		this(env,START_X, START_Y,DEFAULT_IMAGE_PATH,DEFAULT_RANGE_VISION);
	}
	
	public Zombi(ArrayList<MyObject> env,double pX,double pY,String path,int pRangeVisison)
	{
		super(pX,pY,path,pRangeVisison);
		this.env = env;
	}
	
	public void update()
	{
		actLikeAZombi(env);
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
		x += dx;
		y -= dy;
	}
	/*
	 * look()
	 * if(1 des objects dans ca vision est plus proche que l'objet courant s)
	 * 	changer la cible
	 */
	protected void actLikeAZombi(ArrayList<MyObject> env)
	{
		this.objectSeen = look(env);
		
		if(target == null || target.isInfected())
		{
			target = nextTarget(env);
		}
		
		for(MyObject o : env)
		{
			//changer pour aussi mettre la condition que l'objet o n'est pas un humain infecté
			if(((o.getClass() != Zombi.class && !((Survivor) o).isInfected())) && (this.distance((MyObject) o) < this.distance((MyObject)target)))
			{
				target = (Survivor) o;
			}
		}
		
		infectOther(target);
		
		target.bitten(bitte(target));
		
		if(target.isInfected())
		{
			target = null;
		}
		
	}
	
	protected Survivor nextTarget(ArrayList<MyObject> env)
	{
		MyObject s = null;
		
		do
		{
			int random = (int)(Math.random() * (env.size()));
			s = env.get(random);
		}
		while(s.getClass() == Zombi.class || (s.getClass() != Zombi.class && (((Survivor) s).isInfected())));
		
		return (Survivor) s;
	}
	
	protected boolean bitte(Survivor s)
	{
		boolean touched = false;
		
		for(int x = (int) this.getX(); x <= this.getX()+5;x++)
		{
			for(int y = (int) this.getY(); y <= this.getY()+5;y++)
			{
				for(int x2 = (int) ((MyObject) s).getX(); x2 <= ((MyObject) s).getX()+5;x2++)
				{
					for(int y2 = (int) ((MyObject) s).getY(); y2 <= ((MyObject) s).getY()+5;y2++)
					{
						if(x == x2 && y == y2)
						{
							touched = true;
							s.bitten(true);
							System.out.println("TOUCH");
							break;
						}
					}
					
					if(touched)
					{
						break;
					}
				}
				
				if(touched)
				{
					break;
				}
			}
			
			if(touched)
			{
				break;
			}
		}
		
		return touched;
	}
	
	protected void infectOther(Survivor target)
	{
		
		//valeurs utilisées pour calculer l'angle
		double dty = ((MyObject) target).getY() - this.getY();
		double dtx = ((MyObject) target).getX() - this.getX();
		double angle = 0;
		
		//Calcule de l'angle entre l'objet courant (le tracker) et l'objet recu en parametre (le traquer)
		angle = (Math.atan2(dty, dtx) * 180 / Math.PI) * -1;
		
		//operation magique qui rend le deplacement plus fluide
		angle = angle/55;
		
		dx = Math.cos(angle) * 0.7/** 2*/;
		dy = Math.sin(angle) * 0.7/** 2*/;
	}
	
	protected ArrayList<MyObject> look(ArrayList<MyObject> pEnv)
	{
		ArrayList<MyObject> see = new ArrayList<MyObject>();
		
		for(MyObject o:pEnv)
		{
			if(o.getClass() != this.getClass() && ((o.getX() > (this.getX() - this.getRangeVision())) && (o.getX() < (this.getX() + this.getRangeVision())) && ((o.getY() > (this.getY() - this.getRangeVision())) && (o.getY() < (this.getY() + this.getRangeVision())))))
			{
				see.add(o);
			}
		}
		
		return see;
	}	
}