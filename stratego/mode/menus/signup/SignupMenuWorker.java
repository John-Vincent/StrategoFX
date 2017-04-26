package stratego.mode.menus.signup;

import stratego.mode.ModeWorker;
import stratego.network.Networker;
import stratego.network.Packet;

/**
*	This is the class the helps the SignupMenuUI and Network communicate, it controls the whole app while its active.
*
*/
public class SignupMenuWorker extends ModeWorker {

	/**
	*	Calls it's superconstructor {@link stratego.mode.ModeWorker#ModeWorker(ConcurrentLinkedQueue<Runnable> q) ModeWorker}.
	* @author 	Bradley Rhein  bdrhein@iastate.edu
	*/
	public SignupMenuWorker() {
		super();
	}

	@Override
	public boolean addTask(String name) {
		switch (name) {
		case "login":
			queueTask(new SubmitRequest());
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean addTask(String name, Object... args) {
		switch (name) {
		case "signup":
			queueTask(new SubmitRequest((String) args[0], (String) args[1]));
			return true;
		default:
			return false;
		}
	}

	@Override
  protected boolean handlePacket(Packet p){
    if(p == null)
      return false;
    byte[] data = p.getData();
    byte type = p.getType();
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
