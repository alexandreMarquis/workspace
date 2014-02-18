import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JMenuItem;

public class MapController implements MouseListener,MouseMotionListener,KeyListener,ActionListener 
{
	private MapModel dataMap = null;
	
	public MapController(MapModel dataMap)
	{
		this.dataMap = dataMap;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent me) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		// TODO Auto-generated method stub
		synchronized (dataMap) {
			this.dataMap.addTile(me.getX(), me.getY());
			this.dataMap.setNeedToBeUpdate(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String event = ((JMenuItem)e.getSource()).getText();
		
		if(event.compareToIgnoreCase("new map") == 0)
		{
			System.out.println("new map");
		}
		else if(event.compareToIgnoreCase("save map") == 0)
		{
			System.out.println("save map");
			if(dataMap != null)
			{
				dataMap.saveMap();
			}
		}
		else if(event.compareToIgnoreCase("load map") == 0)
		{
			System.out.println("load map");
			if(dataMap != null)
			{
				dataMap.loadMap();
			}
		}
		else if(event.compareToIgnoreCase("load tile") == 0)
		{
			System.out.println("load tile");
			this.dataMap.loadTile();
			if(this.dataMap.getTileSet() != null)
			{
				TileSelectorView v = new TileSelectorView(dataMap);
				TileSelectorController c = new TileSelectorController(dataMap);
				v.addListner(c);
				TileSelectorFrame f = new TileSelectorFrame(v,c);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		// TODO Auto-generated method stub
		System.out.println(me.getX() /32+ " : " + me.getY()/32);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
