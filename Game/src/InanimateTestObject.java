import javax.swing.ImageIcon;


public class InanimateTestObject extends MyObject{

	public InanimateTestObject(double pX, double pY,String color) {
		super(pX, pY);
		this.image = new ImageIcon("C:/Users/Alexandre Marquis-A/Desktop/xml/Game/src/" + color +".png").getImage();
	}

	@Override
	void update() {
		// TODO Auto-generated method stub
		
	}

}
