package aars.me.championrandomizer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Textplatform implements KeyListener {
	
	protected String message = "Search Role/Champion";
	private Championselector cs;
	
	private Image icon;
	
	private int width;
	private int height;
	private static int x = 0;
	private static int y = 165;
	
	private boolean isVisible = true;
	private boolean firstTime = true;
	private boolean isEditable = true;
	private boolean isEditing = true;
	private boolean cursor = true;
	
	private int WRITINGPOS_X;
	private int WRITINGPOS_Y;
	
	private final int FONT_HEIGHT = 30;
	private Font writefont;
	
	public Textplatform(int width, int height, Championselector c) {
		
		this.width = width;
		this.height = height;
		
		cs = c;
		
		WRITINGPOS_X = x+height+3;
		WRITINGPOS_Y = y+height-3;
		
		writefont = new Font("Calibri", 0, FONT_HEIGHT);
		
		try {
			icon = ImageIO.read(getClass().getResource("/UI/search.png"));
		} catch (IOException e) {
			System.out.println("Failed to load search.png");
		}
	}
	
	private long elapsedTime;
	private long lastTime;
	private boolean limitExtended;
	
	public void draw(Graphics2D g, int x2, int y2) {
		if(!isVisible) return;
		if(isEditing()) {
			elapsedTime = System.currentTimeMillis()-lastTime;
			if(elapsedTime>500) {
				toggleCourser();
				lastTime = System.currentTimeMillis();
			}
		}
		
		if(y2>164) y = y2; 
		
		int y = Textplatform.y;
		int x = Textplatform.x;
		
		WRITINGPOS_X = x+height+3;
		WRITINGPOS_Y = y+height-3;
		
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
		g.setColor(Color.GRAY.brighter());
		g.drawRect(x, y, width, height);
		g.drawRect(x, y, height, height);
		g.drawImage(icon, x, y, null);
		
		g.setFont(writefont);
		g.setColor(Color.GRAY.darker());
		g.drawString(message, WRITINGPOS_X, WRITINGPOS_Y);
		if(isCursor()) {
			x = WRITINGPOS_X+getWidthOfString(message, g);
			y = WRITINGPOS_Y-23;
			g.fillRect(x, y, 1, FONT_HEIGHT-5);
		}
	}

	private int getWidthOfString(String s, Graphics g) {
		FontMetrics fontMetrics = g.getFontMetrics();
		int stringWidth = fontMetrics.stringWidth(s);
		return stringWidth;
	}

	private void toggleCourser() {
		if(isCursor()) setCursor(false);
		else setCursor(true);
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void isEditable(boolean b) {
		isEditable = b;
	}
	
	private void addCharacter(char c) {
		message = String.format("%s%s", message, c);
		checkForExtenstion();
		writeToBar();
	}
	
	private void checkForExtenstion() {
		limitExtended = message.length() >= 20;
	}
	
	private void delete() {
		String m = "";
		for(int i = 0; i<message.length()-1; i++)
			m = String.format("%s%s", m, message.charAt(i));
		message = m;
		checkForExtenstion();
		writeToBar();
	}
	
	private void deleteAll() {
		message = "";
		checkForExtenstion();
		writeToBar();
	}
	
	private void writeToBar() {
		cs.setSearch(message);
	}
	
	private void paste() {
		try {
			String data = (String) Toolkit.getDefaultToolkit()
			        .getSystemClipboard().getData(DataFlavor.stringFlavor);
			for(int i = 0; i<data.length(); i++) {
				if(!limitExtended)
					addCharacter(data.charAt(i));
			}
		} catch (HeadlessException e) {
		} catch (UnsupportedFlavorException e) {
		} catch (IOException e) {
		} 
		writeToBar();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!isEditing()) return;
		
		int keyCode = e.getExtendedKeyCode();
		
		if(keyCode==KeyEvent.VK_V&&e.getModifiers()==2) {
			paste();
		}
		
		if(keyCode == 8) {
			delete();
			if(e.getModifiers()==2)
				deleteAll();
			return;
		}
		
		if(((keyCode>=48&&keyCode<=57)||(keyCode>=65&&keyCode<=90)||(keyCode==32))&&(e.getModifiers() != 2 && e.getModifiers()!=10)&&!limitExtended) {
			addCharacter(e.getKeyChar());
			return;
		}	
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	public boolean isHit(int xPos, int yPos, int mode) {
			if (xPos > x && xPos < x + width && yPos > y && yPos < y + height) {
				if(mode == MouseInterface.MOUSE_PRESSED) {
					setEditing(true);
					if(firstTime) deleteAll();
				}
				return true;
		}
		if(mode==MouseInterface.MOUSE_PRESSED) {
			setEditing(false);
			setCursor(false);
		}
		return false;
	}

	public boolean isEditing() {
		return isEditing;
	}

	public void setEditing(boolean isEditing) {
		this.isEditing = isEditing;
	}

	public boolean isCursor() {
		return cursor;
	}

	public void setCursor(boolean cursor) {
		this.cursor = cursor;
	}

	public String getMessage() {
		return message;
	}
	
	public void setVisibility(boolean b) {
		isVisible = b;
		if(b==false) {
			isEditable = false;
			isEditing = false;
			cursor = false;
		}
	}
}
