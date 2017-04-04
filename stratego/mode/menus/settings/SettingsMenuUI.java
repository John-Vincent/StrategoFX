package stratego.mode.menus.settings;

import stratego.mode.Mode;
import stratego.mode.menus.main.MainMenuUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.*;
import javafx.scene.control.Button;
import stratego.network.Networker;
import stratego.application.Background;
import stratego.components.gameboard.*;

/**
*Settings Menu UI
*The settings menu contains buttons that will change different settings for the gameplay experience.
*/
public class SettingsMenuUI extends Mode{

  Mode lastMode;

  /**
  *Constructor
  *Uses BorderPane to create the settings menu.
  */
  public SettingsMenuUI(){
    super(new BorderPane());
    this.setWorker(new SettingsMenuWorker());


    Button back = new Button("Back");
    back.setLayoutX(8);
    back.setLayoutY(8);
    back.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			// System.out.println("Test");
			setNextMode(new MainMenuUI());
			addTask("back");
		}
	});


    Button cheats = new Button("Cheats:" + GameScene.getCheat());

    cheats.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			// System.out.println("Test");
			setNextMode(new MainMenuUI());
			if (GameScene.getCheat() == false) {
				GameScene.setCheat(true);
			} else if (GameScene.getCheat() == true) {
				GameScene.setCheat(false);
			}
			cheats.setText("Cheats: " + GameScene.getCheat());
		}
	});

    VBox buttons = new VBox(50, cheats, back);
    buttons.setPadding(new Insets(100,100,100,100));

    buttons.setAlignment(Pos.TOP_CENTER);
    ((BorderPane)this.getRoot()).setCenter(buttons);

  }


}
