import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;


public class Frame extends JFrame{
	
	public int width;
	public int height;
	
	public static void main(String [] args) {
		Frame frame = new Frame();
	}
	
	public Frame() {
		setBorderless();
		setVisible(true);
	}

	private void setBorderless() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) screenSize.getWidth();
		height = (int) screenSize.getHeight();
		setSize(width, height);
		setBackground(Color.BLACK);
		setUndecorated(true);
	}
}
