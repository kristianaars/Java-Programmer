package aars.me.championrandomizer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import roles.Log;

public class ItemBuildSwitch {
	
	private int parentHeight;
	private int imageHeight;
	
	private boolean itemBuild = false;
	
	private Build build;
	private Background background;
	
	private Image disabled = null;
	private Image enabled = null;
	
	private Log log = new Log("ItemBuildSwitcher");
	
	public ItemBuildSwitch(int h, Build b, Background ba) {
		parentHeight = h;
		build = b;
		background = ba;
		
		itemBuild = b.getIsVisible();
		
		loadUI();
	}

	private void loadUI() {
		try {
			enabled = ImageIO.read(getClass().getResource("/UI/itembdisabled.png"));
			disabled = ImageIO.read(getClass().getResource("/UI/itembenabled.png"));
			imageHeight = disabled.getHeight(null);
		} catch (IOException e) {
			log.printMsg("Could't find image files!", log.ERROR_MESSAGE);
		}
	}
	
	public void draw(Graphics2D g) {
		int y = parentHeight-imageHeight;
		Image img;
		if(itemBuild) img = enabled;
		else img = disabled;
		g.drawImage(img, 0, y, null);
	}
	
	public boolean isHit(int x, int y, int mode, boolean b) {
		if(b) return false;
		Rectangle mouse = new Rectangle(x, y, 1, 1);
		Rectangle button = new Rectangle(0, parentHeight-imageHeight, 450, imageHeight);
		
		if(mouse.intersects(button))
		switch(mode) {
			case(MouseInterface.MOUSE_NOT_PRESSED):
				return true;
			case(MouseInterface.MOUSE_PRESSED):
				return true;
			case(MouseInterface.MOUSE_RELEASED):
				toggleItemBuild();
				return true;
		}
		return false;
	}
	
	public void toggleItemBuild() {
		itemBuild = !itemBuild;
		background.setFilter(itemBuild);
		build.setVisible(itemBuild);
	}
}
