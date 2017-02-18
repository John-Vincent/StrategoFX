package stratego.mode.menus.signup;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;

public class SignupMenuWorker extends ModeWorker {

  public SignupMenuWorker(ConcurrentLinkedQueue<Runnable> q){
    super(q);
  }


}
