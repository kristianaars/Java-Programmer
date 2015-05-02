package roles;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import aars.me.championrandomizer.*;

public class BuildFactory {
	
	private final Role role;
	
	private final Spell[] spells;
	private final Item[] startingItems;
	private final Item[] items;
	
	private Font item_font = new Font("Calibri", 0, 8);
	private Color item_color = Color.WHITE;
	
	private Font itemcount_font = new Font("Arial", Font.BOLD, 20);
	private Color itemcount_color = Color.WHITE;
	
	private static final int SPACE_BETWEEN_ITEMS = 20;
	private static final int NUMBER_OF_ITEMS = 6;
	
	private Font error_font = new Font("Calibri", Font.BOLD, 35);
	private Color error_color = Color.WHITE;
	
	private static Image arrow;
	
	private final int itemCount;
	
	private boolean noBuild = false;
	
	private Log log = new Log("BuildFactory.class");
	
	public BuildFactory(Spell[] sp, Item[] s, Item[] i, Champion c, Role r, int ic) {
		spells = sp;
		items = i;
		startingItems = s;
		role = r;
		itemCount = ic;
		
		if(arrow==null) {
			try {
				arrow = ImageIO.read(getClass().getResource("/UI/arrow.png"));
			} catch (IOException e) {
				log.printMsg("Could't find Image: UI/arrow.png", log.ERROR_MESSAGE);
			}
		}
	}
	
	public BuildFactory(Role r) {
		spells = null;
		startingItems = null;
		items = null;
		role = r;
		itemCount = 0;
		noBuild = true;
	}

	public void draw(int startX, int startY, int titleX, Graphics2D g) {
		int x = startX;
		int y = startY + 60;
		
		if(noBuild) {
			g.setFont(error_font);
			g.setColor(error_color);
			g.drawString("Woops... Looks like", x+35, y+40);
			g.drawString("the build is missing!", x+32, y+75);
			return;
		}
		
		int width = items[0].getIcon().getWidth(null);
		g.setFont(item_font);
		g.setColor(item_color);
		
		
		g.drawImage(spells[0].getIcon(), titleX+5, startY+26, null);
		g.drawImage(spells[1].getIcon(), titleX+5+40, startY+26, null);
		
		for(int i = 0; i<startingItems.length; i++) {
			Image icon = startingItems[i].getIcon();
			String name = startingItems[i].getName();
			
			int textx = ((width-StringUtils.findWidth(name, g))/2)+x;
			
			g.drawImage(icon, x, y, null);
			g.drawString(name, textx, y+width+7);
			if(i!=startingItems.length-1)
				g.drawImage(arrow, x+width+1, y+(((width-arrow.getHeight(null))/2)), null);
			
			if(i==0&&itemCount>1) {
				g.setFont(itemcount_font);
				g.setColor(itemcount_color);
				g.drawString(itemCount+"x", textx+width-21, y+width-2);
				g.setFont(item_font);
				g.setColor(item_color);
			}
			
			x+=width+SPACE_BETWEEN_ITEMS;
		}
		
		x = startX;
		y += 62;
		
		for(int i = 0; i<NUMBER_OF_ITEMS; i++) {
			Image icon = items[i].getIcon();
			String name = items[i].getName();
			
			int textx = ((width-StringUtils.findWidth(name, g))/2)+x;
			
			g.drawImage(icon, x, y, null);
			g.drawString(name, textx, y+width+7);
			if(i!=NUMBER_OF_ITEMS-1)
				g.drawImage(arrow, x+width+1, y+(((width-arrow.getHeight(null))/2)), null);
			x+=width+SPACE_BETWEEN_ITEMS;
		}
	}

	public String getRoleName() {
		return role.getRoleName();
	}
}
