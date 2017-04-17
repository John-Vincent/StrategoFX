package stratego.mode.multiplayer;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import stratego.network.Packet;
import stratego.components.friendslist.FriendModel;
import stratego.mode.ModeWorker;

/*
*Class that helps the multiplayer screen and the network communicate.
*/
public class MultiplayerWorker extends ModeWorker {

	/**
	 * Sets the tasklist that communicates tasks from the UI to the worker.
	 * @param friendModel
	 *
	 * @param q
	 *            The queue that MultiplayerUI uses to pass request to the
	 *            MultiplayerWorker.
	 */
	public MultiplayerWorker(FriendModel friendModel) {
		super();
		//TODO implement fm shit
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
	public boolean addTask(String name, Object...arg){
		switch (name) {
			case "setServer":
				queueTask(new setServerOption((String) arg[0], (String) arg[1]));
				return true;
			case "connectServer":
				queueTask(new connectServerOption((String) arg[0], (String) arg[1]));
				return true;
			case "message":
				//TODO
				return true;
			default:
				return false;
		}
	}

	@Override
	protected boolean handlePacket(Packet p){
    byte[] data = p.getData();
    byte t = p.getType();
		switch(t){
			case Networker.PING:
				System.out.println("Ping from: " + p.getSocketAddress());
				break;
			case Networker.OPENSERV:
				if(data.length == 1 && data[0] == 0x01){
					System.out.println("Server opened");
				} else{
					System.out.println("Server failed to open");
				}
				break;
			case Networker.CONSERV:
				if(data.length != 1){
					//todo data should contain private key and then String for of socketaddress for host
				} else{
					System.out.println("Failed to connect");
				}
		}
    return true;
  }

	private class MenuOptions implements Runnable {

		@Override
		public void run() {
			setRunning(false);
		}

	}

	private class setServerOption implements Runnable {
		String name;
		String password;

		public setServerOption(String name, String password){
			this.name = name;
			this.password = password;
		}

		@Override
		public void run(){
			net.setPrivateServer(this.name, this.password);
		}
	}

	private class connectServerOption implements Runnable{
		String name;
		String password;

		public connectServerOption(String name, String password){
			this.name = name;
			this.password = password;
		}

		@Override
		public void run(){
			net.connectPrivateServer(this.name, this.password);
		}
	}
}
