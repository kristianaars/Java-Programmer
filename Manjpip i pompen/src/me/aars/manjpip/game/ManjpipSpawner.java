package me.aars.manjpip.game;

import java.awt.Graphics2D;
import java.util.Random;

public class ManjpipSpawner {
	
	private boolean URF = false;
	private boolean cheats = false;
	
	private final int START_Y = 0;
	private final int Antall_Manjpip_Typer = 100;
	
	private float FramesBetweenSpawn = 40;
	private int spawnTimer;
	
	private final int parentWidth;
	private final int parentHeight;
	
	private int lastRespawnTime;
	
	private final int MAX_BUFFER = 10;
	private Manjpip[] manjpip;
	
	private int PipWidth = 40;
	private int PipHeight = 40;
	
	private int currentIndex = 0;
	
	private Pomp pomp;
	
	private int lastX;
	
	private final static int MIN_X_DIF = 100;
	
	public ManjpipSpawner(int parentWidth, int parentHeight, Pomp pomp) {
		this.parentWidth = parentWidth;
		this.parentHeight = parentHeight;
		this.pomp = pomp;
		
		manjpip = new Manjpip[MAX_BUFFER];
		
		if(URF) {
			FramesBetweenSpawn = 1;
		}
	}
	
	private Random rnd = new Random();
	
	private void spawnNewManjpip() {
		if(MAX_SPEED<MAX_MAX_SPEED)
			MAX_SPEED+=INCREASE_SPEED;
		if(MIN_SPEED<MAX_MIN_SPEED)
			MIN_SPEED+=INCREASE_SPEED;
		if(FramesBetweenSpawn<MIN_RESPAWNTIME)
			FramesBetweenSpawn-=INCREASE_SPEED;
		
		int type = rnd.nextInt(Antall_Manjpip_Typer)+1;
		int xPos = getRandomXSpeed();
		int yPos = START_Y-PipHeight;

		int t = Manjpip.TYPE_SORT;
		if(type>35&&type<=75) t = Manjpip.TYPE_HVIT;
		else if(type>75&&type<=80) t = Manjpip.TYPE_JULEFINGER;
		else if((type>80&&type<=90)&&Powerups.sykdom.isEnabled()) t = Manjpip.TYPE_BOTOX;
		else if((type>90&&type<=98)&&!Powerups.glidemiddel.isEnabled())t = Manjpip.TYPE_GLIDEMIDDEL;
		else if((type>98&&type<=100)&&pomp.getKondomer()==0) t = Manjpip.TYPE_KONDOM;

		if(Powerups.rainingdick.isEnabled()) t = Manjpip.TYPE_JULEFINGER;
		
		int speed = getRandomSpeed();
		
		manjpip[currentIndex] = new Manjpip(null, t, xPos, yPos, speed);
		currentIndex++;
		if(currentIndex==MAX_BUFFER) currentIndex = 0;
	}
	
	private int getRandomXSpeed() {
		int i = rnd.nextInt(parentWidth-PipWidth);
		while((i+MIN_X_DIF>=lastX)&&(i-MIN_X_DIF<=lastX))
			i = rnd.nextInt(parentWidth-PipWidth);
		lastX = i;
		return i;
	}

	public void update() {
		for(int i = 0; i<MAX_BUFFER; i++) {
			Manjpip m = manjpip[i];
			if(m!=null) {
				if(m.getY()>500) {
					if(cheats ) {	
						if(m.getType()==m.TYPE_JULEFINGER||(m.getType()==m.TYPE_HVIT&&!m.isInfected()))
							pomp.setX((int)m.getHitbox().getX());
						if(m.getType()==m.TYPE_SORT||m.isInfected()) {
							pomp.setX((int)m.getHitbox().getX()+50);
						}
					}
					if(pomp.isHit(m)) {
						
					}
				}
				
				m.update();
				if(m.isDeleted()) manjpip[i]=null;
			}
		}
		
		spawnTimer++;
		
		if(spawnTimer>FramesBetweenSpawn) {
			spawnTimer = 0;
			spawnNewManjpip();
		}

		if(Powerups.rainingdick.isEnabled()) {

			FramesBetweenSpawn = 10;
			
			long timeEnabled = System.currentTimeMillis()- Powerups.rainingdick.getTimeEnabled();
			long timeNeeded = Powerups.rainingdick.getTimeout();
			int precentgeDone = getPrecentage(timeEnabled, timeNeeded);

			if(precentgeDone>=100) {
				Powerups.rainingdick.disable();
				FramesBetweenSpawn = 30;
			}
		}
	}

	private int getPrecentage(long timeEnabled, long timeNeeded) {
		int i = (int) (((float)timeEnabled/(float)timeNeeded)*100);
		if(i>100) i=100;
		return i;
	}
	
	public void draw(Graphics2D g) {
		for(int i = 0; i<MAX_BUFFER; i++) {
			Manjpip m = manjpip[i];
			if(m!=null) {
				m.draw(g);
			}
		}

	}
	
	private float MAX_SPEED = 10;
	private float MIN_SPEED = 5;
	
	private final static int MAX_MAX_SPEED = 22;
	private final static int MAX_MIN_SPEED = 13;
	private final static int MIN_RESPAWNTIME = 25;
	
	private final static float INCREASE_SPEED = 4.0F;
	
	private int getRandomSpeed() {
		int i = (rnd.nextInt((int)MAX_SPEED-(int)MIN_SPEED)+(int)MIN_SPEED)+1;
		return i;
	}

}
