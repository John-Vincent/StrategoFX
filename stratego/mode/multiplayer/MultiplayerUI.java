package stratego.mode.multiplayer;

import stratego.mode.Mode;
import javafx.scene.*;
import stratego.network.Networker;
import stratego.application.Background;

public class MultiplayerUI extends Mode{

  public MultiplayerUI(Parent p){
    super(p);
  }


  @Override
  public void startWorker(Networker online){
    this.worker = new MultiplayerWorker(online);
    this.worker.run();
  }



}
