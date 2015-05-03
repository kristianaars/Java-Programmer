import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;


public class Component extends JComponent {
	
	public Color color;
	
	public int width;
	public int height;
	
	public Component(int width, int height, Color color) {
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, width, height);
	}

	public void setColor(Color c) {
		color = c;
		repaint();
	}

}
