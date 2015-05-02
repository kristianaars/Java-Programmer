package me.aars.manjpip.game;

import java.awt.*;

/**
 * Created by Kristian Aars on 12.04.2015.
 */
public class Julefinger {

    private final static String PROPERTY = "julefingere";

    private int amount = 0;

    private Filemanager f;

    public Julefinger() {
        f = new Filemanager();
        String key = f.getKeyData(PROPERTY);
        if(key==null) key = "0";
        amount = Integer.parseInt(key);
    }

    public int getJulefingere() {
        return amount;
    }

    public boolean trekkIfraJulefingere(int i) {
        if(!validateBuy(i)) {
        	System.out.println("Not sufficient amount of julefingere. Account has not been withdrawn");
        	return false;
        }
        setJulefinger(amount-i);
        
        return true;
    }

    public void addJulefinger(int i) {
    	Stats.julefingereTjent.addToValue(i);
        if(i>0) setJulefinger(amount+i);
    }

    private void setJulefinger(int i) {
        if(i<0) amount = 0;
        else amount = i;
        f.writeToProperty(PROPERTY, ""+amount);
        System.out.println("Current balance: "+amount);
    }

    public void draw(Graphics2D g) {
        g.setFont(new Font("Calibri", 0, 20));
        g.setColor(Color.BLACK);
        g.drawString("Julefingere: " + amount, 0, 20);
    }

	public boolean validateBuy(int price) {
		return (amount-price>0);
	}
}