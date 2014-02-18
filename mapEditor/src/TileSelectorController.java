import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TileSelectorController implements MouseListener,KeyListener,ActionListener
{
	private MapModel dataMap = null;
	
	public TileSelectorController(MapModel m)
	{
		this.dataMap = m;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
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
			System.out.println("double :" + me.getPoint().getY());
			System.out.println("double /32 :" + me.getPoint().getY()/32);
			System.out.println("cast int :" + (int)me.getPoint().getY());
			System.out.println("cast int /32:" + (int)me.getPoint().getY()/32);
			System.out.println("floor:" + Math.floor(me.getPoint().getY()/32));
			dataMap.setSelectedTile(me.getX(), me.getY());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
