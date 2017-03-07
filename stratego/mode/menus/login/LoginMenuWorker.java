package stratego.mode.menus.login;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Arrays;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;

public class LoginMenuWorker extends ModeWorker {

	public LoginMenuWorker(ConcurrentLinkedQueue<Runnable> q) {
		super(q);
	}


	@Override
	public Runnable getRequest(String name) {
		switch (name) {
		case "signup":
			return new SignUpRequest();
		default:
			return null;
		}
	}

	@Override
	public Runnable getRequest(String name, Object... args) {
		switch (name) {
		case "signin":
			return new SignInRequest((String) args[0], (String) args[1]);
		case "signup":
			return new SignUpRequest();
		default:
			return null;
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
			// this terminates the execution of this worker advancing the
			// program to the next UI
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
      case Networker.LOGIN:
        if(data[0]!=0){
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

}