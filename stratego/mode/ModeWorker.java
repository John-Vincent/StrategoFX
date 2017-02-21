package stratego.mode;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.DatagramPacket;
import stratego.application.Background;

public abstract class ModeWorker implements Runnable{

  protected Networker net;

  protected boolean running;

  private ConcurrentLinkedQueue<Runnable> task;


  public ModeWorker(ConcurrentLinkedQueue<Runnable> q){
    this.net = Background.getNetworker();
    this.task = q;
  }

  abstract public Runnable getRequest(String name);
  
  public Runnable getRequest(String name, Object...arg){
	  return null;
  }


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

      while(this.handlePacket(this.net.getPacket())){     }

    }

  }

  protected Runnable getTask(){
    return this.task.poll();
  }

  protected void setRunning(boolean b){
    this.running = b;
  }


  private boolean handlePacket(DatagramPacket p){
    if(p == null)
      return false;
    byte[] data = p.getData();
    if(data[0] == 0x00){
      System.out.println("ping received from " + p.getSocketAddress());
    } else{
      System.out.println("unknown packet received from " + p.getSocketAddress());
    }
    return true;
  }

  public PingRequest getPingRequest(){
    return new PingRequest();
  }


  public class PingRequest implements Runnable{

     @Override
     public void run(){
       net.ping();
     }

  }

}
