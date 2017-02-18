package stratego.mode.menus.settings;

import stratego.mode.Mode;
import javafx.scene.*;
import stratego.network.Networker;
import stratego.application.Background;

public class SettingsMenuUI extends Mode{

  Mode lastMode;

  public SettingsMenuUI(Parent p, Mode m){
    super(p);
    this.lastMode = m;
    this.worker = new SettingsMenuWorker(this.task);
  }


  @Override
  public void startWorker(){
    this.worker.run();
  }



}
