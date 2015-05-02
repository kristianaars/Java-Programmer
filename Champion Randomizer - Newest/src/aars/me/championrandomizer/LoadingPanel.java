package aars.me.championrandomizer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.Timer;


public class LoadingPanel extends JComponent implements ActionListener {
	
	private long period;
	private boolean isRunning;
	
	private static final Font textFont = new Font("Calibri", Font.BOLD, 45);
	
	private Background bg;
	
	private int width;
	private int height;
	
	private Timer timer;
	
	private float antallPunktum = 0;
	private String text;
	
	public LoadingPanel(long period) {
		this.period = period;
		timer = new Timer((int) period, this);
		width = Display.Width;
		height = Display.Height;
		
		bg = new Background(width, height);
		
		setPreferredSize(new Dimension(width, height));
		isRunning = true;
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(isRunning) {
			updateValues();
			repaint();
			return;
		}
		
		timer.stop();
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		bg.draw(g2d);
		
		g.setFont(textFont);
		g.setColor(Color.WHITE);
		g.drawString(text, 140, 250);
	}
	
	private void updateValues() {
		bg.update();
		
		if(antallPunktum<=4) antallPunktum+=0.08F;
		if(antallPunktum>4) antallPunktum = 0;
		text = generateString();
	}

	public void stop() {
		if(isRunning) isRunning = false;
	}
	
	public Background getBackgroundPanel() {
		return bg;
	}
	
	private String generateString() {
		String s = "Loading";
		for(int i = 0; i<(int)antallPunktum; i++) {
			s = s+".";
		}
		return s;
	}

}
