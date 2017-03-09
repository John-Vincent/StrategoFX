package stratego.mode.multiplayer;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;

/*
*Class that helps the multiplayer screen and the network communicate.
*/
public class MultiplayerWorker extends ModeWorker{

	/**
	*Sets the tasklist that communicates tasks from the UI to the worker.
	*@param  ConcurrentLinkedQueue<Runnable>	q	The queue that MultiplayerUI uses to pass request to the MultiplayerWorker.
	*/
  public MultiplayerWorker(ConcurrentLinkedQueue<Runnable> q){
    super(q);
  }

  @Override
  public Runnable getRequest(String name){
    switch(name){
      default:
        return null;
    }
  }

}
