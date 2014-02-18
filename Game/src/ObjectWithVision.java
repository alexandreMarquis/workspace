import java.util.ArrayList;


abstract class ObjectWithVision extends MyObject {

	protected ArrayList<MyObject> objectSeen;
	protected int rangeVision;
	
	public ObjectWithVision(double pX,double pY,String path,int pRangeVision)
	{
		super(pX, pY, path);
		this.setVision(pRangeVision);
	}
	
	abstract ArrayList<MyObject> look(ArrayList<MyObject> pEnv);
	
	public void setVision(int pRangeVision)
	{
		this.rangeVision = pRangeVision;
	}
	
	public int getRangeVision()
	{
		return this.rangeVision;
	}
	
	public ArrayList<MyObject> getObjectSeen()
	{
		return this.objectSeen;
	}
}
