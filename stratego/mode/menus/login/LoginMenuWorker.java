package stratego.mode.menus.login;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;

public class LoginMenuWorker extends ModeWorker{

  public LoginMenuWorker(ConcurrentLinkedQueue<Runnable> q){
    super(q);
  }

  protected Runnable getSignInRequest(){
    return new SignInRequest();
  }

  private class SignInRequest implements Runnable{
    @Override
    public void run(){
      //this terminates the execution of this worker advancing the program to the next UI
      running = false;
    }
  }

}
