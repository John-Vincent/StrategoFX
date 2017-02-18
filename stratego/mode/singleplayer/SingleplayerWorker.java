package stratego.mode.singleplayer;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;

public class SingleplayerWorker extends ModeWorker{

  public SingleplayerWorker(ConcurrentLinkedQueue<Runnable> q){
    super(q);
  }



}
