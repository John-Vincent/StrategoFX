package stratego.mode.singleplayer;


import stratego.mode.Mode;
import javafx.scene.*;
import stratego.network.Networker;


public class SingleplayerUI extends Mode{

  public SingleplayerUI(Parent p){
    super(p);
  }

  public SingleplayerUI(Parent p, double d1, double d2){
    super(p, d1, d2);
  }


  @Override
  public void startWorker(Networker online){
    SingleplayerWorker w = new SingleplayerWorker();
    w.setQueues(online);
    this.worker = new Thread(w);
    this.worker.start();
  }

  @Override
  public void terminate(){
    this.worker.interrupt();
  }

}
