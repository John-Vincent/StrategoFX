package stratego.mode.menus.settings;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import stratego.network.Packet;
import stratego.mode.ModeWorker;
/**
*Class that helps the Settings Menu and the network communicate
*/
public class SettingsMenuWorker extends ModeWorker{

	/**
	*Sets the tasklist that communicates tasks between the SettingsMenuUI and the worker.
	*@param	q	The queue that SettingsMenuUI uses to pass request to the SettingsMenuWorkerWorker.
	*
	*/
  public SettingsMenuWorker(){
    super();
  }

  @Override
  public boolean addTask(String name){
    switch(name){
      case "back":
    	  queueTask(new BackRequest());
        return true;
      default:
        return false;
    }
  }

  private class BackRequest implements Runnable{

		@Override
		public void run() {
			setRunning(false);
		}

	}

}
