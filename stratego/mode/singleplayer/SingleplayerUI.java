package stratego.mode.singleplayer;


import stratego.mode.Mode;
import javafx.scene.*;
import stratego.network.Networker;
import stratego.application.Background;


public class SingleplayerUI extends Mode{

  public SingleplayerUI(Parent p){
    super(p);
    this.worker = new SingleplayerWorker(this.task);
  }


  @Override
  public void startWorker(){
    this.worker.run();
  }



}
