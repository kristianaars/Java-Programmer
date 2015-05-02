package me.aars.manjpip.game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pomp {
	
	private boolean right_Down = false;
	private boolean left_Down = false;
	private boolean ctrl_down = false;
	
	private final int SPEED = 15;
	
	private int kondomer = 0;
	
	private int x;
	private int y;
	
	private int width = 100;
	private int height = 40;
	
	private int parentWidth;
	private int parentHeight;
	
	private Image[] pomp;
	private Points points;
	
	private Rectangle hitbox;
	private Utilityline utilline;

	private int MANJPIPREGN_PROGRESS;
	private int MANJPIP_NEEDED = 10;
	
	private float ratio = 1F;
	
	public Pomp(int parentWidth, int parentHeight, Utilityline utilline) {
		this.parentWidth = parentWidth;
		this.parentHeight = parentHeight;
		this.utilline = utilline;
		
		pomp = new Image[3];
		
		hitbox = new Rectangle();
		
		loadImage();
		
		if(pomp!=null) {
			width = pomp[1].getWidth(null);
			height = pomp[1].getHeight(null);
		}
		
		x = (parentWidth-width)/2;
		y = parentHeight-height-45;
	}
	
	public void setPoints(Points points) {
		this.points = points;
	}
	
	private void loadImage() {
		try {
			pomp[0] = ImageIO.read(getClass().getResource("/me/aars/manjpip/ui/pomp_1.png"));
			pomp[1] = ImageIO.read(getClass().getResource("/me/aars/manjpip/ui/pomp_2.png"));
			pomp[2] = ImageIO.read(getClass().getResource("/me/aars/manjpip/ui/glidemiddeleffekt.png"));
		} catch (IOException e) {
			System.out.println("Error loading pomp.png");
		}
	}
	
	public void draw(Graphics2D g, int i) {
		int x = this.x;
		int y = this.y;
		if(i==1) {
			x = x+16;
			y = y+23;
		}
		
		Image img = pomp[i];
		int width = (int) (img.getWidth(null)*ratio);
		int height = (int) (img.getHeight(null)*ratio);
		
		g.drawImage(img, x, y, width, height, null);
		if(i==1&&Powerups.glidemiddel.isEnabled()) {
			img = pomp[2];
			width = (int) (img.getWidth(null)*ratio);
			height = (int) (img.getHeight(null)*ratio);
			g.drawImage(img, x - 16, y - 23, width, height, null);
		}
		//g.fillRect((int)hitbox.getX(), (int)hitbox.getY(), (int)hitbox.getWidth(), (int)hitbox.getHeight());
	}
	
	
	
	private final static int HITBOX_Y_OFFSET = 80;
	
	public boolean isHit(Manjpip m) {
		boolean isInfeced = m.isInfected();
		int type = m.getType();
		Rectangle hb = m.getHitbox();
		int x = this.x+16;
		int y = (int) (this.y+23+hb.getHeight());
		hitbox.setBounds(x, y-25, width, height-HITBOX_Y_OFFSET);
		
		if(hb.intersects(hitbox)&&!m.isDeleted()) {
			ratio += 0.45F;
			if(type==Manjpip.TYPE_HVIT) {
				Stats.hviteManjpiper.addToValue(1);
				if(isInfeced) if(!minusKondom()) {
					setInfected();
				}
				points.addPoints(Manjpip.TYPE_HVIT_POINTS);
			} else if(type==Manjpip.TYPE_SORT) {
				Stats.sorteManjpiper.addToValue(1);
				addToManjpipRegn();
				if(isInfeced&&!minusKondom()) setInfected();
				if(!Powerups.glidemiddel.isEnabled()) points.minusLife(Manjpip.TPYE_SORT_DAMAGE);
				else points.addPoints(Manjpip.TYPE_SORT_POINTS);
			} else if(type==Manjpip.TYPE_GLIDEMIDDEL) {
				Stats.lubecollected.addToValue(1);
				Powerups.glidemiddel.enable();
			} else if(type==Manjpip.TYPE_JULEFINGER) {
				points.addJulefinger();
			} else if(type==Manjpip.TYPE_KONDOM) {
				kondomer = 1;
				Stats.condomsCollected.addToValue(1);
			} else if(type==Manjpip.TYPE_BOTOX&&Powerups.sykdom.isEnabled()) {
				points.addPoints(Manjpip.TYPE_BOTOX_POINTS);
				Powerups.sykdom.disable();
			}
			
			m.delete();
			return true;
		} else if((type==Manjpip.TYPE_HVIT)&&m.getY()>=parentHeight&&!isInfeced) {
			points.minusLife(Manjpip.TYPE_HVIT_DAMAGE);
			m.delete();
			return false;
		}
		return false;
	}

	private void addToManjpipRegn() {
		MANJPIPREGN_PROGRESS++;
		if(MANJPIPREGN_PROGRESS>=MANJPIP_NEEDED) {
			enableManjpipRegn();
			MANJPIPREGN_PROGRESS = 0;
		}
	}

	private void enableManjpipRegn() {
		Powerups.rainingdick.enable();
	}

	private void setInfected() {
		Powerups.sykdom.enable();
	}

	private boolean minusKondom() {
		if(kondomer-1<0) return false;
		kondomer-=1;
		return true;
	}
	
	private float animationSpeed = 0.03F;
	
	private boolean gameOver;
	
	public void update() {
		
		if(ratio>1) {
			ratio -= animationSpeed;
			if(ratio<1) {
				ratio = 1;
			}
		}
		
		if(gameOver) {
			y-=5;
			return;
		}
		
		int speed;
		
		if (ctrl_down)
			speed = 40;
		else
			speed = SPEED;
		if (right_Down)
			x += speed;
		if (left_Down)
			x -= speed;

		if(x>parentWidth-width) x=parentWidth-width;
		if(x<0-25) x = -25;
		
		if(Powerups.glidemiddel.isEnabled()) {
			long timeEnabled = System.currentTimeMillis()- Powerups.glidemiddel.getTimeEnabled();
			long timeNeeded = Powerups.glidemiddel.getTimeout();
			int precentgeDone = getPrecentage(timeEnabled, timeNeeded);
			
			utilline.setGlidemiddelPrecentage(precentgeDone);
			
			if(precentgeDone>=100) {
				Powerups.glidemiddel.disable();
				utilline.setGlidemiddelPrecentage(0);
			}
		}

		if(Powerups.sykdom.isEnabled()) {
			points.minusLife(0.1F);

			long timeEnabled = System.currentTimeMillis()- Powerups.sykdom.getTimeEnabled();
			long timeNeeded = Powerups.sykdom.getTimeout();
			int precentgeDone = getPrecentage(timeEnabled, timeNeeded);

			utilline.setBotoxPrecentage(precentgeDone);

			if(precentgeDone>=100) {
				Powerups.sykdom.disable();
				utilline.setBotoxPrecentage(0);
			}
		}

		utilline.setKondomer(kondomer);
	}
	
	private int getPrecentage(long timeEnabled, long timeNeeded) {
		int i = (int) (((float)timeEnabled/(float)timeNeeded)*100);
		if(i>100) i=100;
		return i;
	}

	public void keyPressed(int code) {
		if(code == KeyEvent.VK_RIGHT||code == KeyEvent.VK_D) {
			right_Down = true;
		} else if(code == KeyEvent.VK_LEFT||code == KeyEvent.VK_A) {
			left_Down = true;
		}
		if(code==KeyEvent.VK_CONTROL) {
			ctrl_down = true;
		}
	}
	
	public void keyReleased(int code) {
		if(code == KeyEvent.VK_RIGHT||code == KeyEvent.VK_D) {
			right_Down = false;
		} else if(code == KeyEvent.VK_LEFT||code == KeyEvent.VK_A) {
			left_Down = false;
		}
		if(code==KeyEvent.VK_CONTROL) {
			ctrl_down = false;
		}
	}

	public int getKondomer() {
		return kondomer;
	}
	
	public void setX(int i) {
		x = i;
	}

	public int getY() {
		return y;
	}
	
	public int getX() {
		return x;
	}
}
