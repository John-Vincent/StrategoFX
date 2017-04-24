package stratego.components;

import java.io.File;
import java.util.Scanner;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer {
	public static MediaPlayer musicPlayer;
	public static MediaPlayer effectPlayer;
	private static Scanner scanner;

	public static int getCheatSetting() {
		try {
			scanner = new Scanner(new File("SFXsettings.brad"));
			int cheat = scanner.nextInt();
			scanner.close();
			return cheat;
		} catch (Exception e) {
			return 0;
		}
	}

	public static String getMusicName() {
		try {
			scanner = new Scanner(new File("SFXsettings.brad"));
			scanner.nextInt();
			int musicID = scanner.nextInt();
			scanner.close();
			if (musicID == 0)
				return "HSH.mp3";
			else if (musicID == 1)
				return "csgo.mp3";
			else if (musicID == 2)
				return "MUTE.mp3";
			return "MUTE.mp3";
		} catch (Exception e) {
			return "MUTE.mp3";
		}
	}

	public static double getMusicVolume() {
		try {
			scanner = new Scanner(new File("SFXsettings.brad"));
			scanner.nextInt();
			scanner.nextInt();
			double volume = scanner.nextDouble();
			scanner.close();
			return volume;
		} catch (Exception e) {
			return 0;
		}
	}

	public static void setMusicVolume(double vol) {
		musicPlayer.setVolume(vol);
	}

	public static void changeMusic(String filename, double musicVol) {
		//do not start a song over if it's currently playing
		if (!getMusicName().equals(filename)) {
			musicPlayer.stop();
			musicPlayer = new MediaPlayer(new Media(new File(filename).toURI().toString()));
			musicPlayer.setVolume(musicVol);
			musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			musicPlayer.play();
		}
	}

}
