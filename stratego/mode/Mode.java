package stratego.mode;

import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.paint.Paint;
import stratego.network.Networker;
import stratego.components.Sizes;
import java.util.concurrent.ConcurrentLinkedQueue;



public abstract class Mode extends Scene{

  private ModeWorker worker;
  private ConcurrentLinkedQueue<Runnable> task;
  private Mode next = null;
  private double prefWidth = Sizes.screenSize.getWidth()*Sizes.stageSize;
  private double prefHeight = Sizes.screenSize.getHeight()*Sizes.stageSize;
  private double[] minSize = new double[2];
  private boolean resizable = true;

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

  protected void addTask(String task){
    this.task.add(worker.getRequest(task));
  }

  public void addTask(String name, Object... args){
    this.task.add(worker.getRequest(name, args));
  }

  public void startWorker(){
      this.worker.run();
  }

  protected void setNextMode(Mode next){
    this.next = next;
  }

  public double[] getMinSize(){
    return this.minSize;
  }

  protected void setMinSize(double a, double b){
    this.minSize[0] = a;
    this.minSize[1] = b;
  }

  protected void setPrefWidth(double w){
    this.prefWidth = w;
  }

  protected void setPrefHeight(double h){
    this.prefHeight = h;
  }

  public double getPrefWidth(){
    return this.prefWidth;
  }

  public double getPrefHeight(){
    return this.prefHeight;
  }


  protected void setResizable(boolean r){
    this.resizable = r;
  }

  public boolean resizable(){
    return this.resizable;
  }


  public Mode nextMode(){
    return next;
  }
}
