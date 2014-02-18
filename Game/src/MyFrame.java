import javax.swing.*;

public class MyFrame extends JFrame{
	public static final int FRAME_WIDTH = 500;
	public static final int FRAME_HEIGHT = 500;
	
	public MyFrame ()
	{
		this.add(new Board());
		this.setTitle("MyGame");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);	
	}
	
	public static void main(String[] args) {
		new MyFrame();
		System.out.println("this is test");
	}

}
