package me.aars.GunsAreFun.graphics;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import me.aars.GunsAreFun.Display;
import me.aars.GunsAreFun.Game;

public class Screen extends Render{
	
	private Render3D floor;
	private Random random = new Random();
	
	public Screen(int W, int H) {
		super(W, H);
		floor = new Render3D(W, H);
	}
	
	private Render test = Param.param;
	
	public void render(Game game) {
		for (int i = 0; i < getPixelArraySize(); i++) {
			pixels[i] = Color.BLACK.getRGB();
		}
		
		
		long currentTime = System.currentTimeMillis();
		
		floor.floor(game);
		floor.renderDistanceLimiter();
		draw(floor, 0 ,0);
		
		for(int i = 0; i<10; i++) {	
			double y = (Math.cos((currentTime + i * 10)% 2000.0 / 2000.0 * Math.PI * 2F) * 200F);
			double x = (Math.sin((currentTime + i * 10) % 2000.0 / 2000.0 * Math.PI * 2F) * 200F);
			//draw(test, (WIDTH-256)/2+(int)x, (HEIGHT-256)/2+(int)y);
		}
	}

}
