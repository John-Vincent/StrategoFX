package stratego.mode.singleplayer;


import stratego.mode.Mode;
import javafx.scene.*;
import stratego.network.Networker;
import stratego.application.Background;


public class SingleplayerUI extends Mode{

  public SingleplayerUI(Parent p){
    super(p);
  }

  public SingleplayerUI(Parent p, double d1, double d2){
    super(p, d1, d2);
  }


  @Override
  public void startWorker(Networker online, Background back){
    this.worker = new SingleplayerWorker(online, back);
    this.worker.run();
  }



}
