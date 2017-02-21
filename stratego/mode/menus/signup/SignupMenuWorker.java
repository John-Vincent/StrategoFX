package stratego.mode.menus.signup;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;

public class SignupMenuWorker extends ModeWorker {

  public SignupMenuWorker(ConcurrentLinkedQueue<Runnable> q){
    super(q);
  }

  @Override
  public Runnable getRequest(String name){
    switch(name){
      case "submit":
        return new SubmitRequest();
      default:
        return null;
    }
  }

    private class SubmitRequest implements Runnable{
        @Override
        public void run(){
            //this terminates the execution of this worker advancing the program to the next UI
            setRunning(false);
        }
    }




}
