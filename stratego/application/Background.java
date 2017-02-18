package stratego.application;

import stratego.network.Networker;
import stratego.mode.*;
import javafx.application.Platform;
import stratego.mode.menus.main.MainMenuUI;
import java.net.DatagramSocket;
import java.io.IOException;

//this will be used to run the different task
public class Background implements Runnable{
  private StrategoFX app;
  private static Networker net;
  private Mode m;
  private static DatagramSocket socket;

  public Background(StrategoFX app){
    this.app = app;
  }

  public void run(){
    try{
      this.socket = new DatagramSocket(8092);
    } catch(IOException e){
      //todo enter in offline mode
      e.printStackTrace();
    }
    net = new Networker(this.socket);
    Thread t = new Thread(net);
    t.start();
    this.m = new MainMenuUI();

    while(!Thread.currentThread().isInterrupted() && m != null){
      Platform.runLater(new Runnable(){
        @Override
        public void run(){
          app.setMode(m);
        }

      });
      m.startWorker();
      m = m.nextMode();
    }

    t.interrupt();
    this.socket.close();
  }

  public void setNextMode(Mode m){
    this.m = m;
  }

  public static Networker getNetworker(){
    return net;
  }
}
