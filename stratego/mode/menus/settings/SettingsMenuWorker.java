package stratego.mode.menus.settings;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;
import stratego.application.Background;

public class SettingsMenuWorker extends ModeWorker{

  public SettingsMenuWorker(Networker n, Background b){
    super.setNetworker(n);
    this.back = b;
  }



}
