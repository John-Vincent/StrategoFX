package stratego.mode.multiplayer;

import stratego.mode.Mode;
import javafx.scene.*;
import stratego.network.Networker;

public class MultiplayerUI extends Mode{

  public MultiplayerUI(Parent p){
    super(p);
  }

  public MultiplayerUI(Parent p, double d1, double d2){
    super(p, d1, d2);
  }

  @Override
  public void startWorker(Networker online){
    MultiplayerWorker w = new MultiplayerWorker();
    w.setQueues(online);
    this.worker = new Thread(w);
    this.worker.start();
  }

  @Override
  public void terminate(){
    this.worker.interrupt();
  }

}
