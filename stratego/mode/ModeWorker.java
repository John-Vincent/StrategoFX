package stratego.mode;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.DatagramPacket;

public abstract class ModeWorker implements Runnable{

  protected Networker net;

  protected boolean running;

  private ConcurrentLinkedQueue<Runnable> task;

  public void run(){
    this.running = true;
    while(!Thread.currentThread().isInterrupted() && this.running){
      try{
        Thread.sleep(100);
      } catch(InterruptedException e){
        running = false;
      }
    }
  }

  public void setNetworker(Networker online){
    this.net = online;
  }

  public void setQueue(ConcurrentLinkedQueue<Runnable> q){
    this.task = q;
  }

  protected Runnable getTask(){
    return this.task.poll();
  }

  protected boolean sendPacket(byte[] data, String dest){
    DatagramPacket p;
    switch(dest){
      case "server":
        p = new DatagramPacket(data, data.length, net.server);
        break;
      case "host":
        if(net.host == null)
          return false;
        p = new DatagramPacket(data, data.length, net.host);
        break;
      default:
        return false;
    }
    return net.sendPacket(p);
  }

  public PingRequest getPingRequest(){
    return new PingRequest();
  }

  public class PingRequest implements Runnable{

     @Override
     public void run(){
       byte[] data = {(byte)0x00};
       DatagramPacket p = new DatagramPacket(data, data.length, net.server);
       net.sendPacket(p);
       System.out.println("sent packet");
     }

  }

}
