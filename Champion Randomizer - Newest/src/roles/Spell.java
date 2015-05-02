package roles;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum Spell {
	
	clarity("Clarity"),
	ghost("Ghost"),
	heal("Heal"),
	revive("Revive"),
	barrier("Barrier"),
	exhaust("Exhaust"),
	cleanse("Cleanse"),
	teleport("Teleport"),
	clairvoyance("Clairvoyance"),
	flash("Flash"),
	ignite("Ignite"),
	smite("Smite");
	
	private String name;
	private Image icon;
	
	private static final int ICON_WIDTH = 30;
	private Log log = new Log("Spell.enum");
	
	Spell(String s) {
		name = s;
		loadIcon();
	}
	
	private void loadIcon() {
		Thread t = new Thread() {
			public void run()  {
				try {
					Image i = ImageIO.read(getClass().getResource("/spells/icons/"+name+".png"));
					icon = getScaledImage(i, ICON_WIDTH);
				} catch (IOException | IllegalArgumentException e) {
					log.printMsg("Can't find "+"/spells/icons/"+name+".png", log.ERROR_MESSAGE);
				}
			}
		};
		t.start();
	}
	
	private Image getScaledImage(Image source, int width) {
		Image i = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB);
		Graphics g = i.getGraphics();
		g.drawImage(source, 0, 0, width, width, null);
		return i;
	}

	public String getName() {
		return name;
	}
	
	public Image getIcon() {
		return icon;
	}
	
}
