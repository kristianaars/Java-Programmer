package me.aars.manjpip.display;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JComponent;

import me.aars.manjpip.game.*;
import me.aars.views.button.Button;
import me.aars.views.button.MouseInterface;

public class Display extends JComponent implements Runnable, KeyListener, MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;
	
	private boolean isRunning = false;
	private boolean gameOver = true;
	private boolean paused = false;
	
	private long period;
	
	public final static int WIDTH = 500;
	public final static int HEIGHT = 700;
	
	private Image drawImg;
	private Graphics2D g2d;
	
	private Pomp pomp;
	private ManjpipSpawner manjpip;
	private Points points;
	private Utilityline utilline;
	private MainMenu menu;
	private StatsView stats;
	
	//All views must be disabled when shop is enabled!!
	private Shop shop;

	private Julefinger julefinger;
	
	private Color[] colors;
	
	private Button restart;
	private Button shopButton;
	private Button menuButton;
	
	public Display(long cooldown) {
		period = cooldown;
		
		setBackground(Color.WHITE);
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		loadInterfaces();
		readyMouseInterface();
		
		System.out.println(Stats.timePlayed.getFormattedValue());
		
		colors = new Color[9];
		colors[0] = Color.BLACK;
		colors[1] = Color.BLUE;
		colors[2] = Color.CYAN;
		colors[3] = Color.GREEN;
		colors[4] = Color.MAGENTA;
		colors[5] = Color.ORANGE;
		colors[6] = Color.PINK;
		colors[7] = Color.RED;
		colors[8] = Color.YELLOW;
	}
	
	private void restartGame() {
		utilline = new Utilityline(0, HEIGHT-51, this);
		pomp = new Pomp(WIDTH, HEIGHT, utilline);
		points = new Points(utilline, pomp);
		manjpip = new ManjpipSpawner(WIDTH, HEIGHT, pomp);
		Powerups.reset();
		gameOver = false;
		restart.setVisible(false);
		shopButton.setVisible(false);
	}
	
	public void addNotify() {
		super.addNotify();
		start();
	}
	
	private void startClock() {
		Thread t = new Thread(this);
		t.start();
	}
	
	private void loadInterfaces() {
		stats = new StatsView(WIDTH, HEIGHT);
		loadShop();
		loadButtons();
		loadMenu();
		restartGame();
	}
	
	private void loadMenu() {
		menu = new MainMenu(WIDTH, HEIGHT, this);
	}

	private void loadShop() {
		julefinger = new Julefinger();
		shop = new Shop(WIDTH, HEIGHT, julefinger);
	}

	private void loadButtons() {
		restart = new Button(240, 50, Button.DEFAULT_BUTTON_COLOR, "Restart!");
		shopButton = new Button(115, 50, Button.DEFAULT_BUTTON_COLOR,  "Shop");
		menuButton = new Button(115, 50, Button.DEFAULT_BUTTON_COLOR, "Menu");
		restart.alignMiddleX(WIDTH);
		restart.setY(230);
		
		menuButton.setX(restart.getX());
		menuButton.setY(300);
		
		shopButton.setX(restart.getX()+menuButton.getWidth()+10);
		shopButton.setY(300);
		loadButtonActions();
	}

	private void loadButtonActions() {
		//Must only be used after loadButtons()!!
		AbstractAction restartAct = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				restartGame();
			}
		};
		
		AbstractAction shopAct = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				shop.setIsVisible(true);
			}
		};
		
		AbstractAction menuAct = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				menu.setVisible(true);
			}
		};
		
		restart.addAction(restartAct);
		shopButton.addAction(shopAct);
		menuButton.addAction(menuAct);
	}

	private void readyMouseInterface() {
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void start() {
		if(!isRunning) {
			isRunning = true;
			startClock();
		}
	}
	
	int fps = 0;
	int frameC = 0;

	private long gameStartTime;
	
	@Override
	public void run() {
		int frames = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = period/1000F;
		int tickCount = 0;
		boolean ticked = false;
		
		gameStartTime = System.currentTimeMillis();
		
		while(isRunning) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime  = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0;
			
			while(unprocessedSeconds > secondsPerTick) {
				update();
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
				drawToBuffer();
			}
			
			paintScreen();
			frames++;
		}
		
		setGameTime();
		
		if(Frame.LOGGING)
			System.out.println("Terminating...");
		System.exit(0);
	}
	
	private void setGameTime() {
		long currentTime = System.currentTimeMillis();
		long gameTime = currentTime - gameStartTime;
		Stats.timePlayed.addToValue(gameTime);
		System.out.println(Stats.timePlayed.getFormattedValue());
	}

	private void paintScreen() {
		Graphics g;
		try{
			g = getGraphics();
			if(g!=null&&drawImg!=null);
			g.drawImage(drawImg, 0, 0, null);
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}catch(Exception e) {
			System.out.println("Graphics context error: "+e);
		}
	}

	private void drawToBuffer() {
		if(drawImg==null) {
			drawImg = createImage(WIDTH, HEIGHT);
			if(drawImg==null) {
				System.out.println("DrawImg equals null!");
				isRunning = false;
				return;
			} else {
				g2d = (Graphics2D) drawImg.getGraphics();	
			}
		}

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.drawImage(menu.settings.BACKGROUND, 0, 0,  WIDTH, HEIGHT, null);
		
		if(shop.isVisible()) {
			shop.draw(g2d);
		} else if(menu.settings.isVisible) {
			menu.settings.draw(g2d);
		} else if(stats.isVisible) {
			stats.draw(g2d);
		} else if(menu.isVisible()) {
			menu.draw(g2d);
		} else {
			pomp.draw(g2d, 0);
			manjpip.draw(g2d);
			pomp.draw(g2d, 1);
			utilline.draw(g2d);
			points.draw(g2d);
			if(gameOver) {				
				g2d.setFont(new Font("Veranda", 0, 40));
				drawGameOver(g2d);
				restart.draw(g2d);
				menuButton.draw(g2d);
				shopButton.draw(g2d);
			}
		}
		
		
		if(Frame.LOGGING) {	
			String s = Frame.VERSION;
			g2d.setFont(new Font("Arial", 1, 25));
			int x = (WIDTH-StringUtils.findWidth(s, g2d))/2;
			g2d.setColor(Color.WHITE);
			g2d.drawString(s, x, 40);
		}

//		julefinger.draw(g2d);
//		g2d.setColor(Color.YELLOW);
//		g2d.drawString(fps+" fps", 0, 40);
		
	}

	private void drawGameOver(Graphics2D g2d) {
		Color whiteBackground = new Color(1F, 1F, 1F, 0.2F);
		g2d.setColor(whiteBackground);
		//g2d.fillRect(0, 0, WIDTH, HEIGHT);
		String s = "Game Over!!";
		int l = s.length();
		Random r = new Random();
		
		int textWidth = StringUtils.findWidth(s, g2d);
		int x = (WIDTH-textWidth)/2;
		
		for(int i = 0; i<l; i++) {
			String b = ""+s.charAt(i);
			Color clr = colors[r.nextInt(9)];
			int y = r.nextInt(3)+200;
			g2d.setColor(clr);
			g2d.drawString(b, x, y);
			x += r.nextInt(3)+StringUtils.findWidth(b, g2d);
		}
	}

	private void update() {
		if(shop.isVisible()) {
			shop.update();
		} else if(menu.isVisible()) {
			
		} else {			
			pomp.update();
			utilline.update();
			points.update();
			if (!gameOver && !paused) {
				manjpip.update();
				if (points.getALife() <= 0 && !gameOver)
					setGameOver();
			}
		}
	}
	
	private void setGameOver() {
		julefinger.addJulefinger(points.getJulefingere());
		gameOver = true;
		restart.setVisible(true);
		shopButton.setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode==KeyEvent.VK_ESCAPE) togglePause();
		if(isRunning&&!gameOver&&!paused)
			pomp.keyPressed(keyCode);
	}

	public void togglePause() {
		if(paused)unpause();
		else pause();
	}

	public void pause() {
		paused = true;
	}
	
	public void unpause() {
		paused = false;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(isRunning&&!gameOver&&!paused)
			pomp.keyReleased(keyCode);
	}
	
	private void mouseInput(int x, int y, int type) {
		if(shop.isVisible()) {
			shop.isHit(x, y, type);
		} else if(menu.settings.isVisible) {
			menu.settings.isHit(x, y, type);
		} else if(menu.isVisible()) {
			menu.isHit(x, y, type);
		}else if(gameOver){
			restart.checkMouseInput(x, y, type);
			shopButton.checkMouseInput(x, y, type);
			menuButton.checkMouseInput(x, y, type);
		} else {
			utilline.isHit(x, y, type);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if(e.getButton()==MouseEvent.BUTTON1)
			mouseInput(x, y, MouseInterface.MOUSE_PRESSED);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if(e.getButton()==MouseEvent.BUTTON1)
			mouseInput(x, y, MouseInterface.MOUSE_RELEASED);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		mouseInput(x, y, MouseInterface.MOUSE_NOT_PRESSED);
	}

	public void stop() {
		if(isRunning) isRunning = false;
	}

	public void startGame() {
		menu.setVisible(false);
		shop.setIsVisible(false);
		paused = false;
		restartGame();
	}

	public void showShop() {
		shop.setIsVisible(true);
	}

}
