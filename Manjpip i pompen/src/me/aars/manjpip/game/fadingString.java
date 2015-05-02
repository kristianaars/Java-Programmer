package me.aars.manjpip.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class fadingString {
	
	private String message;
	private float x;
	private float y;
	private float animationSpeed;
	private Color color;
	private Font font;
	
	private float alpha = 1F;
	private boolean isDeleted = false;
	
	public final static Font DEFAULT_FONT = new Font("Arial", 0, 27);
	public final static Color DEFAULT_COLOR = Color.WHITE;
	
	public fadingString(String msg, int x, int y, float animationSpeed, Color color, Font font) {
		message = msg;
		this.x = x;
		this.y = y;
		this.animationSpeed = animationSpeed;
		this.color = color;
		this.font = font;
	}
	
	public void draw(Graphics2D g) {
		if(isDeleted) return;
		g.setColor(color);
		g.setFont(font);
		g.drawString(message, x, y);
	}
	
	public void update() {
		if(isDeleted ) return;
		if(alpha>0F) {
			alpha -= (animationSpeed/200);
			if(alpha<0F) {
				alpha = 0;
				isDeleted = true;
			}
		}
		
		y-= animationSpeed;
		color = new Color((float)color.getRed()/255F, (float)color.getGreen()/255F, (float)color.getBlue()/255F, alpha);
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}

}
