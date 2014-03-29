import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

/**
 * 
 * @author Alexandre
 *
 */
public class MapController implements MouseListener,MouseMotionListener,KeyListener,ActionListener 
{
	private MapModel dataMap = null;
	private int mousePressedX = 0,
				mousePressedY = 0,
				mouseReleaseX = 0,
				mouseReleaseY = 0;
	
	private boolean isMouseDragged = false;
	
	
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
		mousePressedX = me.getX();
		mousePressedY = me.getY();
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		// TODO Auto-generated method stub
		mouseReleaseX = me.getX();
		mouseReleaseY = me.getY();
		
		synchronized (dataMap) {
			if(dataMap.isTileSetLoad())
			{
				if(isMouseDragged)
				{
					if(mousePressedX > mouseReleaseX)
					{
						int temp = mousePressedX;
						mousePressedX = mouseReleaseX;
						mouseReleaseX = temp;
					}
					
					if(mousePressedY > mouseReleaseY)
					{
						int temp = mousePressedY;
						mousePressedY = mouseReleaseY;
						mouseReleaseY = temp;
					}
					
					for(int x = mousePressedX; x <= mouseReleaseX; x += dataMap.TILE_SIZE)
					{
						for(int y = mousePressedY; y <= mouseReleaseY; y += dataMap.TILE_SIZE)
						{
							this.dataMap.addTile(x, y);
							this.dataMap.setNeedToBeUpdate(true);
						}
					}
					
					isMouseDragged = false;
				}
				else
				{
					this.dataMap.addTile(me.getX(), me.getY());
					this.dataMap.setNeedToBeUpdate(true);
				}
			}
			
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
			if(!this.dataMap.isTileSetLoad())
			{
				this.dataMap.loadTile();
				if(this.dataMap.getTileSet() != null)
				{
					TileSelectorView v = new TileSelectorView(dataMap);
					TileSelectorController c = new TileSelectorController(dataMap);
					v.addListner(c);
					TileSelectorFrame f = new TileSelectorFrame(v,c);
					dataMap.setTileSetLoad(true);
				}
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		// TODO Auto-generated method stub
		isMouseDragged = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
