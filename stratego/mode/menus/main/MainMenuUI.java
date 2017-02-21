package stratego.mode.menus.main;

import stratego.application.StrategoFX;
import stratego.components.FriendModel;
import stratego.components.FriendsList;
import stratego.components.Sizes;
import stratego.mode.Mode;
import stratego.mode.ModeWorker;
import stratego.mode.menus.settings.SettingsMenuUI;
import javafx.scene.*;
import stratego.network.Networker;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.geometry.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MainMenuUI extends Mode {
	BorderPane pane;
	public final static double buttonWidth = 200;

	public MainMenuUI() {
		super(new BorderPane());
		FriendModel friendModel = new FriendModel();
		this.setWorker(new MainMenuWorker(this.getTaskList(), friendModel));

		this.pane = (BorderPane) this.getRoot();
		this.pane.setPadding(new Insets(0, 30, 20, 30));
		this.setMinSize(500, 400);

		Button ai = new Button("Vs. AI");
		ai.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// TODO
			}
		});
		Button pl = new Button("Vs. Player");
		pl.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// TODO
			}
		});
		Button st = new Button("Settings");
		st.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
			}
		});
		Button lo = new Button("Log Out");
		lo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				getTaskList().add(getWorker().getPingRequest());
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

		Text fl = new Text("Friends List");
		fl.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		FriendsList friendsList = new FriendsList(10);
		friendsList.setPadding(new Insets(10, 15, 10, 10));
		friendModel.addObserver(friendsList);

		Button af = new Button("Add a Friend");

		VBox friends = new VBox(5, fl, friendsList, af);
		friends.setAlignment(Pos.CENTER);
		pane.setRight(friends);
		VBox.setVgrow(fl, Priority.ALWAYS);
		VBox.setVgrow(af, Priority.ALWAYS);
		af.setMaxSize(Double.MAX_VALUE, 50);
		friendModel.addFriend("Bill", "Online");
		friendModel.addFriend("Kyle", "swaq");
		friendModel.addFriend("Joe", "AWOL");
		friendModel.addFriend("Brad", "Offline");
		friendsList.update(friendModel, null);
		
		af.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				addFriend(friends, friendsList, friendModel);

			}
		});
	}

	public void addFriend(VBox friends, FriendsList friendsList, FriendModel friendModel) {
		TextField friendName = new TextField();
		HBox userName = new HBox(5, new Text("Friend's Username:"), friendName);
		Button addFriend = new Button("Add");
		VBox popup = new VBox(5, userName, addFriend);
		popup.setAlignment(Pos.CENTER);
		pane.setRight(popup);
		VBox.setVgrow(addFriend, Priority.ALWAYS);
		addFriend.setMaxSize(Double.MAX_VALUE, 50);

		addFriend.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				getTaskList().add(getWorker().getRequest("addfriend", friendName.getText()));
				friendModel.addFriend(friendName.getText(), "Request Pending...");
				friendsList.update(friendModel, null);
				pane.setRight(friends);
			}
		});
	}

}
