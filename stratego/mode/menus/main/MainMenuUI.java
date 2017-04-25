package stratego.mode.menus.main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import stratego.components.friendslist.FriendModel;
import stratego.components.friendslist.FriendsList;
import stratego.components.gameboard.GameScene;
import stratego.mode.Mode;
import stratego.mode.menus.login.LoginMenuUI;
import stratego.mode.menus.settings.SettingsMenuUI;
import stratego.mode.multiplayer.MultiplayerUI;
import stratego.mode.singleplayer.SingleplayerUI;

/**
 * Main Menu UI The main menu contains multiple buttons leading to the game
 * screen (single and multiplayer), as well as a settings button and a logout
 * button. The main menu also holds a friends list VBox with information unique
 * to the user logged in.
 */
public class MainMenuUI extends Mode {
	BorderPane pane;
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
		
	
		GameScene.setCheat(SettingsMenuUI.getCheatSetting());
		
	}

}
