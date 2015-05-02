package aars.me.championrandomizer;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

public class menuButton {
	
	private int width;
	private int height;
	
	private Image menugear;
	
	private ChampionMenu menu;
	private Rectangle button;
	
	public menuButton(ChampionMenu menu) {
		this.menu = menu;
		
		try {
			menugear = ImageIO.read(getClass().getResource("/UI/menugear.png"));
			width = menugear.getWidth(null);
			height = menugear.getHeight(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		button = new Rectangle(0, 0, width, height);
	}
	
	public void Draw(Graphics2D g) {
		g.drawImage(menugear, 0, 0, null);
	}
	
	public boolean isHit(int x, int y, int mode, boolean b) {
		if(b==true) return false;
		Rectangle mouse = new Rectangle(x, y, 1, 1);
		
		if(mouse.intersects(button))
		switch(mode) {
			case(MouseInterface.MOUSE_NOT_PRESSED):
				return true;
			case(MouseInterface.MOUSE_PRESSED):
				return true;
			case(MouseInterface.MOUSE_RELEASED):
				menu.toggleVisible();
				return true;
		}
		return false;
	}

	public boolean getEnabled() {
		return menu.isVisible();
	}
}
