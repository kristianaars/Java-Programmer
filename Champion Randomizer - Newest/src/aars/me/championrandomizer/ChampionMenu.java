package aars.me.championrandomizer;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import roles.Role;

public class ChampionMenu {
	
	private Championselector selector;
	private Textplatform searchbar;
	private Toolbuttons tb;
	
	private Background background;
	private Display parent;
	
	private boolean isVisible = false;
	
	private int x = 0;
	private int y = 500;
	
	public ChampionMenu(Role[] r, int Width, Background b, Display parent) {
		selector = new Championselector(r, Width);
		searchbar = new Textplatform(300, 30, selector);
		tb = new Toolbuttons(selector);
		background = b;
		this.parent = parent;
	}
	
	public void draw(Graphics2D g2d) {
		if(!isVisible) return;
		selector.draw(g2d, x, y);
		searchbar.draw(g2d, x, y);
		tb.draw(g2d,x, y);
	}
	
	public boolean isHit(int x, int y, int mode) {
		if(!isVisible) return false;
		if(searchbar.isHit(x, y, mode))parent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		if(tb.isHit(x, y, mode)) parent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		if(selector.isHit(x, y, mode))parent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return true;
	}
	
	public void setVisible(boolean b) {
		background.setFilter(b);
		selector.setVisible(b);
		tb.setVisible(b);
		searchbar.setVisibility(b);
		isVisible = b;
	}
	
	private static final float animationSpeed = 35F;
	private static final int ANIMATION_UP = 0;
	private static final int ANIMATION_DOWN = 1;
	private int animationStatus = 3;
	
	
	public void update() {
		if(animationStatus == ANIMATION_UP) {
			y-= animationSpeed * ((float)(y-165)/(float)250);
			if(y<165) animationStatus = ANIMATION_DOWN;
		}
		selector.update();
	}
	
	public boolean isVisible() {
		return isVisible;
	}

	public void keyPressed(KeyEvent e) {
		searchbar.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		searchbar.keyReleased(e);
	}

	public void keyTyped(KeyEvent e) {
		searchbar.keyTyped(e);
	}

	public void checkScroll(int notches) {
		selector.checkScroll(notches);
	}

	public void toggleVisible() {
		if(isVisible) {
			setVisible(false);
		} else  {
			setVisible(true);
			y = 500;
			animationStatus = ANIMATION_UP;
		}
	}

}
