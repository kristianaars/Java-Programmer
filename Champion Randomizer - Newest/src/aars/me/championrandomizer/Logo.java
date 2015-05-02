package aars.me.championrandomizer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Logo {
	
	private Image image;
	
	private int x;
	private int y;
	
	public Logo(int w) {
		try {
			image = ImageIO.read(getClass().getResource("/UI/logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int imgWidth = image.getWidth(null);
		x = (w-imgWidth)/2;
		y = 15;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, x, y, null);
	}

}
