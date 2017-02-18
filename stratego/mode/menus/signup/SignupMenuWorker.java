package stratego.mode.menus.signup;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;

public class SignupMenuWorker extends ModeWorker {

  public SignupMenuWorker(ConcurrentLinkedQueue<Runnable> q){
    super(q);
  }

<<<<<<< HEAD
=======
    protected Runnable getSubmitRequest(){
        return new SubmitRequest();
    }

    private class SubmitRequest implements Runnable{
        @Override
        public void run(){
            //this terminates the execution of this worker advancing the program to the next UI
            running = false;
        }
    }

>>>>>>> f56cbeedcae74180786ee936ff8897cfad741c67


}
