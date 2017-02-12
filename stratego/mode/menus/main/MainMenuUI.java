package stratego.mode.menus.main;

import stratego.mode.Mode;
import javafx.scene.*;
import stratego.network.Networker;


public class MainMenuUI extends Mode{

  public MainMenuUI(Parent p){
    super(p);
  }

  public MainMenuUI(Parent p, double d1, double d2){
    super(p, d1, d2);
  }

  @Override
  public void startWorker(Networker online){
    MainMenuWorker w = new MainMenuWorker();
    w.setQueues(online);
    this.worker = new Thread(w);
    this.worker.start();
  }

  @Override
  public void terminate(){
    this.worker.interrupt();
  }

}
