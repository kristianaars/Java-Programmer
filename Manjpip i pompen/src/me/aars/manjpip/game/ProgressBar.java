package me.aars.manjpip.game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Kristian Aars on 12.04.2015.
 */

public class ProgressBar {

    private Image img;

    private int width;
    private int height;
    private int x;
    private int y;

    private float precentage;
    private float visualPrecentage;

    private final static float ANIMATIONSPEED = 0.05F;

    private ProgressBar(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        precentage = 0.0F;
    }

    public ProgressBar(Color c, int width, int height, int x, int y) {
        this(width, height, x, y);
        img = createImage(c);
    }


    public ProgressBar(Image i, int width, int height, int x, int y) {
        this(width, height, x, y);
        img = i;
    }

    private Image createImage(Color c) {
        Image i = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = i.getGraphics();
        g.setColor(c);
        g.fillRect(0, 0, width, height);
        g.dispose();
        return i;
    }

    public void draw(Graphics2D g) {
        int width = (int) ((float)this.width*visualPrecentage);
        g.drawImage(img, x, y, x + width, y + height, 0, 0, width, height, null);
       //System.out.printf("x: %s, y: %s, width: %s, height: %s \n", x, y, visualPrecentage, height);
    }

    public void setPrecentage(float i) {
        precentage = i;
        if(precentage>1) precentage = 1;
    }

    public void update() {
        if(visualPrecentage!=precentage) {
            if(visualPrecentage>precentage) {
                visualPrecentage-=ANIMATIONSPEED;
                if(visualPrecentage<precentage) visualPrecentage = precentage;
            } else if(visualPrecentage<precentage) {
                visualPrecentage+=ANIMATIONSPEED;
                if(visualPrecentage>precentage) visualPrecentage = precentage;
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }
}
