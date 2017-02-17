package stratego.mode;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.DatagramPacket;
import stratego.application.Background;

public abstract class ModeWorker implements Runnable{

  private Networker net;
  protected Background back;

  protected boolean running;

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

}
