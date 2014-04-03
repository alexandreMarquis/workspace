import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

<<<<<<< HEAD
/**
 * 
 * @author Alexandre
 *
 */
public class TileSelectorView extends JPanel implements Runnable
{
=======
public class TileSelectorView extends JPanel implements Runnable {
>>>>>>> aa4e0ada3a5ff9d7e217aa0778d7a46c8059e534
	private MapModel dataMap;
	public static final int DELAY = 17;
	public static final int TILE_SIZE = 25;
	private Thread animator;

	public TileSelectorView(MapModel dataMap) {
		super();
		this.dataMap = dataMap;
		this.setPreferredSize(new Dimension(dataMap.getTileSet().getHeight(),
				this.dataMap.getTileSet().getWidth()));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.requestFocus();
	}

	public void addNotify() {
		super.addNotify();
		init();
	}

	public void init() {
		if (animator == null) {
			animator = new Thread(this);
			animator.start();
		}
	}

	public void drawGrid(Graphics2D g2d, int width, int height, int tileSize) {
		g2d.setColor(Color.PINK);

		for (int i = TILE_SIZE; i < width; i += tileSize) {
			g2d.drawLine(i, 0, i, height);
		}

		for (int i = TILE_SIZE; i < height; i += tileSize) {
			g2d.drawLine(0, i, width, i);
		}
	}

	public void drawTileSet(Graphics2D g2d) {
		BufferedImage bf = dataMap.getTileSet();

		if (bf != null) {
			g2d.drawImage(bf, 0, 0, bf.getHeight(), bf.getWidth(), null);
		}
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paint(g2d);

		drawTileSet(g2d);
		drawGrid(g2d, this.getSize().width, this.getSize().height, TILE_SIZE);

		Toolkit.getDefaultToolkit().sync();
		g2d.dispose();
	}

	public void animationCycle() {

	}

	@Override
	public void run() {
		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while (true) {
			animationCycle();
			repaint();
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;

			if (sleep < 0)
				sleep = 2;
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			}

			beforeTime = System.currentTimeMillis();
		}
	}

	public void addListner(TileSelectorController c) {
		this.addMouseListener(c);
		this.addKeyListener(c);
	}
}
