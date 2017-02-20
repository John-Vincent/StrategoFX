package stratego.mode.menus.settings;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;

public class SettingsMenuWorker extends ModeWorker{

  public SettingsMenuWorker(ConcurrentLinkedQueue<Runnable> q){
    super(q);
  }

  @Override
  public Runnable getRequest(String name){
    switch(name){
      default:
        return null;
    }
  }

}
