package stratego.mode.singleplayer;


import stratego.mode.Mode;
import stratego.mode.menus.main.MainMenuUI;
import stratego.mode.menus.signup.SignupMenuWorker;
import stratego.components.GameScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import stratego.network.Networker;
import stratego.application.Background;


public class SingleplayerUI extends Mode{

  public SingleplayerUI(){
    super(new GameScene());
    Button back = new Button("Back");
    back.setLayoutX(8);
    back.setLayoutY(8);
    back.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			// System.out.println("Test");
			setNextMode(new MainMenuUI());
			getTaskList().add(getWorker().getRequest("back"));
		}
	});
    
    ((Pane)this.getRoot()).getChildren().add(back);
    
    this.setWorker(new SingleplayerWorker(this.getTaskList()));
    this.getRoot().requestFocus();
    this.setMinSize(760, 800 + GameScene.startY);
  }




}
