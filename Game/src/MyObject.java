import java.awt.Image;

import javax.swing.ImageIcon;


abstract class MyObject {
	private final String DEFAULT_PATH = "C:/Users/Alexandre Marquis-A/Desktop/xml/Game/src/white.png";
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	protected Image image;
	
	public MyObject(double pX,double pY)
	{
		this.setX(pX);
		this.setY(pY);
		this.setImage(DEFAULT_PATH);
	}
	
	public MyObject(double pX,double pY,String path)
	{
		this.setX(pX);
		this.setY(pY);
		this.setImage(path);
	}
	
	abstract void update();
	
	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}
	
	public void setX(double x)
	{
		this.x = x;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public void setImage(String path)
	{
		this.image = new ImageIcon(path).getImage();
	}
	
	/*
	 * à implémenter
	 */
	public double distance(MyObject o)
	{
		double distance = 0;
		double x = this.x - o.getX();
		double y = this.y - o.getY();
		
		//valeur absolue pour les calcules de distance
		//x = x < 0 ? -x:x;                           il y a un exposant plus bas
		//y = y < 0 ? -y:y;
		
		//root(x^2+y^2)
		distance = Math.sqrt(((Math.pow(x,2) + (Math.pow(y, 2)))));
		
		return distance;
	}

}
