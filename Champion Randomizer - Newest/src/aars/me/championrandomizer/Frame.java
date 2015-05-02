package aars.me.championrandomizer;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import roles.Champion;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1527670516711685853L;
	private static final int DEFAULTFPS = 80;
	
	public Frame(long period) {
		prepearFrame();
		prepearUI(period);
	}

	private void prepearUI(long period) {
		try {
			setIconImage(ImageIO.read(getClass().getResource("/UI/Icon.png")));
		} catch (IOException e) {}
		
		setTitle("Champion Randomizer!");
		addLoadingScreen(period);
		setLocationRelativeTo(null);
		setVisible(true);
		
		Background background = lp.getBackgroundPanel();
		
		Display d = new Display(period, background);
		addKeyListener(d);
		
		removeLoadingScreen();
		add(d);
		pack();
	}
	
	private LoadingPanel lp;
	
	private void addLoadingScreen(long period) {
		lp = new LoadingPanel(period);
		add(lp);
		pack();
	}
	
	private void removeLoadingScreen() {
		lp.stop();
		remove(lp);
	}
	
	private void prepearFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}

	public static void main(String[]args) {
		String fpsS = null;
		if(args.length==1)
			fpsS = args[0];
		
		int fps = (fpsS != null) ? Integer.parseInt(fpsS) :  DEFAULTFPS;
		long period = (long) (1000.0/fps); //In Ms!
		
		new Frame(period);
	}

}
