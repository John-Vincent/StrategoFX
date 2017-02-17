package stratego.mode.multiplayer;

import stratego.mode.Mode;
import javafx.scene.*;
import stratego.network.Networker;
import stratego.application.Background;

public class MultiplayerUI extends Mode{

  public MultiplayerUI(Parent p){
    super(p);
  }

  public MultiplayerUI(Parent p, double d1, double d2){
    super(p, d1, d2);
  }

  @Override
  public void startWorker(Networker online, Background back){
    this.worker = new MultiplayerWorker(online, back);
    this.worker.run();
  }



}
