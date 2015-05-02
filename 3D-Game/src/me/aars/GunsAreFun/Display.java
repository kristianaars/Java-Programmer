package me.aars.GunsAreFun;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import me.aars.GunsAreFun.graphics.Render;
import me.aars.GunsAreFun.graphics.Screen;
import me.aars.GunsAreFun.input.InputHandler;

public class Display extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public static final int W = 800;
	public static final int H = 600;
	public static final String TITLE = "Test program: Aars.pixels.random Alpha 0.02";
	
	private Thread thread;
	private Screen screen;
	private BufferedImage img;
	private boolean running = false;
	private Game game;
	private int[] pixels;
	private InputHandler input = new InputHandler();
	private int fps;
	
	public Display() {
		Dimension size = new Dimension(W, H);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		addKeyListener(input);
		addFocusListener(input);
		addMouseMotionListener(input);
		screen = new Screen(W, H);
		game  = new Game();
		img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
		
	}
	
	private void start() {
		if(running) return;
		requestFocus();
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private void stop() {
		if(!running) return;
		running = false;
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public int oldX;
	public int newX;
	
	public void run() {
		int frames = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1/60.0;
		int tickCount = 0;
		boolean ticked = false;
		
		while(running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime  = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0;
			
			while(unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				
				if (tickCount % 60 == 0) {
					fps = frames;
					previousTime += 1000;
					frames = 0;
				}
			}
			
			if(ticked) {
				render();
				frames++;
			}
			
			render();
			frames++;
			
			newX = input.mouseX;
			
			if(oldX < newX) {
				game.c.turnRight = true;
			} else if(oldX > newX) {
				game.c.turnLeft = true;
			} else if(oldX == newX){
				game.c.turnLeft = false;
				game.c.turnRight = false;
			}
			
			oldX = newX;
		}
	}
	
	private void tick() {
		game.tick(input.key);
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
			screen.render(game);
		
		for (int i = 0; i < W*H; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics2D g = (Graphics2D)bs.getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawImage(img, 0, 0, W+10, H+10, null);
		
		drawFpsCounter(g);
		
		g.dispose();
		bs.show();
		}
	
	private void drawFpsCounter(Graphics g) { 
		Font font = new Font("Veranda", 0, 30);
		g.setColor(Color.YELLOW);
		g.setFont(font);
		g.drawString("Fps: "+fps +" Marius Amundrød", 0,  30);
	}

	public static void main(String [] args) {
		BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");
		Display game = new Display();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setCursor(blankCursor);
		frame.setTitle(TITLE);
		frame.add(game);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		game.start();
	}
}
