package stratego.mode.singleplayer;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;
import stratego.application.Background;

public class SingleplayerWorker extends ModeWorker{


  public SingleplayerWorker(Networker n, Background b){
    super.setNetworker(n);
    this.back = b;
  }



}
