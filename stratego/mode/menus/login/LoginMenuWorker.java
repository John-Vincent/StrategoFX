package stratego.mode.menus.login;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;

public class LoginMenuWorker extends ModeWorker{

  public LoginMenuWorker(Networker n, ConcurrentLinkedQueue<Runnable> q){
    super(n,q);
  }


  @Override
  public void run(){

  }


}
