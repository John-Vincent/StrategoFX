package stratego.mode.singleplayer;


import stratego.mode.Mode;
import javafx.scene.*;
import stratego.network.Networker;
import stratego.application.Background;


public class SingleplayerUI extends Mode{

  public SingleplayerUI(Parent p){
    super(p);
  }


  @Override
  public void startWorker(Networker online){
    this.worker = new SingleplayerWorker(online);
    this.worker.run();
  }



}
