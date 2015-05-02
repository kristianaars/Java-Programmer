package aars.me.championrandomizer;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;
import javax.swing.Timer;

import roles.*;

public class Display extends JComponent implements Runnable, MouseListener, MouseMotionListener, KeyListener, MouseWheelListener {
	
	private final static int MOUSE_OFFSET = 0;
	
	public final static int Width = 450;
	public final static int Height = 490;
	private long period ;
	
	private long cooldown = 40;
	
	private Timer timer;
	
	private Background background;
	private Logo logo;
	private Championview cv;
	private RandomButton button;
	private RoleDropdownBox dropdown;
	private ChampionMenu menu;
	private menuButton menub;
	private Build buildMenu;
	private ItemBuildSwitch buildButton;
	
	public final static Settings SETTINGS = new Settings();

	private static final int NO_DELAYS_PER_YIELD = 16;
	private static final int MAX_FRAME_SKIPS = 5;
	
	private boolean isRunning = false;

	private long gameStartTime;

	private Image drawImg;

	private Graphics2D g2d;
	
	public Display(long period, Background background) {
		this.period = period*1000000L;
		prepeareUi(background);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		requestFocusInWindow(true);
	}
	
	public void run() {
		long beforeTime,
			 afterTime,
			 timeDiff,
			 sleepTime,
			 overSleepTime = 0L,
			 excess = 0L;
		
		int noDelays = 0;
		beforeTime = System.nanoTime();
		gameStartTime = beforeTime;
		
		isRunning = true;
		while(isRunning) {
			update();
			drawToBuffer();
			paintToScreen();
			
			afterTime = System.nanoTime();
			timeDiff = afterTime-beforeTime;
			sleepTime = (period-timeDiff) - overSleepTime;
			
			if(sleepTime>0) {
				try{
					Thread.sleep(sleepTime/1000000L);
				}catch(InterruptedException e){}
				overSleepTime = (System.nanoTime()-afterTime)-sleepTime;
			} else {
				excess -= sleepTime;
				overSleepTime = 0L;
				
				if(++noDelays>=NO_DELAYS_PER_YIELD) {
					Thread.yield();
					noDelays = 0;
				}
			}
			
			beforeTime = System.nanoTime();
			
			int skips = 0;
			while(excess > period && skips < MAX_FRAME_SKIPS) {
				excess -= period;
				update();
				skips++;
			}
		}
		
		System.exit(0);
	}
	
	private void paintToScreen() {
		Graphics2D g;
		if(drawImg!=null) {
			g = (Graphics2D) getGraphics();
			g.drawImage(drawImg, 0, 0, null);
		} else {
			return;
		}
	}

	private void drawToBuffer() {
		if(drawImg==null) {
			drawImg = createImage(Width, Height);
			if(drawImg==null) {
				System.out.println("DrawImg equals null!");
				isRunning = false;
				return;
			} else {
				g2d = (Graphics2D) drawImg.getGraphics();	
			}
		}
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, Width, Height);
		
		
		if(menu.isVisible()) {
			background.draw(g2d);
			logo.draw(g2d);
			menu.draw(g2d);
			menub.Draw(g2d);
			return;
		}
		
		if(buildMenu.getIsVisible()) {
			background.draw(g2d);
			buildMenu.draw(g2d);
			buildButton.draw(g2d);
			return;
		}
		
		background.draw(g2d);
		logo.draw(g2d);
		menub.Draw(g2d);
		button.Draw(g2d);
		cv.draw(g2d);
		dropdown.drawCanvas(g2d);
		buildButton.draw(g2d);
		
		
	}
	
	public void addNotify() {
		super.addNotify();
		start();
	}

	public void start() {
		if(!isRunning) {
			isRunning = true;
			Thread t = new Thread(this);
			t.start();
		}
	}
	
	public void stop() {
		if(isRunning)
			isRunning = false;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(Width, Height);
	}
	
	private void prepeareUi(Background bg) {
		Role[] roles = loadRoles();

		background = bg;
		logo = new Logo(Width);
		buildMenu = new Build();
		cv = new Championview(Width, roles, buildMenu);
		button = new RandomButton(Width, cv);
		dropdown = new RoleDropdownBox(Width, roles, cv);
		menu = new ChampionMenu(roles, Width, background, this);
		menub = new menuButton(menu);
		buildButton = new ItemBuildSwitch(Height, buildMenu, background);
		}
	
	private Role[] loadRoles() {
		Role[] roles = new Role[6];
		roles[0] = new Role("All Champions", 0);
		roles[1] = new Top("Top", 1);
		roles[2] = new Jungle("Jungle", 2);
		roles[3] = new Mid("Mid", 3);
		roles[4] = new Adc("Marksman", 4);
		roles[5] = new Support("Support", 5);
		
		for(int i = 0; i<roles.length; i++) {
			roles[i].loadChampions();
		}
		return roles;
	}
	
	private void update() {
		background.update();
		cv.update();
		dropdown.update();
		buildMenu.update();
		menu.update();
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton()!=MouseEvent.BUTTON1) return;
		int x = e.getX();
		int y = e.getY()-MOUSE_OFFSET;
		checkMouseInput(MouseInterface.MOUSE_PRESSED, x, y);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton()!=MouseEvent.BUTTON1) return;
		int x = e.getX();
		int y = e.getY()-MOUSE_OFFSET;
		checkMouseInput(MouseInterface.MOUSE_RELEASED, x, y);
	}

	private void checkMouseInput(int mouseMode, int x, int y) {
		if(menub.isHit(x, y, mouseMode, buildMenu.getIsVisible())){setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); return;}
		else if(buildButton.isHit(x, y, mouseMode, menub.getEnabled())) setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		else if(buildMenu.getIsVisible()){ setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); return;}
		else if(menu.isHit(x, y, mouseMode)) setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		else if(dropdown.isHit(x, y, mouseMode)) setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		else if(button.isHit(x, y, mouseMode)) setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		else setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY()-MOUSE_OFFSET;
		checkMouseInput(MouseInterface.MOUSE_NOT_PRESSED, x, y);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		menu.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		menu.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		menu.keyTyped(e);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		menu.checkScroll(notches);
	}

}
