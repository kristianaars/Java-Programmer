package me.aars.manjpip.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;

import me.aars.manjpip.display.Display;
import me.aars.views.button.Button;

public class Utilityline {
	
	private Button pause;
	
	private Image img;
	private Image kondomimg;
	
	private int width;
	private int height;
	
	private boolean isVisible;
	
	private Display display;
	
	private final static Font SCORE_FONT = new Font("Calibri", 1, 20);
	private final static Font SCORE_FONT_2 = new Font("Calibri", 3, 26);
	private final static Color SCORE_COLOR = new Color(224, 119, 0);
	private final static Color SCORE_COLOR_2 = Color.YELLOW;

	private final static Font TEKST_FONT = new Font("Calibri", 0, 40);
	private final static Color TEKST_COLOR = Color.WHITE;

	private final static Font LIFE_FONT = new Font("Calibri", 0, 25);
	
	private final static Color PIG_COLOR = new Color(0, 0, 0, 0.6F);
	
	private int score;
	private int life;
	private int max_life;

	private ProgressBar life_bar;
	private ProgressBar glidemiddel_bar;
	private ProgressBar sykdom_bar;
	
	
	private int x;
	private int y;

	private boolean kondom;
	
	public Utilityline(int x, int y, Display display) {
		this.x = x;
		this.y = y;
		this.display = display;

		loadProgressBars();
		loadImage();
		readyPauseButton();
	}

	private void loadProgressBars() {
		try {
			Image healthbar_img = ImageIO.read(getClass().getResource("/me/aars/manjpip/ui/healthbar.png"));
			life_bar = new ProgressBar(healthbar_img, healthbar_img.getWidth(null), healthbar_img.getHeight(null), x+247, y+5);
			glidemiddel_bar = new ProgressBar(PIG_COLOR, 50, 50, x+83, y+5);
			sykdom_bar = new ProgressBar(PIG_COLOR, 50, 50, x+138, y+5);
			kondomimg = ImageIO.read(getClass().getResource("/me/aars/manjpip/ui/kondomikon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readyPauseButton() {
		pause = new Button(51, 50, new Color(0F, 0F, 0F, 0F), "");
		pause.setX(x+(width-51-5));
		pause.setY(y+5);
		pause.addAction(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				display.togglePause();
			}
			
		});
	}

	private void loadImage() {
		try {
			img = ImageIO.read(getClass().getResource("/me/aars/manjpip/ui/utilline.png"));
			width = img.getWidth(null);
			height = img.getHeight(null);
		} catch (IOException e) {
			img = null;
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(img, x, y, null);

		life_bar.draw(g);

		g.setColor(TEKST_COLOR);
		
		if(kondom) g.drawImage(kondomimg, x+192, y+4, null);
		
		g.setFont(LIFE_FONT);
		String s = String.format("HP: %s/%s", life, max_life);
		int xp = ((life_bar.getWidth()-StringUtils.findWidth(s, g))/2)+life_bar.getX();

		g.drawString(s, xp, y + 35);

		g.setFont(SCORE_FONT);
		g.setColor(SCORE_COLOR);

		g.drawString("Score:", x + 10, y + 18 + 3);

		g.setFont(SCORE_FONT_2);
		g.setColor(SCORE_COLOR_2);

		g.drawString(""+score, x+10, y+42);

		if(Powerups.glidemiddel.isEnabled()) {
			glidemiddel_bar.draw(g);
		}

		if(Powerups.sykdom.isEnabled()) {
			sykdom_bar.draw(g);
		}


	}

	public void update() {
		life_bar.update();
		glidemiddel_bar.update();
		sykdom_bar.update();
	}
	
	public void isHit(int x, int y, int type) {
		pause.checkMouseInput(x, y, type);
	}
	
	public boolean isVisible() {
		return isVisible;
	}
	
	public void isVisible(boolean b) {
		isVisible = b;
	}
	
	public void setLife(int i) {
		life = i;
		float precentage = (float)i/(float)max_life;
		life_bar.setPrecentage(precentage);
	}
	
	public void setMaxLife(int i) {
		max_life = i;
	}
	
	public void setKondomer(int i) {
		if(i == 1) kondom = true;
		else kondom = false;
	}
	
	public void setScore(int i) {
		score = i;
	}
	
	public void setGlidemiddelPrecentage(int i) {
		if(i>100) System.out.println("Precentage over 100");
		float b = i/100F;
		glidemiddel_bar.setPrecentage(1F-b);
	}
	
	public void setBotoxPrecentage(int i) {
		if(i>100) System.out.println("PRECENTAGE OVER 100!");
		float b = i/100F;
		sykdom_bar.setPrecentage(1F-b);
	}

}
