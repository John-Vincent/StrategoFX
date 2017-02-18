package stratego.mode.menus.settings;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;

public class SettingsMenuWorker extends ModeWorker{

  public SettingsMenuWorker(Networker n){
    super.setNetworker(n);
  }



}
