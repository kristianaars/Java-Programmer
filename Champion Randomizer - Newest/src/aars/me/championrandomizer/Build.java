package aars.me.championrandomizer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import roles.BuildFactory;
import roles.Champion;
import roles.Log;

public class Build {
	
	private Champion currentlySelectedChampion;
	private BuildFactory[] currentlySelectedBuilds;
	
	private final static int SPACE_BETWEEN_BUILDS = 205;
	private final static int CHAMPION_PICTURE_WIDTH = 55;
	
	private Image background;
	
	private boolean isVisible = false;
	
	private Font Header_font = new Font("Calibri", 0, 60);
	private Color Header_color = new Color(81, 81, 81);
	
	private Font Role_font = new Font("Calibri", 0, 28);
	private Color Role_color = Color.WHITE;
	
	private Log log = new Log("Build.class");
	
	private static final int ANIMATION_UP = 0;
	private static final int ANIMATION_DOWN = 1;
	
	private float yOffset = 500;
	private float animationSpeed = 40;
	private int animationStatus = 3;
	
	public Build() {
		try {
			background = ImageIO.read(getClass().getResource(
					"/UI/buildbackground.png"));
		} catch (IOException e) {
			log.printMsg("Could't find image /UI/buildbackground.png",
					log.ERROR_MESSAGE);
		}
	}
	
	public void draw(Graphics2D g) {
		int y = (int) yOffset;
		Image championImg = currentlySelectedChampion.getImage();
		
		g.setFont(Header_font);
		g.setColor(Header_color);
		
		y+=17;
		g.drawImage(championImg, 30, y, CHAMPION_PICTURE_WIDTH, CHAMPION_PICTURE_WIDTH, null);
		y+=43;
		g.drawString("Item Build", 100, y);

		int builds = currentlySelectedBuilds.length;
		
		if(currentlySelectedChampion.getID()==0) return;
		
		for(int i = 1; i<builds; i++) {
			BuildFactory b = currentlySelectedBuilds[i];
			if(b!=null) {
				g.setFont(Role_font);
				g.setColor(Role_color);
				String role = b.getRoleName();
				int stringWidth = StringUtils.findWidth(role, g);
				g.drawImage(background, 0, y, null);
				b.draw(35, y, 35+stringWidth, g);
				g.setFont(Role_font);
				g.setColor(Role_color);
				g.drawString(role, 35, y+47);
				y+=SPACE_BETWEEN_BUILDS;
			}
		}
		
	}
	
	public void update() {
		if(animationStatus == ANIMATION_UP) {
			yOffset-=animationSpeed*(yOffset/250);
			if(yOffset<0) animationStatus = ANIMATION_DOWN;
		}
	}
	
	public void setChampion(Champion c) {
		currentlySelectedChampion = c;
		currentlySelectedBuilds = c.getBuilds();
	}
	
	public void setVisible(boolean b) {
		isVisible = b;
		yOffset = 500;
		animationStatus = 0;
	}
	
	public boolean getIsVisible() {
		return isVisible;
	}
}
