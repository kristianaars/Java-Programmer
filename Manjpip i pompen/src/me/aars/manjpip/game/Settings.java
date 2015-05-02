package me.aars.manjpip.game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import me.aars.views.button.Button;

public class Settings {
	
	private Button costumBackground;
	private Button exit;
	
	private Filemanager settingsfile;
	
	private static final String[] PROPERTIES = new String[]{"sounds", "fps", "background"};
	
	public boolean SOUND;
	public int FPS;
	public Image BACKGROUND;
	
	private int width;
	
	private static final boolean DEFAULT_SOUND = false;
	private static final int DEFAULT_FPS = 60;
	private final Image DEFAULT_BACKGROUND;
	
	private boolean isCostumBackground = false;

	public boolean isVisible = false;
	
	public Settings(int width, int height) {
		this.width = width;
		settingsfile = new Filemanager();
		DEFAULT_BACKGROUND = loadDefaultBackground();
		BACKGROUND = getBackground();
		loadButtons();
	}
	
	private void updateButtonText() {
		String text;
		String text1 = "Change background!";
		String text2 = "Revert background";
		
		if(isCostumBackground) text = text2;
		else text = text1;
		
		costumBackground.setText(text);
		costumBackground.paintButtons();
	}
	
	private void loadButtons() {
		costumBackground = new Button(300, 60, Button.DEFAULT_BUTTON_COLOR, "");
		costumBackground.alignMiddleX(width);
		costumBackground.setY(200);
		costumBackground.addAction(backgroundAction());
		
		exit = new Button(300, 60, Button.DEFAULT_BUTTON_COLOR, "  Main menu  ");
		exit.alignMiddleX(width);
		exit.setY(550);
		exit.addAction(new AbstractAction(){public void actionPerformed(ActionEvent e) {isVisible = false;}});
		updateButtonText();
	}

	private AbstractAction backgroundAction() {
		return new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!isCostumBackground) askForNewBackground();
				else setDefaultBackground();
				System.out.println(isCostumBackground);
			}
			
		};
	}

	private void setDefaultBackground() {
		int i = JOptionPane.showConfirmDialog(null, "Are you sure you want delete your beautiful background?");
		if(i != 0) return;
		settingsfile.writeToProperty(PROPERTIES[2], "DEFAULT");
		BACKGROUND = DEFAULT_BACKGROUND;
		isCostumBackground = false;
		updateButtonText();
	}

	private void askForNewBackground() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int i = fileChooser.showOpenDialog(null);
		if(i == JFileChooser.APPROVE_OPTION) {	
			String s = fileChooser.getSelectedFile().getPath();
			if(s.endsWith(".jpg") || s.endsWith(".png")) {
				try {
					BACKGROUND = ImageIO.read(new File(s));
					settingsfile.writeToProperty(PROPERTIES[2], s);
					isCostumBackground = true;
					updateButtonText();
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(0);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Image-file not supported! (Must be .png or .jpg)");
				return;
			}
		}
	}

	public Image getBackground() {
		String path = settingsfile.getKeyData(PROPERTIES[2]);
		
		if(path==null) {
			settingsfile.writeToProperty(PROPERTIES[2], "DEFAULT");
			path = "DEFAULT";
		}
		
		
		if(path.equals("DEFAULT")) {
			isCostumBackground = false;
			return DEFAULT_BACKGROUND;
		}
		
		File imageFile = new File(path);
		
		if(!imageFile.canRead()) {
			JOptionPane.showMessageDialog(null, "Error loading background! Using default instead...");
			isCostumBackground = false;
			return DEFAULT_BACKGROUND;
		}
		
		Image img;
		try {
			img = ImageIO.read(imageFile);
			isCostumBackground = true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error loading background! Using default instead...");
			return DEFAULT_BACKGROUND;
		}
		
		return img;
	}
	
	private Image loadDefaultBackground() {
		Image i = null;
		try {
			i = ImageIO.read(getClass().getResource("/me/aars/manjpip/ui/background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return i;
	}
	
	public void draw(Graphics2D g) {
		if(!isVisible) return;
		costumBackground.draw(g);
		exit.draw(g);
	}
	
	public void isHit(int x, int y, int type) {
		if(!isVisible) return;
		costumBackground.checkMouseInput(x, y, type);
		exit.checkMouseInput(x, y, type);
	}

}
