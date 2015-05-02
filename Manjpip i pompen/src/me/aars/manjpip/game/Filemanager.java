package me.aars.manjpip.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import me.aars.manjpip.display.Frame;

public class Filemanager {
	
	private static File settingsFile;
	private static Properties settings;
	
	final String parentpath = System.getenv("APPDATA")+"\\Manjpipipompen";

	public Filemanager() {
		if(settingsFile!=null) return;
		
		settingsFile = new File(parentpath+"\\Updater.jar");
		settings = new Properties();

		if (!settingsFile.exists()) {
			createSettingsFile();
		}

		try {
			settings.load(new FileInputStream(settingsFile));
		} catch (IOException e) {

		}
	}
	
	public String getKeyData(String property) {
		if(settings==null) return null;
		String s = settings.getProperty(property);
		return s;
	}
	
	public void writeToProperty(String property, String key) {
		if(Frame.LOGGING)
			System.out.println("Writing to property " + property + "! With the key: " + key);
		if(settings==null) return;
		settings.setProperty(property, key);
		saveFile();
	}
	
	private static void saveFile() {
		try {
			settings.store(new FileOutputStream(settingsFile),
					"Manjpip i pompen config");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Couldn't save file");
			e.printStackTrace();
		}
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
