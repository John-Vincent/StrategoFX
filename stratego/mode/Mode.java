package stratego.mode;

import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.paint.Paint;
import stratego.network.Networker;
import stratego.application.Background;



public abstract class Mode extends Scene{

  protected Runnable worker;

  public Mode(Parent p){
    super(p);
  }

  public Mode(Parent p, double d1, double d2){
    super(p, d1, d2);
  }

  public abstract void startWorker(Networker online, Background back);


}
