package stratego.mode;

import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.paint.Paint;
import stratego.network.Networker;
import stratego.components.Sizes;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * this class is the default display class for the Stratego project
 */
public abstract class Mode extends Scene{

  private ModeWorker worker;
  private ConcurrentLinkedQueue<Runnable> task;
  private Mode next = null;
  private double prefWidth = Sizes.screenSize.getWidth()*Sizes.stageSize;
  private double prefHeight = Sizes.screenSize.getHeight()*Sizes.stageSize;
  private double[] minSize = new double[2];
  private boolean resizable = true;

  /**
   * constructs a scene with the root node p
   * @param  Parent                  p             some javafx object that extends parent
   * @return                         an instance of the mode class
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-03-08T19:13:03+000
   */
  public Mode(Parent p){
    super(p);
    this.task = new ConcurrentLinkedQueue<Runnable>();
  }

  /**
   * sets the worker object for this mode. the ModeWorker will be run automatically from the background thread.
   * @param  ModeWorker              w The ModeWorker object that will compute the logic for this mode
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-03-08T19:13:59+000
   */
  protected void setWorker(ModeWorker w){
    this.worker = w;
  }

  /**
   * returns the current worker for this Mode
   * @return null if the worker has not been set, or a ModeWorker instance.
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-03-08T19:16:48+000
   */
  protected ModeWorker getWorker(){
    return this.worker;
  }

  protected ConcurrentLinkedQueue<Runnable> getTaskList(){
    return this.task;
  }

  /**
   * request a task to be run by the ModeWorker, this task must be defined in the ModeWorker class
   * @param  String                  task the name of that task to be run
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-03-08T19:21:51+000
   */
  protected void addTask(String task){
    this.task.add(worker.getRequest(task));
  }

  /**
   * request a task to be run by the ModeWorker, this task must be defined in the ModeWorker class.
   * @param  String                  name the name of the task
   * @param  Object...               args any arguments that the task will need to run
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-03-08T19:22:41+000
   */
  protected void addTask(String name, Object... args){
    this.task.add(worker.getRequest(name, args));
  }

  /**
   * this will start the execution of the ModeWorker, most users will not need to call this method. It is called by the Background class and should only be used if you are rewriting Background
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-03-08T19:24:16+000
   */
  public void startWorker(){
      this.worker.run();
  }

  /**
   * this sets the next Mode to be displayed by the application after the execution of this mode terminates.
   * @param  Mode                    next an instance of the next mode that should be displayed.
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-03-08T19:25:43+000
   */
  protected void setNextMode(Mode next){
    this.next = next;
  }

  /**
   * returns the minimum size in an array
   * @return returns an array with the width in the first index and the height in the second index
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-03-08T19:32:41+000
   */
  public double[] getMinSize(){
    return this.minSize;

  }

  /**
   * this sets the minimum size that this mode should be displayed at.
   * @param  double                  width the width of the scene
   * @param  double                  height the height of the scene
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-03-08T19:32:41+000
   */
  protected void setMinSize(double width, double height){
    this.minSize[0] = width;
    this.minSize[1] = height;
  }

  /**
   * sets the Prefered width of the Mode, the Mode will be displayed with this width when it is shown.
   * @param  double                  w the width in pixels
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-03-08T19:34:03+000
   */
  protected void setPrefWidth(double w){
    this.prefWidth = w;
  }

  /**
   * sets the prefered hieght of this Mode, the Application will be set to this heigth when this Mode is Displayed
   * @param  double                  h the Height in pixels
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-03-08T19:39:09+000
   */
  protected void setPrefHeight(double h){
    this.prefHeight = h;
  }

  /**
   * returns the Prefered Width of this Mode
   * @return the Prefered Width in Pixels
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-03-08T20:14:01+000
   */
  public double getPrefWidth(){
    return this.prefWidth;
  }

  /**
   * returns the Prefered Height of this Mode
   * @return The Prefered Height in pixels
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-03-08T20:14:31+000
   */
  public double getPrefHeight(){
    return this.prefHeight;
  }

  /**
   * This sets the resizable parameter of this Mode, this will prevent the application from being resized while this Mode is Active.
   * @param  boolean                 r the resizable value
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-03-08T20:15:01+000
   */
  protected void setResizable(boolean r){
    this.resizable = r;
  }

  /**
   * returns the Resizable property of this object
   * @return the resizable attribute
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-03-08T20:17:06+000
   */
  public boolean resizable(){
    return this.resizable;
  }

  /**
   * the Mode that will be displayed after this Mode is Closed.
   * @return an instance of Mode.
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-03-08T20:17:33+000
   */
  public Mode nextMode(){
    return next;
  }
}
