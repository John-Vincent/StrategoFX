package stratego.mode.menus.signup;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;

public class SignupMenuWorker extends ModeWorker {

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

	private class SubmitRequest implements Runnable {
		String name;
		String password;

		public SubmitRequest(String name, String password) {
			this.name = name;
			this.password = password;
		}

		public SubmitRequest() {

		}

		@Override
		public void run() {
			net.signup(name, password);
			// this terminates the execution of this worker advancing the
			// program to the next UI
			setRunning(false);
		}
	}

}
