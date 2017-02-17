package stratego.mode.multiplayer;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;
import stratego.application.Background;

public class MultiplayerWorker extends ModeWorker{


  public MultiplayerWorker(Networker n, Background b){
    super.setNetworker(n);
    this.back = b;
  }



}
