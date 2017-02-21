package stratego.mode.menus.login;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;

public class LoginMenuWorker extends ModeWorker{

  public LoginMenuWorker(ConcurrentLinkedQueue<Runnable> q){
    super(q);
  }

  @Override
  public Runnable getRequest(String name){
    switch(name){
      case "signin":
        return new SignInRequest();
      case "signup":
    	  return new SignUpRequest();
      default:
        return null;
    }
  }

  private class SignInRequest implements Runnable{
    @Override
    public void run(){
      //this terminates the execution of this worker advancing the program to the next UI
      setRunning(false);
    }
  }

    private class SignUpRequest implements Runnable{
        @Override
        public void run(){
            setRunning(false);
        }
    }
}
