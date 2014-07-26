import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

/**
 * 
 * @author Alexandre
 *
 */
public class TileSelectorController implements MouseListener,KeyListener,ActionListener,WindowListener,MouseMotionListener
{
	private MapModel dataMap = null;
	private int mousePressedX = 0,
				mousePressedY = 0,
				mouseReleaseX = 0,
				mouseReleaseY = 0;
	
	private boolean isMouseDragged = false;

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
		mousePressedX = me.getX();
		mousePressedY = me.getY();
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		mouseReleaseX = me.getX();
		mouseReleaseY = me.getY();
		
		synchronized (dataMap) {
			System.out.println(isMouseDragged);
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
				//**DEPARTURE/*//Socket///
				//cette array a la mauvaise size.
				//{{{0,0} ,{0,50}},{{50,0},{50,50}}};
				int[][][] selectedTiles = null;
				double 	mrX = 0,
						mrY = 0;
				int		m = 0,
						n = 0;
				mrX = mouseReleaseX;
				mrY = mouseReleaseY;
				
				selectedTiles = new int [(int) Math.ceil((mrX/dataMap.TILE_SIZE) - (mousePressedX / dataMap.TILE_SIZE))][(int) Math.ceil((mrY/dataMap.TILE_SIZE) - (mousePressedY / dataMap.TILE_SIZE))][2];
				
				for(int x = mousePressedX; x < mouseReleaseX; x += dataMap.TILE_SIZE)
				{
					for(int y = mousePressedY; y < mouseReleaseY; y += dataMap.TILE_SIZE)
					{
						if(m == selectedTiles.length) 
						{
							m = 0;
							System.out.println("m: " + m);
						}
						
						if(n == selectedTiles[m].length) 
						{
							n = 0;
							System.out.println("n: " + n);
						}
						
						int[] pos = {x,y};
						selectedTiles[m][n] = pos.clone(); 
						n++;
					}
					m++;
					n = 0;
				}
				this.dataMap.setSelectedTile(selectedTiles);
				isMouseDragged = false;
			}
			else
			{
				int [][][] selectedSingleTile = null;
				selectedSingleTile = new int [1][1][2];
				selectedSingleTile[0][0][0] = (mousePressedX/dataMap.TILE_SIZE);
				selectedSingleTile[0][0][1] = (mousePressedY/dataMap.TILE_SIZE);
				dataMap.setSelectedTile(selectedSingleTile);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		isMouseDragged = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		dataMap.setTileSetLoad(false);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}



}
