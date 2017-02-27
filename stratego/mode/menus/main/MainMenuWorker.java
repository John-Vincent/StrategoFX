package stratego.mode.menus.main;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import java.util.Arrays;
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
		case "ping":
			return new PingRequest();
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

	@Override
  protected boolean handlePacket(DatagramPacket p){
		String temp;
    if(p == null)
      return false;
    byte[] data = p.getData();
    byte type = data[0];
    data = Arrays.copyOfRange(data, 1, data.length);
    switch(type){
      case Networker.PING:
        System.out.println("ping from: " + p.getSocketAddress());
        break;
      case Networker.FRIENDQ:
        temp = new String(data, p.getOffset(), p.getLength()-1);
				String[] friends = temp.split(";");
				friendModel.clearFriends();
				for(int i = 0; i < friends.length; i++){
					String[] friendData = friends[i].split(":");
					if(friendData.length == 2){
						if(friendData[1].equals('0'))
							friendData[1] = "offline";
						else
							friendData[1] = "online";
						friendModel.addFriend(friendData[0], friendData[1]);
					}
				}
        break;
			case Networker.FRIENDR:
				if(data[0] == 0x00)
					break;
				temp = new String(data, p.getOffset(), p.getLength()-1);
				friendModel.addFriend(temp, "pending");
				break;
      default:
        System.out.println("unknown packet from: " + p.getSocketAddress());
    }
    return true;
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
		}
	}

	private class FriendUpdater implements Runnable {
		private long time=0;

		@Override
		public void run(){
			if(time + 300000 < System.currentTimeMillis()){
				this.time = System.currentTimeMillis();
				net.requestFriendsList();
			}
		}
	}

}
