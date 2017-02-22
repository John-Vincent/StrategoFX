package stratego.mode.menus.login;

import java.util.concurrent.ConcurrentLinkedQueue;
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
			setRunning(false);
		}
	}

	private class SignUpRequest implements Runnable {
		@Override
		public void run() {
			setRunning(false);
		}
	}

}
