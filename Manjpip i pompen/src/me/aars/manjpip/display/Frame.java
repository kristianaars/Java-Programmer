package me.aars.manjpip.display;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class Frame extends JFrame implements WindowListener {
	
	private static final long serialVersionUID = 6727227490047712529L;
	
	private static long DEFAULT_FPS = 1000/65;
	private Display display;
	
	public final static String TITLE = "Manjpip I Pompen The Game!";
	public final static String VERSION = "Pre-Alpha 1.6";
	
	public static final boolean LOGGING = false;
	
	public static void main(String[] args) {
		long cd = DEFAULT_FPS;
		if(args.length>0) {
			int fps = Integer.parseInt(args[0]);
			cd = 1000/fps;
		}
		
		JFrame f = new Frame(cd);
		f.addWindowListener((WindowListener)f);
		f.setVisible(true);
	}
	
	
	
	public Frame(long c) {
		super(String.format("%s | %s", TITLE, VERSION));
		setResizable(false);
		loadUI(c);
		setLocationRelativeTo(null);
	}
	
	private void loadUI(long c) {
		display = new Display(c);
		addKeyListener(display);
		add(display);
		pack();
	}



	@Override
	public void windowActivated(WindowEvent arg0) {
		if(Frame.LOGGING)
			System.out.println("Frame activated");
	}



	@Override
	public void windowClosed(WindowEvent arg0) {
		if(Frame.LOGGING)
			System.out.println("Frame closed");
		display.stop();
	}



	@Override
	public void windowClosing(WindowEvent arg0) {
		if(Frame.LOGGING)
			System.out.println("Frame closing");
		display.stop();
	}



	@Override
	public void windowDeactivated(WindowEvent arg0) {
		if(Frame.LOGGING)
			System.out.println("Frame Deactivated");
		display.pause();
	}



	@Override
	public void windowDeiconified(WindowEvent arg0) {
		if(Frame.LOGGING)
			System.out.println("Frame Deiconified");
		display.pause();
	}



	@Override
	public void windowIconified(WindowEvent arg0) {
		if(Frame.LOGGING)
			System.out.println("Frame Iconified");
		display.pause();
	}



	@Override
	public void windowOpened(WindowEvent arg0) {
		if(Frame.LOGGING)
			System.out.println("Opened");
	}

}
