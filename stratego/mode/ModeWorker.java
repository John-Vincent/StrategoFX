package stratego.mode;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import stratego.network.Packet;
import stratego.application.Background;

/**
 * This is the class the helps the UI and Network communicate, it controls the whole app while its Mode is active.
 */
public abstract class ModeWorker implements Runnable{
  /**
   * an Instance of the Networker object that can be used to send packets
   */
  protected Networker net;


  private boolean running;

  private Runnable[] todo = null;

  private ConcurrentLinkedQueue<Runnable> task;

  /**
   * sets the Networker object and the tasklist that is used to get task from the Mode
   * @param	q             the queue that the Mode uses to pass request to the Mode Worker
   * @return
   * @author  Collin Vincent  collinvincent96@gmail.com
   * @date   2017-03-08T20:29:46+000
   */
  public ModeWorker(){
    this.net = Background.getNetworker();
    this.task = new ConcurrentLinkedQueue<Runnable>();
  }

  /**
   * a Method that any subclass must override. This Method is called by the Mode on the JavaFX thread, it returns a runnable that will added to the Task queue by the Mode, and then processed by the Worker
   * on the Background thread as soon as possible.
   * @param	name          the name of the runnable to be returned
   * @return                         returns a Runnable object that preforms the task requested.
   * @author  Collin Vincent  collinvincent96@gmail.com
   * @date   2017-03-08T20:32:46+000
   */
  abstract public boolean addTask(String name);

  /**
   * a Method that any subclass must override. This returns a runnable that requires parameters to the Mode.
   * @param	name          the name of the runnable to be returned.
   * @param	arg          a list of arguments to be passed to the runnable.
   * @return                         returns a Runnable object that preforms the task requested.
   * @author  Collin Vincent  collinvincent96@gmail.com
   * @date   2017-03-08T20:32:46+000
   */
  public boolean addTask(String name, Object...arg){
	  return false;
  }

  /**
   * allows the Worker or any object with a reference to the worker to add a task to the workers to do list
   * @param  task the runnable object to run
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-21T23:19:18+000
   */
  protected void queueTask(Runnable task){
    this.task.add(task);
  }

  /**
   * this method is set up to run all request from the Mode as well and pass all packets from the network to the handlePacket Method, it also runs anything added to the todo list for the Worker
   * @author  Collin Vincent  collinvincent96@gmail.com
   * @date   2017-03-08T21:09:52+000
   */
  public void run(){
    this.running = true;
    while(!Thread.currentThread().isInterrupted() && this.running){
      try{
        Thread.sleep(100);
      } catch(Throwable e){
        this.running = false;
      }

      Runnable r = this.getTask();
      while(r != null){
        r.run();
        r = this.getTask();
      }

      if(todo != null){
        for(int i = 0; i < todo.length; i++){
          todo[i].run();
        }
      }

      Packet p = this.net.getPacket();
      while(p != null){
        System.out.println("Packet recieved from: " + p.getSocketAddress());
        this.handlePacket(p);
        p = this.net.getPacket();
      }

    }

  }

  /**
   * polls the next runnable from the task queue
   * @return returns the runnable from the top of the queue
   * @author  Collin Vincent  collinvincent96@gmail.com
   * @date   2017-03-08T21:11:27+000
   */
  protected Runnable getTask(){
    return this.task.poll();
  }

  /**
   * sets the value of the running field, once running is set to false the run method will exit after the it finishes checking the todo list as well as the packet list from the Network
   * @param	b the value to set running to.
   * @author  Collin Vincent  collinvincent96@gmail.com
   * @date   2017-03-08T21:16:19+000
   */
  protected void setRunning(boolean b){
    this.running = b;
  }

  /**
   * this sets the todo array for the ModeWorker, this is an array of runnables that will be executed after the request from the UI are processed and before the Network is checked for packets,
   * this list allows subclasses to add additional functionalitly to the run method without having to override it.
   * @param	todo array of runnables
   * @author  Collin Vincent  collinvincent96@gmail.com
   * @date   2017-03-08T21:17:50+000
   */
  protected void setTodo(Runnable[] todo){
    this.todo = todo;
  }

  protected void addTodo(Runnable todo){
    Runnable[] temp = new Runnable[this.todo.length+1];
    int i;
    for(i = 0; i < this.todo.length; i++){
      temp[i] = this.todo[i];
    }
    temp[temp.length-1] = todo;
    this.todo = temp;
  }

  /**
   * preforms an operation based on the contents of a packet recieved from the Network. This method is set up to handle just the basic packets to make the application function, but
   * this method should be overriden by any subclass that wants to interface with the Network
   * @param	p             the Datagram packet that was recieved by the Network
   * @return                         true if the packet was handled successfully false it it failed to be handled
   * @author  Collin Vincent  collinvincent96@gmail.com
   * @date   2017-03-08T21:23:56+000
   */
  protected boolean handlePacket(Packet p){
    byte[] data = p.getData();
    if(p.getType() == 0x00){
      System.out.println("ping received from " + p.getSocketAddress());
    } else{
      System.out.println("unknown packet received from " + p.getSocketAddress());
    }
    return true;
  }

  /**
   * returns a pingrequest which is a runnable that will ping the server, since pinging is the most simple network action that is always the same this is available for any subclass to use.
   * @return returns a runnable that will ping the server.
   * @author  Collin Vincent  collinvincent96@gmail.com
   * @date   2017-03-08T21:26:45+000
   */
  protected PingRequest getPingRequest(){
    return new PingRequest();
  }

  private class PingRequest implements Runnable{

     @Override
     public void run(){
       net.ping();
     }

  }

}
