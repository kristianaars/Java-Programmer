package aars.me.championrandomizer;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class RandomButton {
	private Rectangle buttonHitbox;
	private int x;
	private int y;

	private Championview cv;

	private final int PanelWidth;

	private AudioClip clip;

	private Image currentlyDisplayed;
	private Image button;
	private Image buttonPressed;
	private Image buttonHover;
	
	public RandomButton(int Width, Championview cv) {
		PanelWidth = Width;
		this.cv = cv;
		loadImages();
		loadClip();
		createRect();
	}
	
	private void loadClip() {
		String filename = null;
		try {	
			filename = "button.wav";
			URL in = getClass().getResource("/UI/"+filename);
			clip = Applet.newAudioClip(in);
		} catch(NullPointerException|ExceptionInInitializerError e) {
			System.out.println("Error loading audioclip for: "+filename);
		}
	}
	
	private void createRect() {
		int width = currentlyDisplayed.getWidth(null);
		int height = currentlyDisplayed.getHeight(null);
		
		x = (PanelWidth-width)/2;
		y = 260-(height/2);
		buttonHitbox = new Rectangle(x, y, width, height);
	}
	
	private void loadImages() {
		try {
			button = ImageIO.read(getClass().getResource("/UI/button.png"));
			buttonPressed = ImageIO.read(getClass().getResource("/UI/buttonpressed.png"));
			buttonHover = ImageIO.read(getClass().getResource("/UI/buttonrollover.png"));
			currentlyDisplayed = button;
		} catch (IOException e) {
			System.out.println("button.png or buttonpressed.png not found...");
		}
	}
	
	public void Draw(Graphics2D g) {
		g.drawImage(currentlyDisplayed, x, y, null);
	}
	
	public boolean isHit(int xP, int yP, int value) {
		Rectangle mouse = new Rectangle(xP, yP, 1, 1);
		setButtonImg(button);
		
		if(mouse.intersects(buttonHitbox))
		switch(value) {
			case(MouseInterface.MOUSE_NOT_PRESSED):
				setButtonImg(buttonHover);
				return true;
			case(MouseInterface.MOUSE_PRESSED):
				AudioPlayer.play(clip);
				setButtonImg(buttonPressed);
				return true;
			case(MouseInterface.MOUSE_RELEASED):
				cv.startChampionDraw();
				setButtonImg(buttonHover);
				return true;
		}
		
		return false;
	}
	
	private void setButtonImg(Image i) {
		currentlyDisplayed = i;
		createRect();
	}
}