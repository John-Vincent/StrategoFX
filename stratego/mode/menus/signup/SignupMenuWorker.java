package stratego.mode.menus.signup;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;

public class SignupMenuWorker extends ModeWorker {

  public SignupMenuWorker(Networker n, ConcurrentLinkedQueue<Runnable> q){
    super(n,q);
  }

    @Override
    public void run() {

    }


}
