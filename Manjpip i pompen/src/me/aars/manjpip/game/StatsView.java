package me.aars.manjpip.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class StatsView {
	
	private int width;
	private int height;
	
	private final Font font = new Font("Veranda", 0, 20);
	private final Color backgroundColor =  new Color(0F, 0F, 0F, 0.5F);
	
	public boolean isVisible = false;
	
	public StatsView(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void draw(Graphics2D g) {
		if(!isVisible) return;
		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);
		
		int y = 100;
		
		for(Stats s: Stats.values()) {
			int x = 20;
			y+=27;
			g.setFont(font);
			g.setColor(Color.WHITE);
			
			g.drawString(s.name, x, y);
			g.drawLine(x, y+4, x+450, y+4);
			
			String b = ""+s.getFormattedValue();
			x = StringUtils.findWidth(b, g);
			g.drawString(b, 469-x, y);
		}
	}

}
