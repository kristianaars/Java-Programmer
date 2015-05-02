package me.aars.manjpip.updater;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Updater {
	
	final String parentpath = System.getenv("APPDATA")+"\\Manjpipipompen";
	final String LOCAL_VERSION = parentpath+"/ver.txt/";
	final String LOCAL_APPLICATION = parentpath+"\\manjpip.jar";
	final String LOCAL_APPLICATION_LINK = parentpath+"\\link.txt";
	
	final String VERSION_LINK = "https://dl.dropboxusercontent.com/s/04fs4aoyncthypa/version.txt?dl=0";
	final String APPLICATION_LINK = "https://dl.dropboxusercontent.com/s/p8ufs5dcl4i5mna/link.txt?dl=0";
	
	static boolean ThreadActive = false;
	
	public static void main(String[] args) {
		Updater up = new Updater();
		while(ThreadActive) {
		}
		up.runApp();
	}
	
	public Updater() {
		int localversion = 0, serverversion = 0;
		
		File p = new File(parentpath);
		if(!p.isDirectory()) {
			if (!p.mkdir()) {
				JOptionPane.showMessageDialog(null, "Couldt create filepath! Make sure you are running with adminrights!");
				System.exit(0);
			}
		}

		try{
			File f = new File(LOCAL_VERSION);
			if(!f.exists()) {
				f.createNewFile();
				BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsolutePath()));
				bw.write("0");
				bw.close();
			}
			BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));
			String s = br.readLine();
			try {
				localversion = Integer.parseInt(s);
			}catch(NumberFormatException e) {
				f.createNewFile();
				BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsolutePath()));
				bw.write("0");
				bw.close();
				br = new BufferedReader(new FileReader(f.getAbsolutePath()));
				s = br.readLine();
				localversion = Integer.parseInt(s);
				br.close();
			}
			
			br.close();
			
			URL website = new URL(VERSION_LINK);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(LOCAL_VERSION);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			br = new BufferedReader(new FileReader(LOCAL_VERSION));
			s = br.readLine();
			serverversion = Integer.parseInt(s);
			
			System.out.println(serverversion);
			System.out.println(localversion);
			
		} 	catch (IOException e) {
			e.printStackTrace();
		}
		
		if (serverversion!=localversion) {
			final JTextArea desc = new JTextArea("Updating Manjpip i Pompen...");
			desc.setEditable(false);
			desc.setBackground(new Color(0F, 0F, 0F, 0F));
			final JProgressBar jProgressBar = new JProgressBar();
			jProgressBar.setMaximum(100000);
			jProgressBar.setSize(200, 100);
			JFrame frame = new JFrame();
			frame.setResizable(false);
			frame.setTitle("Updating...");
			frame.setLayout(new FlowLayout());
			frame.add(desc);
			frame.add(jProgressBar);
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			frame.setSize(300, 100);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			
			File f = new File(LOCAL_APPLICATION_LINK);
			if(!f.exists()) {
				try {
					f.createNewFile();
					BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsolutePath()));
					bw.write("0");
					bw.close();
				} catch (IOException e) {
					System.exit(1);
				}
			}
			
			String urlpath = null;
			
			URL website;
			try {
				website = new URL(APPLICATION_LINK);
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos = new FileOutputStream(LOCAL_APPLICATION_LINK);
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();
				BufferedReader br = new BufferedReader(new FileReader(LOCAL_APPLICATION_LINK));
				urlpath = br.readLine();
				br.close();
			} catch (IOException e1) {
				
			}
			
			final String urlp = urlpath;

			System.out.println("Downloading: "+urlpath);
			
			if(urlpath==null) runApp();
			
			Runnable updatethread = new Runnable() {
				public void run() {
					try {
						URL url = new URL(urlp);
						HttpURLConnection httpConnection = (HttpURLConnection) (url
								.openConnection());
						long completeFileSize = httpConnection
								.getContentLength();

						java.io.BufferedInputStream in = new java.io.BufferedInputStream(
								httpConnection.getInputStream());
						java.io.FileOutputStream fos = new java.io.FileOutputStream(LOCAL_APPLICATION);
						java.io.BufferedOutputStream bout = new BufferedOutputStream(
								fos, 1024);
						byte[] data = new byte[1024];
						long downloadedFileSize = 0;
						int x = 0;
						while ((x = in.read(data, 0, 1024)) >= 0) {
							downloadedFileSize += x;

							// calculate progress
							final int currentProgress = (int) ((((double) downloadedFileSize) / ((double) completeFileSize)) * 100000d);

							// update progress bar
							SwingUtilities.invokeLater(new Runnable() {

								@Override
								public void run() {
									jProgressBar.setValue(currentProgress);
								}
							});
							bout.write(data, 0, x);
						}
						bout.close();
						in.close();
						runApp();
						ThreadActive = false;
					} catch (FileNotFoundException e) {
						
					} catch (IOException e) {
						
					}
				}
			};
			
			ThreadActive = true;
			new Thread(updatethread).start();
		}
	}
	
	private void runApp() {
		File f = new File(LOCAL_APPLICATION);
		if(!f.exists()) {
			JOptionPane.showMessageDialog(null, "Woops... Something went wrong! Please delete the following folder: " + parentpath + " and try again! ");
		}
		try {
			String jvm_location;
			if (System.getProperty("os.name").startsWith("Win")) {
			    jvm_location = System.getProperties().getProperty("java.home") + File.separator + "bin" + File.separator + "java.exe";
			} else {
			    jvm_location = System.getProperties().getProperty("java.home") + File.separator + "bin" + File.separator + "java";
			}
			ProcessBuilder pb = new ProcessBuilder(jvm_location, "-jar", LOCAL_APPLICATION);
			pb.directory(new File(parentpath));
			Process p = pb.start();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Woops... Something went wrong! Please delete the following folder: " + parentpath + " and try again! ");
		}
		System.exit(2);
	}
}