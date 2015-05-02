package me.aars.views.button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.AbstractAction;

public class Button {
	
	private int width;
	private int height;
	private int x = 0;
	private int y = 0;
	
	private Image[] image;
	
	public static Color DEFAULT_BUTTON_COLOR = new Color(60, 60, 255);
	
	private final static int MODE_NORMAL = 0;
	private final static int MODE_HOVER = 1;
	private final static int MODE_PRESSED = 2;
	
	private int mode = MODE_NORMAL;
	
	private final int NUMBER_OF_MODES = 3;
	
	private final int BOTTOMLINE_HEIGHT = 7;
	private final int OFFSET_WHEN_PRESSED = 5;
	
	private boolean isVisible = true;
	private boolean isEnabled = true;
	
	private Color OUTLINE_COLOR = Color.GRAY;
	private final static Color TEXT_COLOR = Color.WHITE;
	private final static Color FILTER_COLOR = new Color(0, 0, 0, 0.5F);
	
	private Color bottomline_color;
	
	private Color color;
	private String text;
	
	private Rectangle hitbox;
	
	private AbstractAction action;
	
	public Button(int width, int height) {
		this.width = width;
		this.height = height;
		createHitbox();
	}
	
	public Button(int width, int height, Color color, String text) {
		this(width, height);
		setColor(color);
		setText(text);
		paintButtons();
	}
	
	public void setColor(Color color) {
		this.color = color;
		bottomline_color = color.darker();
	}

	public void paintButtons() {
		Image img[] = new Image[NUMBER_OF_MODES];
		Color c = color;
		String s = text;
		
		for(int i = 0; i < img.length; i++) {	
			Image image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) image.getGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			g.setColor(c);
			g.fillRect(0, 0, width, height);
			
			g.setColor(OUTLINE_COLOR);
			//g.drawRect(0, 0, width-1, height-1);
			
			drawText(s, g);
			g.dispose();
			
			img[i] = image;
		}
		
		placeImages(img);
	}
	
	private void drawText(String s, Graphics2D g) {
		int length = s.length();
		if(length<=0) length = 1;
		
		int tekstForhold = width/length;
		Font font = new Font("Calibri", 0, (int)(tekstForhold*1.3F));
		
		g.setFont(font);
		g.setColor(TEXT_COLOR);
		
		int textWidth = StringUtils.findWidth(s, g);
		int textHeight = tekstForhold;
		
		int x = (width-textWidth)/2;
		int y = ((height-textHeight)/2)+textHeight;
		
		g.drawString(s, x, y);
	}

	public Button(int width, int height, Image[] image) {
		this(width, height);
		placeImages(image);
	}
	
	public void checkMouseInput(int x, int y, int type) {
		if(!isVisible||!isEnabled) return;
		Rectangle mouse = new Rectangle(x, y, 1, 1);
		if (hitbox.intersects(mouse)){
			switch (type) {
			case(MouseInterface.MOUSE_NOT_PRESSED):
				mode = MODE_HOVER;
			break;
			case(MouseInterface.MOUSE_PRESSED):
				mode = MODE_PRESSED;
			break;
			case(MouseInterface.MOUSE_RELEASED):
				mode = MODE_NORMAL;
				runAction();
			break;
			}
		}
		else {
			mode = MODE_NORMAL;
		}
	}
	
	private void placeImages(Image[] image) {
		this.image = new Image[NUMBER_OF_MODES];

		/*
		 * Om resten av Arrayen er null vil den private Arrayen få verdi null
		 * for at den kan "draw" uten error.
		 */

		if (image[0] == null) {
			System.out.println("Missing button image!!");
			this.image[0] = null;
		}

		for (int i = 0; i < NUMBER_OF_MODES; i++) {
			Image img = image[i];
			if (img != null)
				this.image[i] = img;
			else
				this.image[i] = this.image[0];
		}
	}
	
	public void draw(Graphics2D g) {
		if(!isVisible) return;
		if(bottomline_color!=null) {
			g.setColor(bottomline_color);
			g.fillRect(x, y+height, width, BOTTOMLINE_HEIGHT);
			int y = this.y;
			if(mode==MODE_PRESSED)
				y+=OFFSET_WHEN_PRESSED;
			g.drawImage(image[mode], x, y, null);
		} else g.drawImage(image[mode], x, y, null);
		if(!isEnabled) drawFilter(g);
	}
	
	private void drawFilter(Graphics2D g) {
		g.setColor(FILTER_COLOR);
		g.fillRect(x, y, width, height);
	}

	public void addAction(AbstractAction a) {
		action = a;
	}
	
	private void runAction() {
		if(action!=null) action.actionPerformed(null);
	}
	
	public void setVisible(boolean b) {
		isVisible = b;
	}
	
	public boolean getVisibel() {
		return isVisible;
	}
	
	public void disable() {
		isEnabled = false;
	}

	public void alignMiddleX(int WIDTH) {
		int x = (WIDTH-width)/2;
		setX(x);
	}

	public void setY(int i) {
		y = i;
		createHitbox();
	}
	
	public void setX(int i) {
		x = i;
		createHitbox();
	}
	
	private void createHitbox() {
		hitbox = new Rectangle(x, y, width, height);
	}

	public void allignBottomY(int parentHeight) {
		setY((parentHeight - height)-15);
	}

	public int getX() {
		return x;
	}

	public int getWidth() {
		return width;
	}
	
	public void setText(String s) {
		text = s;
	}
}
