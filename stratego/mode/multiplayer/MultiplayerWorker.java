package stratego.mode.multiplayer;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;

public class MultiplayerWorker extends ModeWorker{

  public MultiplayerWorker(Networker n, ConcurrentLinkedQueue<Runnable> q){
    super(n,q);
  }



}
