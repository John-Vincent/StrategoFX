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
		super.setTodo(new Runnable[]{new FriendUpdater()});
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
		case "addfriend":
			return new FriendRequest((String) args[0]);
		default:
			return null;
		}

	}

	private class FriendRequest implements Runnable {
		String friendName;
		public FriendRequest(String name){
			friendName = name;
		}

		@Override
		public void run() {
			net.sendFriendRequest(friendName);
		}
	}

	private class FriendUpdater implements Runnable {
		private long time=0;

		@Override
		public void run(){
			if(time + 300000 > System.curTimeMillis()){
				this.time = System.curTimeMillis();
				net.requestFriendsList();
			}
		}
	}

}
