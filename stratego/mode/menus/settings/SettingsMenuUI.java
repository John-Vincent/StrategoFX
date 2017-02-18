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
  }


  @Override
  public void startWorker(Networker online){
    this.worker = new SettingsMenuWorker(online, this.task);
    this.worker.run();
  }



}
