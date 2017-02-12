package stratego.mode;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;

public interface ModeWorker extends Runnable{


  public void run();

  public void setQueues(Networker online);


}
