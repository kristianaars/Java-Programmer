package aars.me.championrandomizer;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import roles.Champion;
import roles.Role;


public class Championselector {
	
	private static final int CHAMPIONS_X_AXIS = 5;
	private static final int SPACE_BETWEEN_PICTURES = 3;
	private int TOTAL_HEIGHT = 0;
	
	private static final Font font = new Font("Calibri", 1, 15);
	
	private final Role[] roles;
	private BufferedImage[][] images;
	
	private String search = "";
	
	private final int picturewidth;
	
	private int x = 0;
	private int y = 197;
	
	private static final int ANIMATION_UP = 0;
	private static final int ANIMATION_DOWN = 1;
	
	private float yOffset = y;
	private float animationSpeed = 25;
	private int animationStatus = 3;
	
	private int yPosList = 0;
	private int championLength;
	
	private boolean isVisible = true;
	
	public Championselector(Role[] r, int width) {
		roles = r;
		
		picturewidth = width/CHAMPIONS_X_AXIS -SPACE_BETWEEN_PICTURES;
		championLength = roles[0].getChampions().size();
		images = new BufferedImage[2][championLength];
		
		Thread t = new Thread() {
			public void run() {
				for(int i = 1; i<championLength; i++) {
					Champion c = roles[0].getChampions().get(i);
					images[0][i] = resizeImage(c.getImage(), picturewidth);
					images[1][i] = createGrayImage(images[0][i]);
				}
			}
		};
		t.start();
	}
	
	private BufferedImage resizeImage(BufferedImage image, int wh) {
		BufferedImage i = new BufferedImage(wh, wh, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = i.createGraphics();
		g2d.drawImage(image, 0, 0, wh, wh, null);
		return i;
	}

	private BufferedImage createGrayImage(BufferedImage image) {
		BufferedImage i = new BufferedImage(picturewidth, picturewidth, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = i.createGraphics();
        g.drawImage(image, 0, 0, null);
        
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        op.filter(i, i);
		return i; 
	}
	
	public void draw(Graphics2D g, int x2, int y2) {
		if(!isVisible) return;
		
		System.out.println(y2);
		
		if(y2>197)
			y = y2;
		
		int x = this.x;
		int y = this.y+yPosList;
		
		int counter = 0;
		TOTAL_HEIGHT = 0;
		
		g.setColor(Color.WHITE);
		g.setFont(font);
		
		int r = 0;
		boolean difRole = false;
		
		for(int i = 1; i<roles.length; i++) {
			String s1 = roles[i].getRoleName();
			String s2 = search;
			if(stringEquals(s1, s2)) {
				r = i;
				difRole = true;
			}
		}
		
		championLength = roles[r].getChampions().size();
		
		for (int i = 1; i<championLength; i++) {
			int offset = 0;
			
			if (y < this.y) {
				offset = this.y - y;
			}

			Champion c = roles[r].getChampions().get(i);
			
			if (stringContains(c.getName(), search)||difRole) {
				
				Image img = null;
				
				if(c.checkEnabled())
					img = images[0][c.getID()];
				if(!c.checkEnabled())
					img = images[1][c.getID()];
				
				g.drawImage(img, x, y + offset, x + picturewidth, y
						+ picturewidth + offset, 0, 0 + offset, picturewidth,
						picturewidth + offset, null);

				
				if (offset < picturewidth - font.getSize()) {
					drawTextInMiddle(g, c.getName(), x, y + picturewidth - 5,
							picturewidth);
				}

					counter++;
					x += picturewidth + SPACE_BETWEEN_PICTURES + 1;
					if (counter == CHAMPIONS_X_AXIS) {
						y += picturewidth + SPACE_BETWEEN_PICTURES + 1;
						TOTAL_HEIGHT += picturewidth + SPACE_BETWEEN_PICTURES
								+ 1;
						x = 0;
						counter = 0;
					}
				}
			}
	}

	public boolean isHit(int xPos, int yPos, int mouseMode) {
		if(!isVisible) return false;
		
		int x = this.x;
		int y = this.y+yPosList;
		
		int counter = 0;
		
		int r = 0;
		boolean difRole = false;
		
		for(int i = 1; i<roles.length; i++) {
			String s1 = roles[i].getRoleName();
			String s2 = search;
			if(stringEquals(s1, s2)) {
				r = i;
				difRole = true;
			}
		}
		
		championLength = roles[r].getChampions().size();
		
		for (int i = 1; i<championLength; i++) {
			int offset = 0;

			if (y < this.y) {
				offset = this.y - y;
			}
			
			Champion c = roles[r].getChampions().get(i);
			
			if (stringContains(c.getName(), search)||difRole) {
				Rectangle picture = new Rectangle(x, y+offset, picturewidth, picturewidth-offset);
				Rectangle mouse = new Rectangle(xPos, yPos, 2, 2);
				
				if(mouse.intersects(picture)) {
					if(mouseMode==MouseInterface.MOUSE_PRESSED)
					c.toggleEnabled();
					return true;
				}
				
				counter++;
				x += picturewidth + SPACE_BETWEEN_PICTURES + 1;
				if (counter == CHAMPIONS_X_AXIS) {
					y += picturewidth + SPACE_BETWEEN_PICTURES + 1;
					x = 0;
					counter = 0;
				}
			}
		}
		return false;
	}

	private void drawTextInMiddle(Graphics2D g, String s, int xpos, int ypos, int width) {
		int Stringwidth = getStringWidth(g, s);
		int x = xpos+(width-Stringwidth)/2;
		int y = ypos;
		g.drawString(s, x, y);
	}

	private int getStringWidth(Graphics2D g, String s) {
		FontMetrics fontMetrics = g.getFontMetrics();
		int stringWidth = fontMetrics.stringWidth(s);
		return stringWidth;
	}

	public void setSearch(String message) {
		search = message;
		yPosList = 0;
	}
	
	public void checkScroll(int notch) {
		if(!isVisible) return;
		notch*=-1;
		scollspeed = (float) ((notch*5 + (0.9*scollspeed)));
	}
	
	public void deselectAll() {
		int r = 0;
		boolean difRole = false;
		for (int i = 1; i < roles.length; i++) {
			String s1 = roles[i].getRoleName();
			String s2 = search;
			if (stringEquals(s1, s2)) {
				r = i;
				difRole = true;
			}
		}
		ArrayList<Champion> ch = roles[r].getChampions();
		for(int i = 1; i<ch.size(); i++) {
			String name = ch.get(i).getName();
			if(stringContains(name, search)||difRole)
			ch.get(i).setEnabled(false);
		}
	}
	
	public void selectAll() {
		int r = 0;
		boolean difRole = false;
		for (int i = 1; i < roles.length; i++) {
			String s1 = roles[i].getRoleName();
			String s2 = search;
			if (stringEquals(s1, s2)) {
				r = i;
				difRole = true;
			}
		}
		ArrayList<Champion> ch = roles[r].getChampions();
		for(int i = 1; i<ch.size(); i++) {
			String name = ch.get(i).getName();
			if(stringContains(name, search)||difRole)
			ch.get(i).setEnabled(true);
		}
	}
	
	private boolean stringContains(String s1, String s2) {
		s1 = s1.toUpperCase().replace(" ", "")
				.replace("'", "");
		s2 = s2.toUpperCase().replace(" ", "")
				.replace("'", "");
		return s1.contains(s2);
	}
	
	private boolean stringEquals(String s1, String s2) {
		s1 = s1.toUpperCase().replace(" ", "")
				.replace("'", "");
		s2 = s2.toUpperCase().replace(" ", "")
				.replace("'", "");
		return s1.equals(s2);
	}

	public void setVisible(boolean b) {
		isVisible = b;
		animationStatus = ANIMATION_UP;
		y = 500;
	}
	
	float scollspeed = 0;
	
	public void update() {
		yPosList += scollspeed;
		
		float animationspeed = 0.8F;
		
		if(scollspeed>0) {
			scollspeed-=animationspeed;
			if(scollspeed<0) scollspeed = 0;
		} else if(scollspeed<0) {
			scollspeed+=animationspeed;
			if(scollspeed>0) scollspeed = 0;
		}
		
		if(yPosList>0)
			yPosList = 0;
		if(yPosList<TOTAL_HEIGHT/-1)
			yPosList = TOTAL_HEIGHT/-1;
	}
}
