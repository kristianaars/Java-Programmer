package me.aars.GunsAreFun;

import java.awt.event.KeyEvent;

import me.aars.GunsAreFun.graphics.Render3D;
import me.aars.GunsAreFun.graphics.Screen;
import me.aars.GunsAreFun.input.Controller;
import me.aars.GunsAreFun.input.InputHandler;

public class Game {
	
	public int time;
	public Controller c;
	
	public int newX;
	public int newY;
	
	public int oldX;
	public int oldY;
	
	public Game() {
		c = new Controller();
	}
	
	public void tick(boolean[] key) {
		time++;;
		
		boolean forward = key[KeyEvent.VK_W];
		boolean backward = key[KeyEvent.VK_S];
		boolean right = key[KeyEvent.VK_D];
		boolean left = key[KeyEvent.VK_A];
		boolean shift = key[KeyEvent.VK_SHIFT];
		
		c.tick(forward, backward, right, left, shift);
	}

}