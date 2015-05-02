package aars.me.championrandomizer;

import java.applet.AudioClip;

public class AudioPlayer {
	
	private static AudioClip audio;
	
	public static void play(AudioClip a) {
		stop();
		start(a);
	}
	
	public static void stop() {
		if(audio!=null)
			audio.stop();
	}
	
	public static void start(AudioClip a) {
		if(a==null) return;
		audio = a;
		audio.play();
	}

}
