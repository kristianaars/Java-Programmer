package me.aars.manjpip.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Points {
	
	private float points = 0;
	private int julefingere = 0;
	private float multiplier = 1.5F;
	private float life;
	
	private Utilityline utilline;
	
	private Pomp pomp;
	
	private final int MAX_LIFE;
	
	private int stringMsgIndex = 0;
	
	private static final int MAX_POINTS_BUFFER = 10;
	private fadingString[] pointsmessage;
	
	private final Filemanager filemanager;
	
	private Font font = new Font("Arial", 1, 30);
	private Color color = Color.ORANGE;
	
	public Points(Utilityline utilline, Pomp pomp) {
		pomp.setPoints(this);
		this.utilline = utilline;
		filemanager = new Filemanager();
		this.pomp = pomp;
		
		pointsmessage = new fadingString[MAX_POINTS_BUFFER];
		
		//Bruker timeout som HP
		MAX_LIFE = (int)Powerups.health.getRawTimeout();

		utilline.setMaxLife(MAX_LIFE);
		reset();
	}
	
	public void reset() {
		points = 0;
		multiplier = 1;
		setLife(MAX_LIFE);
	}
	
	public void addPoints(int i) {
		int p = (int)(i*multiplier);
		Font font = fadingString.DEFAULT_FONT;
		if(p >= 20) font = this.font;
		spawnNewStringMessage("+" + p +" points", Color.WHITE, font);
		Stats.totalPoints.addToValue(p);
		points += p;
		utilline.setScore((int) points);
	}
	
	private void spawnNewStringMessage(String s, Color c) {
		spawnNewStringMessage(s, c, fadingString.DEFAULT_FONT);
	}
	
	private void spawnNewStringMessage(String s, Color c, Font f) {
		if(stringMsgIndex>MAX_POINTS_BUFFER-1) stringMsgIndex = 0;
		pointsmessage[stringMsgIndex] = new fadingString(s, pomp.getX(), pomp.getY()+30, 4, c, f);
		stringMsgIndex++;
	}

	public int getPoints() {
		return (int) points;
	}
	
	public int getALife() {
		return (int)life;
	}
	
	public void minusLife(float i) {
		setLife(life-i);
		spawnNewStringMessage("-" + (int)i+" health", Color.RED);
	}

	public void setLife(float i) {
		if(i<0) i = 0;
		if(i>MAX_LIFE) i = MAX_LIFE;

		life = i;
		utilline.setLife((int)i);

	}

	public int getJulefingere() {
		return julefingere+((int)points/100);
	}

	private final static Font GOLD_FONT = new Font("Arial", 1, 30);
	
	public void addJulefinger() {
		julefingere+=1;
		spawnNewStringMessage("+ 1 Julefinger", Color.YELLOW, GOLD_FONT);
	}
	
	public void draw(Graphics2D g) {
		for(int i = 0; i<MAX_POINTS_BUFFER; i++) {
			fadingString f = pointsmessage[i];
			if(f!=null) {
				f.draw(g);
			}
		}
	}
	
	public void update() {
		for(int i = 0; i<MAX_POINTS_BUFFER; i++) {
			fadingString f = pointsmessage[i];
			if(f!=null) {
				f.update();
				if(f.isDeleted()) pointsmessage[i] = null;
			}
		}
	}
}
