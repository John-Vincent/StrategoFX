package stratego.mode.menus.login;

import stratego.network.Networker;
import stratego.network.Packet;
import stratego.mode.ModeWorker;

/**
*Class that helps the LoginMenuUI and network communicate and controls the whole app while active.
*/
public class LoginMenuWorker extends ModeWorker {

	/**
	*Sets the task list that gets tasks from LoginMenuUI
	* @param	q	A queue that passes requests from LoginMenuUI to LoginMenuWorker.
	* @author Ryan McCullough rmm@iastate.edu
	*/
	public LoginMenuWorker() {
		super();
		queueTask(new StopMusic());
	}


	@Override
	public boolean addTask(String name) {
		switch (name) {
		case "signup":
			queueTask(new SignUpRequest());
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean addTask(String name, Object... args) {
		switch (name) {
		case "signin":
			queueTask(new SignInRequest((String) args[0], (String) args[1]));
			return true;
		case "signup":
			queueTask(new SignUpRequest());
			return true;
		default:
			return false;
		}
	}

	private class SignInRequest implements Runnable {
		String username;
		String password;

		public SignInRequest(String name, String pass) {
			username = name;
			password = pass;
		}

		@Override
		public void run() {
			net.login(username, password);
			//setRunning(false);
			// this terminates the execution of this worker advancing the
			// program to the next UI
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
      case Networker.LOGIN:
        if(data[0]==1){
          this.setRunning(false);
        }
        break;
      default:
        System.out.println("unknown packet from: " + p.getSocketAddress());
    }
    return true;
  }

	private class SignUpRequest implements Runnable {
		@Override
		public void run() {
			setRunning(false);
		}
	}
	
	private class StopMusic implements Runnable{

		@Override
		public void run() {
			stratego.components.MusicPlayer.stopMusic();
		}
		
	}

}
