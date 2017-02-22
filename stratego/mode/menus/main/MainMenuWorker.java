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
		default:
			return null;
		}
	}

}
