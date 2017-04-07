package stratego.mode.multiplayer;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import stratego.network.Packet;
import stratego.mode.ModeWorker;

/*
*Class that helps the multiplayer screen and the network communicate.
*/
public class MultiplayerWorker extends ModeWorker {

	/**
	 * Sets the tasklist that communicates tasks from the UI to the worker.
	 *
	 * @param q
	 *            The queue that MultiplayerUI uses to pass request to the
	 *            MultiplayerWorker.
	 */
	public MultiplayerWorker() {
		super();
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
	protected boolean handlePacket(Packet p){
    byte[] data = p.getData();
    byte t = p.getType();
		switch(t){
			case Networker.PING:
				System.out.println("Ping from: " + p.getSocketAddress());
				break;
			case: Networker.OPENSERV:
				if(data.length == 1 && data[0] == 0x01){
					System.out.println("Server opened");
				} else{
					System.out.println("Server failed to open");
				}
				break;
			case: Networker.CONSERV:
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
}
