package aars.me.championrandomizer;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Toolbuttons {
	
	private Championselector cs;
	
	private Image img;
	
	private boolean isVisible = true;
	
	private final static int x = 301;
	private final static int y = 165;
	
	public Toolbuttons(Championselector cs) {
		this.cs = cs;
		try {
			img = ImageIO.read(getClass().getResource("/UI/toolbuttons.png"));
		} catch (IOException e) {
			System.out.println("toolbuttons.png failed to load");
		}
		
	}
	
	public void draw(Graphics2D g, int x2, int y2) {
		if(!isVisible)return;
		g.drawImage(img, x, y2, null);
	}
	
	public boolean isHit(int xPos, int yPos, int mode) {
		Rectangle select = new Rectangle(x, y, 82, 31);
		Rectangle deselect = new Rectangle(x+select.width, y, 67, 31);
		Rectangle mouse = new Rectangle(xPos, yPos, 2, 2);
		if(mouse.intersects(select)) {
			if(mode == MouseInterface.MOUSE_PRESSED)
				cs.selectAll();
			return true;
		} else if(mouse.intersects(deselect)) {
			if(mode == MouseInterface.MOUSE_PRESSED)
				cs.deselectAll();
			return true;
		}
		return false;
	}

	public void setVisible(boolean b) {
		isVisible = b;
	}

}
