package me.aars.manjpip.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import me.aars.manjpip.display.Display;

public class Manjpip {
	
	public static final int TYPE_HVIT = 0;
	public static final int TYPE_SORT = 1;
	public static final int TYPE_JULEFINGER = 2;
	public static final int TYPE_GLIDEMIDDEL = 3;
	public static final int TYPE_BOTOX = 4;
	public static final int TYPE_KONDOM = 5;

	public static final int TYPE_HVIT_POINTS = 9;
	public static final int TYPE_HVIT_INFECTED_POINTS = 16;
	public static final int TYPE_SORT_POINTS = 18;
	public static final int TYPE_SORT_INFECTED_POINTS = 26;
	public static final int TYPE_JULEFINGER_POINTS = 0;
	public static final int TYPE_GLIDEMIDDEL_POINTS = 0;
	public static final int TYPE_BOTOX_POINTS = 17;
	public static final int TYPE_KONDOM_POINTS = 0;

	public static final int TPYE_SORT_DAMAGE = 15;
	public static final int TYPE_HVIT_DAMAGE = 15;

	public static final float TYPE_HVIT_INFECTED_DAMAGE = 0.2F;
	public static final float TYPE_SORT_INFECTED_DAMAGE = 0.4F;


	private int width = 40;
	private int height = 40;
	
	private Image img;
	
	private static Image[][] types;
	
	private int type;
	private int x;
	private int y;
	private int speed;
	
	
	private boolean deleted = false;
	
	private boolean isInfected = false;
	
	public Manjpip(Image img, int type, int x, int y, int speed) {
		this.img = img;
		this.type = type;
		this.x = x;
		this.y = y;
		this.speed = speed;
		
		if(types==null) {
			types = new Image[6][2];
			loadImages();
		}
		
		if(type==TYPE_HVIT||type==TYPE_SORT) {
			int i = new Random().nextInt(10);
			if(i==1) isInfected = true;
		}
		
		loadImage(type);
		
		if(img!=null) {
			width = img.getWidth(null);
			height = img.getHeight(null);
		}
	}
	
	private void loadImages() {
		try {
			types[TYPE_HVIT][0] = ImageIO.read(getClass().getResource("/me/aars/manjpip/ui/hvitmanjpip.png"));
			types[TYPE_SORT][0] = ImageIO.read(getClass().getResource("/me/aars/manjpip/ui/sortmanjpip.png"));
			types[TYPE_HVIT][1] = ImageIO.read(getClass().getResource("/me/aars/manjpip/ui/hvitmanjpip_infected.png"));
			types[TYPE_SORT][1] = ImageIO.read(getClass().getResource("/me/aars/manjpip/ui/sortmanjpip_infected.png"));
			types[TYPE_JULEFINGER][0] = ImageIO.read(getClass().getResource("/me/aars/manjpip/ui/julefinger.png"));
			types[TYPE_GLIDEMIDDEL][0] = ImageIO.read(getClass().getResource("/me/aars/manjpip/ui/glidemiddel.png"));
			types[TYPE_KONDOM][0] = ImageIO.read(getClass().getResource("/me/aars/manjpip/ui/kondom.png"));
			types[TYPE_BOTOX][0] = ImageIO.read(getClass().getResource("/me/aars/manjpip/ui/botox.png"));
		} catch (IOException e) {
			System.out.println("Couldt load Manjpip.png");
		} catch (IllegalArgumentException e) {
			System.out.println("Couldt load Manjpip.png");
		}
	}

	private void loadImage(int type) {
		if(isInfected) img = types[type][1];
		else img = types[type][0];
	}

	public void update() {
		y += speed;
	}
	
	public void draw(Graphics2D g) {
		if(outOfWindowBounds())
			return;
		if(img!=null) g.drawImage(img, x, y, null);
		else g.drawString("ERROR!", x, y);
	}
	
	private boolean outOfWindowBounds() {
		if(y+height>Display.HEIGHT||y<-10) return true;
		return false;
	}

	public Rectangle getHitbox() {
		return new Rectangle(x, y, width, height);
	}

	public int getY() {
		return y;
	}

	public int getType() {
		return type;
	}

	public void setY(int i) {
		y = i;
	}

	public void delete() {
		deleted = true;
	}

	public boolean isDeleted() {
		return deleted;
	}
	
	public boolean isInfected() {
		return isInfected;
	}
}
