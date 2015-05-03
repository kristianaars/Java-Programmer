import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;


public class Frame extends JFrame{
	
	public int width;
	public int height;
	
	public Frame(boolean showTaskLine, Color color, JFrame parentFrame) {
		addWindowListener(new WindowAdapter()
		{
		    public void windowClosing(WindowEvent e)
		    {
		    	parentFrame.setVisible(true);
		    }
		});
		setBorderless();
		addComponent(getSize(showTaskLine), color);
		readyFrame();
	}
	
	private void readyFrame() {
		setResizable(false);
	}

	private void addComponent(Dimension size, Color color) {
		Component c = new Component(size.width, size.height, color);
		add(c);
		pack();
	}

	private static final int TASKLINE_HEIGHT = 40;
	
	public Dimension getSize(boolean showTaskLine) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) screenSize.getWidth();
		height = (int) screenSize.getHeight();
		if(showTaskLine) height -= TASKLINE_HEIGHT;
		return new Dimension(width, height);
	}

	private void setBorderless() {
		setUndecorated(true);
	}
}
