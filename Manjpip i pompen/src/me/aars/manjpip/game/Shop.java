package me.aars.manjpip.game;

import me.aars.views.button.Button;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 * Created by Kristian Aars on 12.04.2015.
 */

public class Shop {

    private boolean isVisible = false;

    private int width;
    private int height;
    
    private int x;
    private int y;
    
    private int targetX;
    private int targetY;
    
    private Image background;
    
    private Powerups[] shopItems;

    private Button[] buyButtons;
    private Button exitButton;
    
    private Julefinger currency;
    
    private Font PRICE_AND_LEVEL_FONT = new Font("Calibri", 0, 30);

	private float animationSpeed = 40F;

    public Shop(int width, int height, Julefinger currency) {
        this.width = width;
        this.height = height;
        this.currency = currency;

        shopItems = Powerups.values();

        loadBackground();
        loadButtons();
    }
    
	private void loadBackground() {
		try {
			background = ImageIO.read(getClass().getResource("/me/aars/manjpip/ui/shop.png"));
			targetX = (width-background.getWidth(null))/2;
			targetY = (height-background.getHeight(null))/2;
			x = targetX;
			y = height;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadButtons() {
		exitButton = new Button(300, 30, Button.DEFAULT_BUTTON_COLOR, "    Exit    ");
		exitButton.addAction(new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				isVisible = false;
			}
			
		});
		
		exitButton.alignMiddleX(width);
		exitButton.allignBottomY(height);
		
        buyButtons = new Button[shopItems.length];
        for(int i = 0; i< shopItems.length; i++) {
            buyButtons[i] = new Button(60, 40, new Color(60, 60, 255), "Buy!");
            buyButtons[i].setX(x+290);
            final int id = i;
            buyButtons[i].addAction(new AbstractAction() {
				public void actionPerformed(ActionEvent arg0) {
					Powerups item = shopItems[id];
					int price = item.getPriceForNextLevel();
					if(price<0) buyButtons[id].disable();;
					if(!buyUpgrade(item)) JOptionPane.showMessageDialog(null, "Not enough julefingere!");
					else JOptionPane.showMessageDialog(null, "Transaction was completed! "+price+" julefingere is now removed from your account!");
				}	
            });
        }
        buyButtons[0].setY(targetY+210);
        buyButtons[1].setY(targetY+330);
        buyButtons[2].setY(targetY+440);
        buyButtons[3].setY(targetY+555);
    }
	
	private void setButtonVisiblility(boolean b) {
		for(Button button : buyButtons) button.setVisible(b);
	}
	
    private boolean buyUpgrade(Powerups item) {
        int price = item.getPriceForNextLevel();
        if(price<0) return false;

        if(!currency.trekkIfraJulefingere(price)) return false;

        item.increaseLevel();
        return true;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
    	if(isVisible) {
    		y = height;
    		setButtonVisiblility(false);
    	}
    	
        this.isVisible = isVisible;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void draw(Graphics2D g) {
    	g.drawImage(background, x ,y, null);
    	for(Button b:buyButtons) b.draw(g);
    	
    	g.setFont(PRICE_AND_LEVEL_FONT);
    	g.setColor(Color.WHITE);
    	
    	
    	g.setColor(Color.WHITE);
    	int price, level, max_level;
    	level = shopItems[0].getLevel();
    	max_level = shopItems[0].getMaxLevel();
    	price = shopItems[0].getPriceForNextLevel();
    	g.drawString(String.format("Level %s/%s", level, max_level), x+140,  y+235);
    	if(!harRaad(price)) g.setColor(Color.RED);
    	g.drawString(String.format("Price: %s J", price), x+140,  y+265);
    	
    	g.setColor(Color.WHITE);
    	level = shopItems[1].getLevel();
    	max_level = shopItems[1].getMaxLevel();
    	price = shopItems[1].getPriceForNextLevel();
    	g.drawString(String.format("Level %s/%s", level, max_level), x+145,  y+350);
    	if(!harRaad(price)) g.setColor(Color.RED);
    	g.drawString(String.format("Price: %s J", price), x+145,  y+380);
    	
    	g.setColor(Color.WHITE);
    	level = shopItems[2].getLevel();
    	max_level = shopItems[2].getMaxLevel();
    	price = shopItems[2].getPriceForNextLevel();
    	g.drawString(String.format("Level %s/%s", level, max_level), x+145,  y+460);
    	if(!harRaad(price)) g.setColor(Color.RED);
    	g.drawString(String.format("Price: %s J", price), x+145,  y+490);
    	
    	g.setColor(Color.WHITE);
    	level = shopItems[3].getLevel();
    	max_level = shopItems[3].getMaxLevel();
    	price = shopItems[3].getPriceForNextLevel();
    	g.drawString(String.format("Level %s/%s", level, max_level), x+145,  y+570);
    	if(!harRaad(price)) g.setColor(Color.RED);
    	g.drawString(String.format("Price: %s J", price), x+145,  y+600);
    	
    	exitButton.draw(g);
    }

	private boolean harRaad(int price) {
		return currency.validateBuy(price);
	}

	public void isHit(int x, int y, int type) {
		if(!isVisible) return;
		for(Button b:buyButtons) b.checkMouseInput(x, y, type);
		exitButton.checkMouseInput(x, y, type);
	}
	
	public void update() {
		if(!isVisible) return;
		if(y!=targetY)
			y-=animationSpeed *(y/250F); 
		if(y<=targetY) {
			setButtonVisiblility(true);
			y = targetY;
		}
	}
	
	
}