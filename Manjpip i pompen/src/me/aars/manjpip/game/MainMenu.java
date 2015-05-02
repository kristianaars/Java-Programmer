package me.aars.manjpip.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;

import me.aars.views.button.Button;
import me.aars.manjpip.display.Display;

public class MainMenu {
	
	private boolean isVisible = true;
	
	private Display display;
	
	private int width;
	private int height;
	
	private Image logo;
	
	private Button[] buttons;
	
	private final int AMOUNT_OF_BUTTONS = 5;
	
	private final static int BUTTON_WIDTH = 300;
	private final static int BUTTON_HEIGHT = 60;
	
	public MainMenu(int width, int height, Display display) {
		this.width = width;
		this.height = height;
		this.display = display;
		loadImages();
		createButton();
		loadPositions();
		settings = new Settings(width, height);
	}
	
	private void loadImages() {
		try {
			logo = ImageIO.read(getClass().getResource("/me/aars/manjpip/ui/logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int logoX;
	private int logoY;

	public Settings settings;
	
	private void loadPositions() {
		if(buttons==null) createButton();
		
		logoX = (width-logo.getWidth(null))/2;
		logoY = 70;
		
		int y = 220;
		
		for(int i = 0; i<AMOUNT_OF_BUTTONS; i++) {
			buttons[i].alignMiddleX(width);
			buttons[i].setY(y);
			y+=90;
		}
	}

	private void createButton() {
		buttons = new Button[AMOUNT_OF_BUTTONS];
		buttons[0] = new Button(BUTTON_WIDTH, BUTTON_HEIGHT, Button.DEFAULT_BUTTON_COLOR, "   Play!   ");
		buttons[0].addAction(new AbstractAction() {public void actionPerformed(ActionEvent arg0) {display.startGame();}});;
		
		buttons[1] = new Button(BUTTON_WIDTH, BUTTON_HEIGHT, Button.DEFAULT_BUTTON_COLOR, "   Shop    ");
		buttons[1].addAction(new AbstractAction() {public void actionPerformed(ActionEvent arg0) {display.showShop();}});;;
		
		buttons[2] = new Button(BUTTON_WIDTH, BUTTON_HEIGHT, Button.DEFAULT_BUTTON_COLOR, "   Help    ");
		buttons[2].disable();
		
		buttons[3] = new Button(BUTTON_WIDTH, BUTTON_HEIGHT, Button.DEFAULT_BUTTON_COLOR, "   Stats   ");
		buttons[3].disable();
		
		buttons[4] = new Button(BUTTON_WIDTH, BUTTON_HEIGHT, Button.DEFAULT_BUTTON_COLOR, "  Settings ");
		buttons[4].addAction(new AbstractAction() {public void actionPerformed(ActionEvent arg0) {settings.isVisible=true;}});;;
	}
	
	
	public void draw(Graphics2D g) {
		if(!isVisible) return;
		for(Button b:buttons) b.draw(g);
		g.drawImage(logo, logoX, logoY, null);
	}
	
	public void isHit(int x, int y, int type) {
		if(!isVisible) return;
		for(Button b:buttons) b.checkMouseInput(x, y, type);
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}
