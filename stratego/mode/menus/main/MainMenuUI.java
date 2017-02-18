package stratego.mode.menus.main;

import stratego.mode.Mode;
import stratego.mode.ModeWorker;
import javafx.scene.*;
import stratego.network.Networker;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MainMenuUI extends Mode{

  BorderPane pane;

  public MainMenuUI() {
  		super(new BorderPane());
      this.worker = new MainMenuWorker(this.task);

  		this.pane = (BorderPane) this.getRoot();

  		Button ai = new Button("Vs. AI");
  		Button pl = new Button("Vs. Player");
  		Button st = new Button("Settings");
  		Button lo = new Button("Log Out");
      lo.setOnAction(new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent e){
          task.add(worker.getPingRequest());
        }
      });
      
  		VBox buttons = new VBox(10, ai, pl, st, lo);
  		buttons.setAlignment(Pos.CENTER);
  		pane.setLeft(buttons);

  		Text title = new Text("Stratego - 309 Edition");
  		title.setFont(new Font(30));
  		pane.setTop(title);
  		BorderPane.setAlignment(title, Pos.TOP_CENTER);

  		Text fl = new Text("Friends List");
  		Text af = new Text("Add a Friend");
  		VBox friends = new VBox(5, fl, af);
  		friends.setAlignment(Pos.CENTER);
  		pane.setRight(friends);

  	}

  @Override
  public void startWorker(){
    this.worker.run();
  }


}
