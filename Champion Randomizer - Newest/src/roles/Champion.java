package roles;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;

import javax.imageio.ImageIO;

import aars.me.championrandomizer.AudioPlayer;
import aars.me.championrandomizer.Display;
import aars.me.championrandomizer.Settings;

public enum Champion implements Comparator{
	
	start("Champion"),
	annie("Annie"),
	kayle("Kayle"),
	masteryi("Master Yi"),
	alistar("Alistar"),
	ryze("Ryze"),
	sion("Sion"),
	sivir("Sivir"),
	soraka("Soraka"),
	teemo("Teemo"),
	tristana("Tristana"),
	warwick("Warwick"),
	nunu("Nunu"),
	ashe("Ashe"),
	tryndamere("Tryndamere"),
	jax("Jax"),
	morgana("Morgana"),
	zilean("Zilean"),
	singed("Singed"),
	evelynn("Evelynn"),
	twitch("Twitch"),
	karthus("Karthus"),
	chogath("Cho'Gath"),
	amumu("Amumu"),
	rammus("Rammus"),
	anivia("Anivia"),
	drmundo("Dr. Mundo"),
	kassadin("Kassadin"),
	twistedfate("Twisted Fate"),
	janna("Janna"),
	gangplank("Gangplank"),
	corki("Corki"),
	taric("Taric"),
	veigar("Veigar"),
	blitzcrank("Blitzcrank"),
	malphite("Malphite"),
	katarina("Katarina"),
	nasus("Nasus"),
	fiddlesticks("Fiddlesticks"),
	udyr("Udyr"),
	heimerdinger("Heimerdinger"),
	shaco("Shaco"),
	nidalee("Nidalee"),
	poppy("Poppy"),
	pantheon("Pantheon"),
	gragas("Gragas"),
	mordekaiser("Mordekaiser"),
	ezreal("Ezreal"),
	shen("Shen"),
	kennen("Kennen"),
	akali("Akali"),
	garen("Garen"),
	malzahar("Malzahar"),
	olaf("Olaf"),
	kogmaw("Kog'Maw"),
	xinzhao("Xin Zhao"),
	vladimir("Vladimir"),
	galio("Galio"),
	urgot("Urgot"),
	missfortune("Miss Fortune"),
	sona("Sona"),
	swain("Swain"),
	lux("Lux"),
	leblanc("LeBlanc"),
	irelia("Irelia"),
	trundle("Trundle"),
	cassiopeia("Cassiopeia"),
	caitlyn("Caitlyn"),
	renekton("Renekton"),
	karma("Karma"),
	maokai("Maokai"),
	jarvaniv("Jarvan IV"),
	nocturne("Nocturne"),
	leesin("Lee Sin"),
	brand("Brand"),
	rumble("Rumble"),
	vayne("Vayne"),
	orianna("Orianna"),
	yorick("Yorick"),
	leona("Leona"),
	wukong("Wukong"),
	skarner("Skarner"),
	talon("Talon"),
	riven("Riven"),
	xerath("Xerath"),
	graves("Graves"),
	shyvana("Shyvana"),
	fizz("Fizz"),
	volibear("Volibear"),
	ahri("Ahri"),
	viktor("Viktor"),
	sejuani("Sejuani"),
	ziggs("Ziggs"),
	nautilus("Nautilus"),
	fiora("Fiora"),
	lulu("Lulu"),
	hecarim("Hecarim"),
	varus("Varus"),
	darius("Darius"),
	draven("Draven"),
	jayce("Jayce"),
	zyra("Zyra"),
	diana("Diana"),
	rengar("Rengar"),
	syndra("Syndra"),
	khazix("Kha'Zix"),
	elise("Elise"),
	zed("Zed"),
	nami("Nami"),
	vi("Vi"),
	thresh("Thresh"),
	quinn("Quinn"),
	zac("Zac"),
	lissandra("Lissandra"),
	aatrox("Aatrox"),
	lucian("Lucian"),
	jinx("Jinx"),
	yasuo("Yasuo"),
	velkoz("Vel'Koz"),
	braum("Braum"),
	gnar("Gnar"),
	azir("Azir"),
	kalista("Kalista"),
	reksai("Rek'Sai"),
	bard("Bard");
	
	
	private final Log log = new Log("Champion.class");
	private Settings settings = Display.SETTINGS;
	
	private final String name;
	
	private boolean isEnabled = true;

	private BufferedImage image;
	private AudioClip audio;
	private BuildFactory[] builds;
	private int ID;
	
	Champion(String name) {
		this.name = name;
		loadImage();
		loadSoundFile();
		
		builds = new BuildFactory[6];
		
		checkIfEnabled();
	}
	
	private void loadSoundFile() {
		Thread t = new Thread() {
			public void run() {		
				String filename = null;
				try {	
					filename = name.replace(" ", "").replace("'", "").replace(".", "")+".wav";
					URL in = getClass().getResource("/champions/clips/"+filename);
					audio = Applet.newAudioClip(in);
				} catch(NullPointerException|ExceptionInInitializerError | IllegalArgumentException e) {
					System.out.println("Error loading audioclip for: "+filename);
				}
			}
		};
		t.start();
	}
	
	private void checkIfEnabled() {
		isEnabled = settings.getProperty(name);
	}
	
	private void loadImage() {
		final String n = name;
		Thread t = new Thread() {
			public void run() {		
				String name = n.replace("'", "")+"_Square_0.png";
				try {
					image = ImageIO.read(getClass().getResource("/champions/icons/"+name));
				} catch (IOException | IllegalArgumentException e) {
					log.printMsg("Not Found: "+name, log.ERROR_MESSAGE);
				}
			}
		};
		t.start();
	}
	
	public String getName() {
		return name;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void playSound() {
		
	}

	public void addBuild(BuildFactory b, int i) {
		builds[i] = b;
	}
	
	public void playAudio() {
		AudioPlayer.play(audio);
	}
	
	public void toggleEnabled() {
		if(isEnabled) setEnabled(false);
		else setEnabled(true);
	}
	
	public synchronized void setEnabled(boolean b) {
		settings.setProperty(name, b);
		isEnabled = b;
	}

	public synchronized boolean checkEnabled() {
		return isEnabled;
	}

	public int getID() {
		return ID;
	}
	
	public String toString() {
		String s = name;
		if(s.equalsIgnoreCase("Champion")) s = "aaaaa";
		return s;
	}

	@Override
	public int compare(Object o1, Object o2) {
		String s1 = o1.toString();
		String s2 = o2.toString();
		int i = s1.compareToIgnoreCase(s2);
		return i;
	}

	public void setID(int i) {
		ID = i;
		//log.printMsg("Name: "+name+" ID: "+ID, log.DEBUG_MESSAGE);
	}
	
	public BuildFactory[] getBuilds() {
		return builds;
	}
}