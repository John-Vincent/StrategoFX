package stratego.application;

import stratego.network.Networker;
import stratego.mode.*;
import javafx.application.Platform;
import stratego.mode.menus.main.MainMenuUI;
import stratego.mode.menus.login.LoginMenuUI;
import java.net.DatagramSocket;
import java.io.IOException;

/**
 * this class will run all of the ModeWorkers as well as pass the next Mode to be displayed to Stratego once the Current ModeWorker Terminates
 */
public class Background implements Runnable{
  private StrategoFX app;
  private static Networker net;
  private Mode m;
  private static DatagramSocket socket;

  /**
   * returns an instance of Background this will only be called from StrategoFX
   * @param  StrategoFX              app           This is the instance of Stratego that will be needed to set the next Mode
   * @return                         the instance of Background
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-03-08T22:00:31+000
   */
  public Background(StrategoFX app){
    this.app = app;
  }

  /**
   * this runs the loop that will set the next mode when the current one ends, To set the mode it makes Stratego display it, and the starts that modes Worker.
   * this also starts the Networker object/thread that will watch for packets coming in.
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-03-08T22:01:42+000
   */
  public void run(){
    try{
      socket = new DatagramSocket(8092);
    } catch(IOException e){
      //todo enter in offline mode
      e.printStackTrace();
    }
    net = new Networker(socket);
    Thread t = new Thread(net);
    t.start();
    this.m = new LoginMenuUI();

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
    socket.close();
  }

  /**
   * This is used by the ModeWorker to get the Networker that is handling the apps connection
   * @return [description]
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-03-08T22:05:05+000
   */
  public static Networker getNetworker(){
    return net;
  }
}
