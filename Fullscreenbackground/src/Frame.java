import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;


public class Frame extends JFrame implements KeyListener{
	
	public int width;
	public int height;
	
	private JFrame parentFrame;
	
	public Component component;
	
	public Frame(boolean showTaskLine, Color color, JFrame parentFrame) {
		this.parentFrame = parentFrame;
		setBorderless();
		addComponent(getSize(showTaskLine), color);
		readyFrame();
	}
	
	private void readyFrame() {
		setResizable(false);
		
		addKeyListener(this);
		addWindowListener(new WindowAdapter()
		{
		    public void windowClosing(WindowEvent e)
		    {
		    	exit();
		    }
		});
	}

	protected void exit() {
		setVisible(false);
		parentFrame.setVisible(true);
	}

	private void addComponent(Dimension size, Color color) {
		component = new Component(size.width, size.height, color);
		add(component);
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

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE||e.getKeyCode()==KeyEvent.VK_F11) exit();
	}
	
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
}
