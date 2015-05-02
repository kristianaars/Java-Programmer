package aars.me.championrandomizer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Background {
	
	private int ParentWidth;
	private int ParentHeight;
	
	private int width;
	private int height;
	
	private BufferedImage image;
	
	private float x = 0;
	private float y = 0;
	
	private final static float ANIMATIONSPEED = 0.5F;
	private final static int ANIMATION_RIGHT = 0;
	private final static int ANIMATION_LEFT = 1;
	
	private float alpha = 0F;
	private Color filterColor = new Color(1F, 1F, 1F, alpha);
	
	private int animationway = 1;
	
	private boolean drawFilter = false;
	
	public Background(int W, int H) {
		ParentWidth = W;
		ParentHeight = H;
		prepeareImage();
	}
	
	private void prepeareImage() {
		width = 0; height = 0;
		try {
			image = ImageIO.read(getClass().getResource("/UI/background.png"));
			width = image.getWidth(null);
			height = image.getHeight(null);
		} catch (IOException e) {
			System.err.println("Background.png not found!");
		}
	}

	public void update() {
		if(animationway == ANIMATION_RIGHT) {
			x += ANIMATIONSPEED;
			if(x>=0F) {
				animationway = ANIMATION_LEFT;
			}
		}

		if(animationway == ANIMATION_LEFT) {
			x -= ANIMATIONSPEED;
			if(x<=(width/-1)+ParentWidth) {
				animationway = ANIMATION_RIGHT;
			}
		}
		
		if (drawFilter) {
			if (alpha < 0.6F) {
				alpha += 0.04F;
				filterColor = new Color(1F, 1F, 1F, alpha);
			}
		} else if(alpha>0) {
			alpha-=0.04F;
			if(alpha<0) alpha = 0;
		}
		filterColor = new Color(1F, 1F, 1F, alpha);
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, (int) x, (int) y, null);
		g.setColor(filterColor);
		g.fillRect(0, 0, ParentWidth, ParentHeight);
	}
	
	public void setFilter(boolean b) {
		drawFilter = b;
	}

}
