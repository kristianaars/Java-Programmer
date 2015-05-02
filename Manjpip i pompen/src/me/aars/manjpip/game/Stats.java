package me.aars.manjpip.game;

public enum Stats {
	
	timePlayed("Timeplayed", "Time played", -1, Stats.FORMAT_TIME),
	timedied("timesdied", "Times Lost"),
	
	julefingereTjent("ChristmasfingersEarned", "Julefingere earned"),
	hviteManjpiper("hvitemanjpiper", "White penises collected"),
	sorteManjpiper("sortmanjpip", "Black penises collected"),
	condomsCollected("condomscollected", "Condoms collected"),
	lubecollected("lubecollected", "Lube Collected"),
	
	hplost("healthlost", "HP lost"),
	totalPoints("totalpoints", "Totoal Score");
	
	public static final int FORMAT_TIME = 1;
	
	private int format = 0;
	
	public long value;
	public long maxValue;
	
	public String name;
	private String property;
	
	private static Filemanager filemanager = new Filemanager();
	
	private Stats(String property, String displayName) {
		this.property = property;
		name = displayName;
		loadValue(property);
		maxValue = -1;
	}
	
	private Stats(String property, String displayName, int maxValue) {
		this(property, displayName);
		this.maxValue = maxValue;
	}
	
	private Stats(String property, String displayName, int maxValue, int format) {
		this(property, displayName, maxValue);
		this.format = format;
	}
	
	private void loadValue(String property) {
		String s = getProperty(property);
		value = stringToInt(s);
	}
	
	private String getProperty(String property) {
		Filemanager f = getDefaultFileManager();
		String s = f.getKeyData(property);
		if(s==null) {
			s = createProperty(property);
		}
		return s;
	}

	private long stringToInt(String s) {
		long i;
		
		try{
			i = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			System.out.println("Error converting string to int wit String: "+s);
			i = 0;
		}
		
		return validateInt(i);
	}

	private long validateInt(long i) {
		if(i < 0L) setValue(property, 0);
		if(i < maxValue&&maxValue!=-1) i = maxValue;
		return i;
	}
	
	public String getFormattedValue() {
		if(format == FORMAT_TIME)
			return formatMsToHHMMSS(value);
		return ""+value;
	}

	private String formatMsToHHMMSS(long i) {
		i = i / 1000;
		long s = i  % 60;
	    long m = (i/60) % 60;
	    long h = (i /(60 * 60));
	    
	    return String.format("%02d:%02d:%02d", h, m, s);
	}

	private String createProperty(String property) {
		//Creates new property and return the value
		setValue(property, 0);
		return getProperty(property);
	}
	
	private void setProperty(String property, String s) {
		Filemanager f = getDefaultFileManager();
		f.writeToProperty(property, s);
	}
	
	public void setValue(String property, long i) {
		long newValue = validateInt(i);
		setProperty(property, ""+newValue);
		value = newValue;
	}

	private Filemanager getDefaultFileManager() {
		if(filemanager==null) filemanager = new Filemanager();
		return filemanager;
	}

	public void addToValue(long i) {
		long newValue = value+i;
		setValue(property, newValue);
	}
	
	public void resetValues() {
		for(Stats s : values())
			s.setValue(property, 0);
	}
}
