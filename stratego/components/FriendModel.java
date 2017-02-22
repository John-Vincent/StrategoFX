package stratego.components;

import java.util.ArrayList;
import java.util.Observable;

public class FriendModel extends Observable {

	public ArrayList<Friend> friendList;
	
	public FriendModel(){
		friendList = new ArrayList<Friend>();
	}

	public void addFriend(String name, String status) {
		friendList.add(new Friend(name, status));
		this.setChanged();
	}

	public String getFriendName(int i) {
		return friendList.get(i).getName();
	}

	public String getFriendStatus(int i) {
		return friendList.get(i).getStatus();
	}

	public int getFriendListSize() {
		return friendList.size();
	}

	private class Friend {
		private String name;
		private String status;

		private Friend(String name, String status) {
			this.name = name;
			this.status = status;
		}

		public String getName() {
			return this.name;
		}

		public String getStatus() {
			return this.status;
		}
	}

}
