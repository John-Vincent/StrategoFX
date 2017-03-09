package stratego.mode.menus.signup;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import java.util.Arrays;
import stratego.mode.ModeWorker;

/**
*	This is the class the helps the SignupMenuUI and Network communicate, it controls the whole app while its active.
*
*/
public class SignupMenuWorker extends ModeWorker {

	/**
	*	Calls it's superconstructor {@link stratego.mode.ModeWorker#ModeWorker(ConcurrentLinkedQueue<Runnable> q) ModeWorker}.
	* @author 	Bradley Rhein  bdrhein@iastate.edu
	*/
	public SignupMenuWorker(ConcurrentLinkedQueue<Runnable> q) {
		super(q);
	}

	@Override
	public Runnable getRequest(String name) {
		switch (name) {
		case "login":
			return new SubmitRequest();
		default:
			return null;
		}
	}

	@Override
	public Runnable getRequest(String name, Object... args) {
		switch (name) {
		case "signup":
			return new SubmitRequest((String) args[0], (String) args[1]);
		default:
			return null;
		}
	}

	@Override
  protected boolean handlePacket(DatagramPacket p){
    if(p == null)
      return false;
    byte[] data = p.getData();
    byte type = data[0];
    data = Arrays.copyOfRange(data, 1, data.length);
    switch(type){
      case Networker.PING:
        System.out.println("ping from: " + p.getSocketAddress());
        break;
      case Networker.SIGNUP:
				System.out.println(data[0]);
        if(data[0]!=0){
          this.setRunning(false);
        }
        break;
      default:
        System.out.println("unknown packet from: " + p.getSocketAddress());
    }
    return true;
  }

	private class SubmitRequest implements Runnable {
		String name;
		String password;

		/**
		*	Constructor.
		*	Sets the user name and password for the new user request.
		*	@param	name	The requested username for the new user.
		*	@param	password	The requested password for the new user.
		* @author  Bradley Rhein  bdrhein@iastate.edu
		*/
		public SubmitRequest(String name, String password) {
			this.name = name;
			this.password = password;
		}

		/**
		*	No-argument constructor.
		*	Creates an empty submit request when the user attempts to return to the login screen.
		* @author  Bradley Rhein  bdrhein@iastate.edu
		*/
		public SubmitRequest() {

		}

		@Override
		public void run() {
			if(name == null || password == null){
				setRunning(false);
			}else
				net.signup(name, password);
			// this terminates the execution of this worker advancing the
			// program to the next UI
		}
	}

}
