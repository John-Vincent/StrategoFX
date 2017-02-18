package stratego.mode.menus.main;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;

public class MainMenuWorker extends ModeWorker {

  public MainMenuWorker(ConcurrentLinkedQueue<Runnable> q){
    super(q);
  }

  @Override
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

}
