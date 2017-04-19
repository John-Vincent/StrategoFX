package stratego.mode.menus.main;

import stratego.application.StrategoFX;
import stratego.mode.singleplayer.*;
import stratego.components.friendslist.FriendModel;
import stratego.components.friendslist.FriendsList;
import stratego.components.gameboard.GameScene;
import stratego.components.Sizes;
import stratego.mode.Mode;
import stratego.mode.ModeWorker;
import stratego.mode.menus.login.LoginMenuUI;
import stratego.mode.menus.settings.SettingsMenuUI;
import stratego.mode.multiplayer.MultiplayerUI;
import javafx.scene.*;
import stratego.network.Networker;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.geometry.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Main Menu UI The main menu contains multiple buttons leading to the game
 * screen (single and multiplayer), as well as a settings button and a logout
 * button. The main menu also holds a friends list VBox with information unique
 * to the user logged in.
 */
public class MainMenuUI extends Mode {
	BorderPane pane;
	Scanner scanner;
	public final static double buttonWidth = 200;

	/**
	 * Constructor. Uses the BorderPane format to create the main menu.
	 *
	 * @author Bradley Rhein bdrhein@iastate.edu
	 */
	public MainMenuUI() {
		super(new BorderPane());
		FriendModel friendModel = new FriendModel();
		this.setWorker(new MainMenuWorker(friendModel));

		this.pane = (BorderPane) this.getRoot();
		this.pane.setPadding(new Insets(0, 30, 20, 30));
		this.setMinSize(500, 400);

		Button ai = new Button("Vs. AI");
		ai.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				setNextMode(new SingleplayerUI());
				addTask("singleplayer");
			}
		});
		Button pl = new Button("Vs. Player");
		pl.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				setNextMode(new MultiplayerUI());
				addTask("multiplayer");
			}
		});
		Button st = new Button("Settings");
		st.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				setNextMode(new SettingsMenuUI());
				addTask("settings");
			}
		});
		Button lo = new Button("Log Out");
		lo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("logout requested");
				setNextMode(new LoginMenuUI());
				addTask("logout");
				stratego.application.StrategoFX.musicPlayer.stop();
			}
		});

		VBox buttons = new VBox(50, ai, pl, st, lo);
		buttons.setFillWidth(true);
		buttons.setAlignment(Pos.CENTER);
		buttons.setPadding(new Insets(60, 100, 0, 0));
		buttons.setMinSize(0, 0);
		pane.setCenter(buttons);
		VBox.setVgrow(ai, Priority.ALWAYS);
		VBox.setVgrow(pl, Priority.ALWAYS);
		VBox.setVgrow(st, Priority.ALWAYS);
		VBox.setVgrow(lo, Priority.ALWAYS);
		ai.setMinWidth(buttonWidth);
		pl.setMinWidth(buttonWidth);
		st.setMinWidth(buttonWidth);
		lo.setMinWidth(buttonWidth);
		ai.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		pl.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		st.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		lo.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		pane.setMinSize(400, 250);

		Text title = new Text("StrategoFX");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 50));
		title.autosize();
		pane.setTop(title);
		BorderPane.setAlignment(title, Pos.TOP_CENTER);

		FriendsList friendsList = new FriendsList(5, friendModel, this.getWorker());
		pane.setRight(friendsList);
		
		if (!(stratego.application.StrategoFX.musicPlayer.getStatus().equals(Status.PLAYING))) {
			try {
				scanner = new Scanner(new File("SFXsettings.brad"));
				int cheat = scanner.nextInt();
				if(cheat == 1)
					GameScene.setCheat(true);	//Defaults to false
				int music = scanner.nextInt();
				stratego.application.StrategoFX.musicPlayer = new MediaPlayer(
						new Media(new File(getMusicName(music)).toURI().toString()));
				stratego.application.StrategoFX.musicPlayer.setVolume(scanner.nextDouble()/100);
				stratego.application.StrategoFX.musicPlayer.play();
				scanner.close();
			} catch (FileNotFoundException e) {
				// Do nothing. Sound is muted if no settings are present.
			}
		}
	}

	public String getMusicName(int musicID) {
		if (musicID == 0)
			return "HSH.mp3";
		else if (musicID == 1)
			return "csgo.mp3";
		else if (musicID == 2)
			return "MUTE";
		return "MUTE";
	}
}
