/**
 * 
 */
package me.aars.manjpip.game;

/**
 * @author Kristian
 *
 */
public enum Powerups {
	
	glidemiddel(new int[]{5, 6, 8, 9, 10, 12, 15}, new int[]{0, 40, 80, 130, 180, 230, 280}, "Glidemiddel"),
	health(new int[]{25, 50, 75, 100, 125, 150, 175}, new int[]{0, 60, 120, 140, 200, 260, 310}, "Health"),
	sykdom(new int[]{10, 9, 8, 7, 6, 5, 4, 3}, new int[]{0, 20, 40, 80, 120, 170, 230}, "Klamydia"),
	rainingdick(new int[]{3, 4, 5, 6, 7, 8, 9, 10}, new int[]{0, 50, 100, 150, 200, 250, 300, 350}, "Julefingerfest");
	
	private int[] timeouts;
	private int[] price;

	private int level;
	
	private final static int MAX_LEVEL = 6;
	
	private long timeEnabled;
	
	private Filemanager filemanager;
	
	private String property;
	
	
	Powerups(int[] time, int[] price, String property) {
		filemanager = new Filemanager();
		timeouts = time;
		this.price = price;
		this.property = property;
		loadLevel();
	}
	
	private void loadLevel() {
		String s = filemanager.getKeyData(property);
		if(s==null) {
			setLevel(0);
			s = "0";
		}
		
		level = Integer.parseInt(s);
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int i) {
		String s = ""+i;
		filemanager.writeToProperty(property, s);
		level = i;
	}
	
	public boolean isEnabled() {
		return timeEnabled != 0;
	}
	
	public void enable() {
		timeEnabled = System.currentTimeMillis();
	}
	
	public void disable() {
		timeEnabled = 0;
	}
	
	public long getTimeout() {
		return timeouts[level]*1000;
	}

	public int getPriceForNextLevel() {
		if(level+1>MAX_LEVEL) return -1;
		else return price[level+1];
	}

	public int getRawTimeout() {
		//Used to get health points for the health powerup
		return timeouts[level];
	}
	
	public long getTimeEnabled() {
		return timeEnabled;
	}
	
	public int[] getLevelTable() {
		return timeouts;
	}

	public static void reset() {
		for(Powerups p: values()) p.disable();
	}

	public void increaseLevel() {
		System.out.println(property+" "+level);
		if(level<MAX_LEVEL)
			setLevel(level+1);
	}

	public int getMaxLevel() {
		return MAX_LEVEL;
	}
}
