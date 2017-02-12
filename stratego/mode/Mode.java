package stratego.mode;

import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.paint.Paint;
import stratego.network.Networker;
import stratego.network.Networker;


public abstract class Mode extends Scene{

  protected Thread worker;

  public Mode(Parent p){
    super(p);
  }

  public Mode(Parent p, double d1, double d2){
    super(p, d1, d2);
  }


  /**
   * this should start the worker thread
   * @param  Networker               online this is the object that communicates through the internet we only need it to get the concurrent queues to the worker thread
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-02-10T19:40:32+000
   */
  public abstract void startWorker(Networker online);

  /**
   * this should call interupt on the worker thread, the worker thread needs to check if it has been interupted every loop and if it has it should break the loop and terminate.
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-02-10T19:43:21+000
   */
  public abstract void terminate();

}
