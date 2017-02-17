package stratego.mode.menus.main;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;
import stratego.application.Background;

public class MainMenuWorker extends ModeWorker {


  public MainMenuWorker(Networker n, Background b){
    super.setNetworker(n);
    this.back  = b;
  }

  @Override
  public void run(){
    try{
      Thread.sleep(1000);
    } catch(Throwable e){

    }

    this.back.closeNetwork();

    while(!Thread.currentThread().isInterrupted() && this.running){
      try{
        Thread.sleep(100);
      } catch(Throwable e){
        this.running = false;
      }
    }
  }

}
