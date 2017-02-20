package stratego.mode.multiplayer;

import stratego.mode.Mode;
import javafx.scene.*;
import stratego.network.Networker;
import stratego.application.Background;

public class MultiplayerUI extends Mode{

  public MultiplayerUI(Parent p){
    super(p);
    this.setWorker(new MultiplayerWorker(this.getTaskList()));

  }




}
