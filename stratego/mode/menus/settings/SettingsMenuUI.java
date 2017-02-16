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

  public SettingsMenuUI(Parent p, double d1, double d2, Mode m){
    super(p, d1, d2);
    this.lastMode = m;
  }

  @Override
  public void startWorker(Networker online, Background back){
    this.worker = new SettingsMenuWorker(online, back);
    this.worker.run();
  }



}
