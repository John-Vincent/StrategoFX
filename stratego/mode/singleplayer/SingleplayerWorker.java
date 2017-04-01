package stratego.mode.singleplayer;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import stratego.network.Packet;
import stratego.mode.ModeWorker;

/**
*The class that lets the SinglePlayerUI communicate with the network.
*/
public class SingleplayerWorker extends ModeWorker{

	/**
	*Constructor
	*sets the tasklist that communicates tasks between the UI and the worker.
	*@param	q	The queue that SinglePlayerUI uses to pass request to the SinglePlayerWorker.
	*/
  public SingleplayerWorker(){
    super();
  }

  @Override
  public boolean addTask(String name){
    switch(name){
      case "back":
    	  queueTask(new MenuOptions());
        return true;
      default:
        return false;
    }
  }

  private class MenuOptions implements Runnable{

		@Override
		public void run() {
			setRunning(false);
		}

	}

}
