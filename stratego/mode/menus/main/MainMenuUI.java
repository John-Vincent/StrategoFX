package stratego.mode.menus.main;

import stratego.mode.Mode;
import javafx.scene.*;
import stratego.network.Networker;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class MainMenuUI extends Mode{

  BorderPane pane;

  public MainMenuUI(){
    super(new BorderPane());
    this.pane = (BorderPane)this.getRoot();

    Button ai = new Button("Vs. AI");
    Button pl = new Button("Vs. Player");
    Button st = new Button("Settings");
    Button lo = new Button("Log Out");
    VBox buttons = new VBox(10, ai, pl, st, lo);
    pane.setLeft(buttons);

    Label title = new Label("Stratego - 309 Edition");
    pane.setTop(title);

    Label fl = new Label("Friends List");
    Label af = new Label("Add a Friend");
    VBox labels = new VBox(5, fl, af);
    pane.setRight(labels);
  }

  @Override
  public void startWorker(Networker online){
    MainMenuWorker w = new MainMenuWorker();
    w.setQueues(online);
    this.worker = new Thread(w);
    this.worker.start();
  }

  @Override
  public void terminate(){
    this.worker.interrupt();
  }

}
