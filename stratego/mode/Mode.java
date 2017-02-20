package stratego.mode;

import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.paint.Paint;
import stratego.network.Networker;
import java.util.concurrent.ConcurrentLinkedQueue;



public abstract class Mode extends Scene{

  private ModeWorker worker;
  private ConcurrentLinkedQueue<Runnable> task;
  private Mode next = null;

  public Mode(Parent p){
    super(p);
    this.task = new ConcurrentLinkedQueue<Runnable>();
  }

  protected void setWorker(ModeWorker w){
    this.worker = w;
  }

  protected ModeWorker getWorker(){
    return this.worker;
  }

  protected ConcurrentLinkedQueue<Runnable> getTaskList(){
    return this.task;
  }

  public void startWorker(){
      this.worker.run();
  }

  protected void setNextMode(Mode next){
    this.next = next;
  }


  public Mode nextMode(){
    return next;
  }
}
