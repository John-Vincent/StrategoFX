package stratego.mode.menus.main;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;
import stratego.components.FriendModel;

public class MainMenuWorker extends ModeWorker {

	public FriendModel friendModel;

	public MainMenuWorker(ConcurrentLinkedQueue<Runnable> q, FriendModel fm) {
		super(q);
		friendModel = fm;
	}

	@Override
	public Runnable getRequest(String name) {
		switch (name) {
		case "logout":
			return new MenuOptions();
		default:
			return null;
		}
	}

	@Override
	public Runnable getRequest(String name, Object... args) {
		switch (name) {
		case "addfriend":
			return new FriendRequest((String) args[0]);
		default:
			return null;
		}

	}

	
	private class MenuOptions implements Runnable{

		@Override
		public void run() {
			setRunning(false);
		}
		
	}
	
	private class FriendRequest implements Runnable {
		String friendName;

		public FriendRequest(String name) {
			friendName = name;
		}

		@Override
		public void run() {
			net.sendFriendRequest(friendName);
			// this terminates the execution of this worker advancing the
			// program to the next UI
			setRunning(false);
		}
	}

}
