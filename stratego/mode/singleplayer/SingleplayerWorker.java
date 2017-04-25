package stratego.mode.singleplayer;

import stratego.components.friendslist.FriendModel;
import stratego.mode.ModeWorker;
import stratego.network.Networker;
import stratego.network.Packet;

/**
 * The class that lets the SinglePlayerUI communicate with the network.
 */
public class SingleplayerWorker extends ModeWorker {

	private FriendModel friendModel;

	/**
	 * Constructor sets the tasklist that communicates tasks between the UI and
	 * the worker.
	 * 
	 * @param q
	 *            The queue that SinglePlayerUI uses to pass request to the
	 *            SinglePlayerWorker.
	 */
	public SingleplayerWorker(FriendModel friendModel) {
		super();
		this.friendModel = friendModel;
		super.setTodo(new Runnable[] { new FriendUpdater() });
		queueTask(new StartMusic());
	}

	@Override
	public boolean addTask(String name) {
		switch (name) {
		case "back":
			queueTask(new MenuOptions());
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
					if (friendData[1].equals("0"))
						friendData[1] = "offline";
					else
						friendData[1] = "online";
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

	private class MenuOptions implements Runnable {

		@Override
		public void run() {
			setRunning(false);
		}

	}

	private class StartMusic implements Runnable {

		@Override
		public void run() {
			stratego.components.MusicPlayer.changeMusic(stratego.components.MusicPlayer.getGameMusic(),
					stratego.components.MusicPlayer.getCurrentVolume());
		}
	}

	private class FriendUpdater implements Runnable {
		private long time = 0;

		@Override
		public void run() {
			if (time + 300000 < System.currentTimeMillis()) {
				this.time = System.currentTimeMillis();
				net.requestFriendsList();
			}
		}
	}
}
