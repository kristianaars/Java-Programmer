package roles;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum Item {
	
	standard("Standard"),
	healthpot("Healing Potion"),
	doransring("Doran's Ring"),
	hourglass("Hourglass"),
	liandry("Liandry's Torment"),
	rabadonsdeathcap("Deathcap"),
	sorceresshoes("Sorcerer's Shoes"),
	voidstaff("Void Staff"),
	nashoorstooth("Nashoors Tooth"),
	sapphirecrystal("Saphire Crystal"),
	abyssalscepter("Abysall Scepter"),
	frozenheart("Frozen Heart"),
	rodofages("Rod Of Ages"),
	archangelsstaff("Archangles Staff"),
	lichbane("Lich Bane"),
	morellonicon("Morellonicon"),
	unholygrale("Athene's Unholy Grail"),
	bootsofspeed("Boots Of Speed"),
	iceborn("Iceborn Gauntlet"),
	crysallineflask("Crystalline Flask"),
	banshees("Banshee's Veil"),
	rejuvenation("Rejuvenation"),
	hextech("Hextech Gunblade"), 
	doransblade("Doran's Blade"), 
	rylais("Rylai's Crystal Scepter"), 
	spiritvisage("Spirit Visage"), 
	manapotion("Mana Potion"), 
	longsword("Long Sword"), 
	hydra("Ravenous Hydra"), 
	mobilityboots("Mobility Boots"), 
	bloodthirster("Bloodthirster"), 
	blackcleaver("The Black Cleaver"), 
	lastwhisper("Last Whisper"), 
	ghostblade("Youmuu's Ghostblade"), 
	infinityedge("Infinity Edge"), 
	perfecthexcore("Perfect Hex Core"), 
	ionianboots("Ionian Boots"), 
	botrk("Ruined king's blade"), 
	statikkshiv("Statikk Shiv"),
	berserkers("Berserker's Greaves"), 
	deathfire("Deathfire"),
	trinket_green("Warding Totem"),
	ward_green("Stealth ward"), 
	spellthief("Spellthief"),
	sightstone("Sightstone"), 
	frostqueen("Frostqueen"), 
	mikaels("Mikaels"),
	manamune("Manamune"), 
	phantomdancer("Phantom Dancer");
	
	private Image img;
	private String name;
	
	private Log log = new Log("Item.class");
	
	private static final int WIDTH = 45;
	
	Item(String name) {
		
		this.name = name;
		
		try {
			Image i = ImageIO.read(getClass().getResource("/items/icons/"+toString()+".png"));
			img = getScaledImage(i, WIDTH);
		} catch (IOException | IllegalArgumentException e) {
			log.printMsg("Can't find "+"/items/icons/"+toString()+".png", log.ERROR_MESSAGE);
			Image i = null;
		}
	}
	
	private Image getScaledImage(Image i, int width) {
		Image it = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
		Graphics g = it.getGraphics();
		g.drawImage(i, 0, 0, width, width, null);
		g.dispose();
		return it;
	}
	
	public Image getIcon() {
		return img;
	}
	
	public String getName() {
		return name;
	}
} 
