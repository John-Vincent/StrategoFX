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
       byte[] data = {(byte)0x00};
       DatagramPacket p = new DatagramPacket(data, data.length, net.server);
       net.sendPacket(p);
       System.out.println("sent packet to " + net.server);
     }

  }

}
