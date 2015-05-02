package aars.me.championrandomizer;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

import roles.*;


public class Championview {
	
	private final static int NOVALUE = -4444;
	private final static Font font = new Font("Calibri", 0, 35);

	private Role[] roles;
	private Role selectedRole;
	
	private Champion selected;
	private String championName;
	private Build build;
	
	private final int Width;
	
	private int imagex;
	private int imagey;
	private int textx;
	private int texty;
	
	private boolean isDrawing = false;
	
	private boolean isDrawable = true;
	
	public Championview(int Width, Role[] roles, Build build) {
		this.Width = Width;
		this.roles = roles;
		this.build = build;
		
		int imagewidth = 120;
		imagex = (Width-imagewidth)/2;
		imagey = 290;
		
		textx = NOVALUE;
		texty = 440;
		
		Champion startChamp = Champion.vayne;
		updateChampion(startChamp);
		build.setChampion(startChamp);
	}
	
	public void draw(Graphics2D g) {
		g.setFont(font);
		g.setColor(Color.WHITE);
		
		if(textx == NOVALUE) {
			FontMetrics metrics = g.getFontMetrics();
			findTextX(metrics);
		}
		
		Image championImage = selected.getImage();
		String championName = selected.getName();
		
		g.drawImage(championImage, imagex, imagey, null);
		g.drawString(championName, textx, texty);
	}

	private void findTextX(FontMetrics metrics) {
		int width = metrics.stringWidth(championName);
		textx = (Width-width)/2;
	}
	
	private long lastUpdate;
	private long cooldown;
	
	public void update() {
		isDrawable = false;
		int lnt = selectedRole.getChampions().size();
		for(int i = 1; i<lnt; i++) {
			if(selectedRole.getChampions().get(i).checkEnabled())
				isDrawable = true;
		}
		if(isDrawing) {
			long currentTime = System.currentTimeMillis();
			long elapsedTime = currentTime-lastUpdate;
			
			if(elapsedTime>cooldown) {
				lastUpdate = System.currentTimeMillis();
				cooldown = cooldown+10;
				randomizeChampion();
				if(cooldown>170) {
					isDrawing = false;
					cooldown = 0;
					lastUpdate = 0;
					setNewChampion();
				}
			}
		}
	}
	
	private int lastID = 0;
	
	private void setNewChampion() {
		while(selected.getID()==lastID)
			randomizeChampion();
		lastID = selected.getID();
		selected.playAudio();
		build.setChampion(selected);
	}

	public void startChampionDraw() {
		if(!isDrawable) return;
		if(isDrawing) return;
		isDrawing = true;
	}
	
	private int lastRandom = 0;

	private void randomizeChampion() {
		if(!isDrawable) return;
		Random rnd = new Random();
		int r = lastRandom;
		while(r==lastRandom||!selectedRole.getChampions().get(r).checkEnabled()||r==0)	
			r = rnd.nextInt(selectedRole.getChampions().size());
		Champion c = selectedRole.getChampions().get(r);
		updateChampion(c);
	}
	
	
	private void updateChampion(Champion c) {
		championName = c.getName();
		selected = c;
		textx = NOVALUE;
	}

	public void setRole(int i) {
		selectedRole = roles[i];
	}

	public boolean isDrawing() {
		return isDrawing;
	}
}
