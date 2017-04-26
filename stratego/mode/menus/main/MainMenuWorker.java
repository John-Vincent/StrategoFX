package stratego.mode.menus.main;

import stratego.network.Networker;
import stratego.network.Packet;
import stratego.mode.ModeWorker;

import java.io.File;
import java.util.Scanner;

import stratego.components.friendslist.FriendModel;

/**
 * This is the class the helps the MainMenuUI and Network communicate, it
 * controls the whole app while its active.
 */
public class MainMenuWorker extends ModeWorker {
	/**
	 * An instance of the FriendModel object for the logged in user.
	 */
	private FriendModel friendModel;

	/**
	 * Sets the FriendModel object and the tasklist that is used to get tasks
	 * from MainMenuUI.
	 *
	 * @param q
	 *            The queue that MainMenuUI uses to pass request to the
	 *            MainMenuWorker.
	 * @param fm
	 *            This users FriendModel provided by MainMenuUI.
	 * @author Bradley Rhein bdrhein@iastate.edu
	 */
	public MainMenuWorker(FriendModel fm) {
		super();
		friendModel = fm;
		super.setTodo(new Runnable[] { new FriendUpdater() });
		queueTask(new StartMusic());
	}

	@Override
	public boolean addTask(String name) {
		switch (name) {
		case "ping":
			queueTask(getPingRequest());
			return true;
		case "logout":
			queueTask(new LogoutOption());
			return true;
		case "singleplayer":
			queueTask(new MenuOptions());
			return true;
		case "settings":
			queueTask(new MenuOptions());
			return true;
		case "multiplayer":
			queueTask(new MenuOptions());
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean addTask(String name, Object... args) {
		switch (name) {
		case "addfriend":
			queueTask(new FriendRequest((String) args[0]));
			return true;
		default:
			return false;
		}

	}

	@Override
	protected boolean handlePacket(Packet p) {
		String temp;
		if (p == null)
			return false;
		byte[] data = p.getData();
		byte type = p.getType();
		switch (type) {
		case Networker.PING:
			System.out.println("ping from: " + p.getSocketAddress());
			break;
		case Networker.FRIENDQ:
			temp = new String(data, 0, data.length);
			String[] friends = temp.split(";");
			friendModel.clearFriends();
			for (int i = 0; i < friends.length; i++) {
				String[] friendData = friends[i].split(":");
				if (friendData.length == 2) {
					friendModel.addFriend(friendData[0], friendData[1]);
				}

			}
			break;
		case Networker.FRIENDR:
			if (data[0] == 0x00)
				break;
			temp = new String(data, 0, data.length);
			friendModel.addFriend(temp, "pending");
			break;
		default:
			System.out.println("unknown packet from: " + p.getSocketAddress());
		}
		return true;
	}

	private class LogoutOption implements Runnable {

		@Override
		public void run() {
			net.logout();
			setRunning(false);
		}
	}

	private class FriendRequest implements Runnable {
		String friendName;

		/**
		 * Constructor. Creates a friend request for the user with the provided
		 * name, if the user exists.
		 *
		 * @param name
		 *            The name of the requested user.
		 * @author Bradley Rhein bdrhein@iastate.edu
		 */
		public FriendRequest(String name) {
			friendName = name;
		}

		@Override
		public void run() {
			net.sendFriendRequest(friendName);
		}
	}

	private class FriendUpdater implements Runnable {
		private long time = 0;

		@Override
		public void run() {
			if (time + 15000 < System.currentTimeMillis()) {
				this.time = System.currentTimeMillis();
				net.requestFriendsList();
			}
		}
	}

	private class MenuOptions implements Runnable {

		@Override
		public void run() {
			setRunning(false);
		}

	}

	private class StartMusic implements Runnable{

		@Override
		public void run() {
			stratego.components.MusicPlayer.changeMusic(stratego.components.MusicPlayer.getSettingMusicName(), stratego.components.MusicPlayer.getSettingMusicVolume());
		}

	}
}
