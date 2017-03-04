package stratego.mode.singleplayer;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;

public class SingleplayerWorker extends ModeWorker{

  public SingleplayerWorker(ConcurrentLinkedQueue<Runnable> q){
    super(q);
  }

  @Override
  public Runnable getRequest(String name){
    switch(name){
      case "back":
    	  return new MenuOptions();
      default: 
        return null;
    }
  }
  
  private class MenuOptions implements Runnable{

		@Override
		public void run() {
			setRunning(false);
		}

	}

}
