package stratego.mode.multiplayer;

import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.application.Platform;

import java.nio.charset.StandardCharsets;

import stratego.network.Networker;
import stratego.network.Packet;
import stratego.components.friendslist.FriendModel;
import stratego.mode.ModeWorker;

/*
*Class that helps the multiplayer screen and the network communicate.
*/
public class MultiplayerWorker extends ModeWorker {

	private HostManager HManager;
	private FriendModel friendModel;
	private MultiplayerUI ui;
	private String serverName;
	private String serverPassword;

	/**
	 * Sets the tasklist that communicates tasks from the UI to the worker.
	 *
	 * @param friendModel
	 *
	 * @param q
	 *            The queue that MultiplayerUI uses to pass request to the
	 *            MultiplayerWorker.
	 */
	public MultiplayerWorker(FriendModel friendModel, MultiplayerUI ui) {
		super();
		this.friendModel = friendModel;
		this.ui = ui;
		super.setTodo(new Runnable[] { new FriendUpdater() });
		queueTask(new StartMusic());
	}

	@Override
	public boolean addTask(String name) {
		switch (name) {
		case "main":
			queueTask(new MenuOptions());
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean addTask(String name, Object... arg) {
		switch (name) {
		case "setServer":
			queueTask(new setServerOption((String) arg[0], (String) arg[1]));
			return true;
		case "connectServer":
			queueTask(new connectServerOption((String) arg[0], (String) arg[1]));
			return true;
		case "message":
			queueTask(new sendChatOption((String) arg[0]));
			return true;
		case "game":
			queueTask(new sendGameOption());
			return true;
		default:
			return false;
		}
	}

	@Override
	protected boolean handlePacket(Packet p) {
		String temp;
		byte[] data = p.getData();
		byte t = p.getType();
		switch (t) {
		case Networker.PING:
			System.out.println("Ping from: " + p.getSocketAddress());
			break;
		case Networker.OPENSERV:
			if (data.length == 1 && data[0] == 0x01) {
				System.out.println("Server opened");
				Platform.runLater(new MultiplayerUISwitcher());
				HManager = new HostManager();
				super.addTodo(HManager);
			} else {
				System.out.println("Server failed to open");
			}
			break;
		case Networker.CONSERV:
			if (data.length != 1) {
				// todo data should contain private key and then String for of
				// socketaddress for host
				net.connectToHost(data);
				Platform.runLater(new MultiplayerUISwitcher());
			} else {
				System.out.println("Failed to connect");
			}
			break;
		case Networker.JOINSERV:
			if (HManager != null && data.length == 3 && data[0] == (byte) 0x04 && data[1] == (byte) 0x14
					&& data[2] == (byte) 0x45) {
				HManager.add(p.getAddress(), " ");
				System.out.println("someone joined the server");
			}
			break;
		case Networker.CHAT:
			if (HManager != null) {
				HManager.sendPacket(p);
			}
			Platform.runLater(new addMessage(new String(data, 0, data.length, StandardCharsets.UTF_8)));
			break;
		case Networker.GAMEDATA:
			if (HManager != null) {
				HManager.sendPacket(p);
				HManager.turnReceived();
			}
			// display move
			break;
		case Networker.LEAVESERV:
			if (HManager != null) {
				HManager.remove(p.getAddress());
			}
			break;
		case Networker.TURN:
			if(HManager != null){

			}
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
			if(HManager != null){
				net.closeServer(serverName, serverPassword);
			} else{
				net.clearHost();
			}
		}

	}

	private class setServerOption implements Runnable {
		String name;
		String password;

		public setServerOption(String name, String password) {
			this.name = name;
			this.password = password;
		}

		@Override
		public void run() {
			serverName = name;
			serverPassword = password;
			net.setPrivateServer(this.name, this.password);
		}
	}

	private class connectServerOption implements Runnable {
		String name;
		String password;

		public connectServerOption(String name, String password) {
			this.name = name;
			this.password = password;
		}

		@Override
		public void run() {
			net.connectPrivateServer(this.name, this.password);
		}
	}

	private class sendChatOption implements Runnable {
		String chat;

		public sendChatOption(String message) {
			this.chat = Networker.username +": " +  message;
		}

		@Override
		public void run() {
			if(HManager == null){
				Networker.sendPacket(new Packet(Networker.CHAT, chat.getBytes(), Networker.host));
			}else{
				HManager.sendPacket(new Packet(Networker.CHAT, chat.getBytes(), null));
			}
		}
	}

	private class addMessage implements Runnable{
		String chat;

		public addMessage(String message) {
			this.chat = message;
		}

		@Override
		public void run() {
			ui.addMessage(chat);
		}
	}

	private class MultiplayerUISwitcher implements Runnable{

		@Override
		public void run(){
			ui.gameUI();
		}
	}

	private class sendGameOption implements Runnable {

		public sendGameOption() {
			// todo
		}

		@Override
		public void run() {
			// todo
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
