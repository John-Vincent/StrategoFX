package stratego.components;

import java.io.File;
import java.util.Scanner;

import javafx.animation.Animation.Status;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer {
	public static MediaPlayer musicPlayer;
	public static MediaPlayer effectPlayer;
	public static final String HSH = "HSH.mp3";
	public static final String CSGO = "csgo.mp3";
	public static final String MUTE = "MUTE.mp3";
	public static final String FFVI = "ffvi-fanfare.mp3";
	public static final String CSWIN = "csgoWin.mp3";
	public static final String CSLOSS = "csgoLoss.mp3";
	public static final String POLOSS = "punchoutLoss.mp3";
	public static final String CONTRA = "contraJungle.mp3";
	public static final String LOL = "league.mp3";
	private static Scanner scanner;
	private static String currentSong = null;
	

	public static String getSettingMusicName() {
		try {
			scanner = new Scanner(new File("SFXsettings.brad"));
			scanner.nextInt();
			int musicID = scanner.nextInt();
			scanner.close();
			switch(musicID){
			case 0:
				return HSH;
			case 1:
				return CSGO;
			case 2:
				return MUTE;
			default:
				return MUTE;
			}
		} catch (Exception e) {
			return MUTE;
		}
	}

	public static double getSettingMusicVolume() {
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
		if (!filename.equals(getCurrentSong()) || !musicPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
			musicPlayer.stop();
			musicPlayer = new MediaPlayer(new Media(new File(filename).toURI().toString()));
			musicPlayer.setVolume(musicVol);
			musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			musicPlayer.play();
			currentSong = filename;
		}
	}

	public static void stopMusic(){
		musicPlayer.stop();
		currentSong = MUTE;
	}
	
	public static String getCurrentSong() {
		return currentSong;
	}
	
	public static int getCurrentSongID(){
		switch (currentSong){
		case HSH:
			return 0;
		case CSGO:
			return 1;
		case MUTE:
			return 2;
		default:
			return 2;
		}
	}
	
	public static double getCurrentVolume(){
		return musicPlayer.getVolume();
	}

	public static String getGameMusic() {
		switch(getSettingMusicName()){
		case HSH:
			return CONTRA;
		case CSGO:
			return LOL;
		case MUTE:
			return MUTE;
		default:
			return MUTE;
		}
	}

	public static String getLossMusic() {
		switch(getSettingMusicName()){
		case HSH:
			return POLOSS;
		case CSGO:
			return CSLOSS;
		case MUTE:
			return MUTE;
		default:
			return MUTE;
		}
	}

	public static String getWinMusic() {
		switch(getSettingMusicName()){
		case HSH:
			return FFVI;
		case CSGO:
			return CSWIN;
		case MUTE:
			return MUTE;
		default:
			return MUTE;
		}
	}

}
