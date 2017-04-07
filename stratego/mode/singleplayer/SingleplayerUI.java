package stratego.mode.singleplayer;

import stratego.mode.Mode;
import stratego.mode.menus.main.MainMenuUI;
import stratego.mode.menus.signup.SignupMenuWorker;
import stratego.components.gameboard.GameScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import stratego.network.Networker;
import stratego.application.Background;

/**
 * Single Player UI Displays the gameboard for the user.
 */
public class SingleplayerUI extends Mode {

	/**
	 * Constructor creates the gameboard.
	 */
	public SingleplayerUI() {
		super(new GameScene());
		Button back = new Button("Exit");
		back.setLayoutX(8);
		back.setLayoutY(8);
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// System.out.println("Test");
				setNextMode(new MainMenuUI());
				addTask("back");
				GameScene.kX = 0;
				GameScene.kY = 0;
			}
		});

		((Pane) this.getRoot()).getChildren().add(back);

		this.setWorker(new SingleplayerWorker());
		this.getRoot().requestFocus();
		this.setMinSize(375, 400 + GameScene.startY);
		this.setPrefWidth(750);
		this.setPrefHeight(800 + GameScene.startY);
		this.setResizable(true);
	}

}
