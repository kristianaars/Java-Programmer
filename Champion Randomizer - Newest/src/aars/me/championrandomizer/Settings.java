package aars.me.championrandomizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

public class Settings {
	
	private File settingsFile;
	private Properties settings;
	
	final String parentpath = System.getenv("APPDATA")+"\\ChampionRandomizer";

	public Settings() {
		settingsFile = new File(parentpath+"\\Settings.ini");
		settings = new Properties();

		if (!settingsFile.exists()) {
			createSettingsFile();
		}

		try {
			settings.load(new FileInputStream(settingsFile));
		} catch (IOException e) {

		}
	}

	private boolean booleanStringReader(String s) {
		return s.equals("true");
	}
	
	private String getStringBoolean(boolean b) {
		if(b) {
			return "true";
		}
		return "false";
	}
	
	private void saveFile() {
		try {
			settings.store(new FileOutputStream(settingsFile),
					"Champion Randomizer Settings");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Couldn't save file");
			e.printStackTrace();
		}
	}
	
	public boolean getProperty(String s) {
		String property = settings.getProperty(s);
		if(property==null) {
			System.out.println(s);
			writeKeyToFile(s);
			property = settings.getProperty(s);
		}
		return booleanStringReader(property); 
	}
	
	public void setProperty(String s, boolean b) {
		settings.setProperty(s, getStringBoolean(b));
		saveFile();
	}

	private void writeKeyToFile(String s) {
		setProperty(s, true);
	}

	private void createSettingsFile() {
		try {
			
			File dir = new File(parentpath);
			dir.mkdir();
			settingsFile.createNewFile();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Couldn't create filedirectory!");
		}
	}
}
