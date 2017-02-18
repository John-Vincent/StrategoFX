package stratego.mode;

import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.paint.Paint;
import stratego.network.Networker;
import java.util.concurrent.ConcurrentLinkedQueue;



public abstract class Mode extends Scene{

  protected ModeWorker worker;
  protected ConcurrentLinkedQueue<Runnable> task;
  protected Mode next = null;

  public Mode(Parent p){
    super(p);
    this.task = new ConcurrentLinkedQueue<Runnable>();
  }


  public abstract void startWorker(Networker online);


  public Mode nextMode(){
    return next;
  }
}
