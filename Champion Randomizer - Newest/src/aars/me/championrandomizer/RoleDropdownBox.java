package aars.me.championrandomizer;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import roles.Role;


public class RoleDropdownBox {
	
	private Image dropdown;
	private final Role[] roles;
	private final Championview cv;
	private int selectedIndex;
	
	private final Font font = new Font("Calibri", Font.BOLD, 18);
	private final Color textColor = new Color(40, 40, 40);
	private final Color dropDownColor = new Color(255, 232, 160);
	private final Color lineColor = new Color(0.5F, 0.5F, 0.5F, 0.5F);
	
	private int visualX;
	private int visualY;
	private int acctualX;
	private int acctualY;
	
	private int dropdownX;
	
	private int width = 154;
	private int height = 31;
	
	private int dropdownWidth = 150;
	private int dropdownHeight = 22;
	
	private AudioClip clip;
	
	private boolean isDropped = true;
	
	public RoleDropdownBox(int w, Role[] roles, Championview cv) {
		this.roles = roles;
		this.cv = cv;
		
		loadClip();
		
		try {
			this.dropdown = ImageIO.read(getClass().getResource("/UI/dropdown.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int wd = dropdown.getWidth(null);
		int ht = dropdown.getHeight(null);
		
		acctualX = (w-wd)/2;
		acctualY = 200;
		visualX = (w-width)/2;
		visualY = acctualY;
		dropdownX = (w-dropdownWidth)/2;
		
		setIndex(0);
	}
	
	public boolean isHit(int xPos, int yPos, int mouseMode) {
		if(cv.isDrawing()) return false;
			if (isDropped) {
				int y = acctualY + (height);
				for (int i = 0; i < roles.length; i++) {
					if (i != selectedIndex) {
						if (xPos > dropdownX
								&& xPos < dropdownX + dropdownWidth && yPos > y
								&& yPos < y + dropdownHeight) {
							if(mouseMode==MouseInterface.MOUSE_NOT_PRESSED||mouseMode == MouseInterface.MOUSE_PRESSED) return true;
							if(mouseMode==MouseInterface.MOUSE_RELEASED) {	
								disableDrop();
								setIndex(i);
								AudioPlayer.play(clip);
								return true;
							}
						}
						y += dropdownHeight;
					}
				}
				if(mouseMode==MouseInterface.MOUSE_RELEASED)
					disableDrop();
				return false;
			} else {
				if (xPos > visualX && xPos < visualX + width && yPos > visualY
						&& yPos < visualY + height) {
					if(mouseMode==MouseInterface.MOUSE_NOT_PRESSED||mouseMode == MouseInterface.MOUSE_PRESSED) return true;
					if(mouseMode==MouseInterface.MOUSE_RELEASED) {	
						toggleDropdown();
						return true;
					}
				}
			}
		return false;
	}
	
	private void loadClip() {
		String filename = null;
		try {	
			filename = "rolldown.wav";
			URL in = getClass().getResource("/UI/"+filename);
			clip = Applet.newAudioClip(in);
		} catch(NullPointerException|ExceptionInInitializerError e) {
			System.out.println("Error loading audioclip for: "+filename);
		}
	}
	
	private void setIndex(int i) {
		selectedIndex = i;
		cv.setRole(i);
	}

	private void toggleDropdown() {
		if(!cv.isDrawing())
		if(isDropped) disableDrop();
		else {
			enableDrop();
		}
	}
	
	private float rolesLngt;
	private float animationSpeed;
	
	public void update() {
			if(isDropped) {
				if(rolesLngt<roles.length&&rolesLngt>=0) rolesLngt += animationSpeed;
				if(rolesLngt>roles.length) rolesLngt = roles.length;
				
				if(rolesLngt<=0) {
					rolesLngt = 0;
					isDropped = false;
				}
		}
	}

	public void drawCanvas(Graphics2D g) {
		g.setFont(font);
		
		if(isDropped) {
			int y = acctualY+height-3;
			
			g.setColor(dropDownColor);
			g.fillRect(dropdownX, visualY, dropdownWidth, (int)(dropdownHeight*(rolesLngt+1))-15);
			
			g.setColor(lineColor);
			g.drawRect(dropdownX, visualY+dropdownHeight-22, dropdownWidth, (int)(dropdownHeight*(rolesLngt+1))-15);
			
			for (int i = 0; i < rolesLngt; i++) {
				if (i != selectedIndex) {
					g.setColor(textColor);
					g.drawString(roles[i].getRoleName(), dropdownX + 6, y+ dropdownHeight - 5);
					g.setColor(lineColor);
					if (i != rolesLngt - 1)
						g.drawLine(dropdownX + 5, y + dropdownHeight, dropdownX + 5 + 140, y + dropdownHeight);
					y += dropdownHeight;
				}
			}
			
		}
		
		g.drawImage(dropdown, acctualX, acctualY, null);
		
		g.setColor(textColor);
		if(cv.isDrawing())
			g.setColor(lineColor.darker());
		g.drawString(roles[selectedIndex].getRoleName(), visualX+2, visualY+20);

	}
	
	private void disableDrop() {
		animationSpeed = -0.6F;
		rolesLngt += animationSpeed;
		//isDropped = false is done in update function
	}
	
	private void enableDrop() {
		animationSpeed = 0.6F;
		isDropped = true;
	}


	public boolean getState() {
		return isDropped;
	}
	
	public void setState(boolean b) {
		isDropped = b;
	}

	public void toggleState() {
		if(isDropped) {
			isDropped = false;
			return;
		}
	isDropped = true;
	}

//	public void playSound() {
//		if (!sound.isPlaying()) 
//			sound.start();
//	}
}
