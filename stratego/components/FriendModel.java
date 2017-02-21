package stratego.components;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class FriendModel implements Observable {
	// arraylist of friend
	// subclass of friend, name online status
	// platform.run later in update
	// clear vbox, add children,
	private ArrayList<Friend> friendList = new ArrayList<Friend>();

	public void addFriend(String name, String status) {
		friendList.add(new Friend(name, status));
	}

	// method that can take an array of labels
	// put it in the vbox

	public Friend getFriend(int i) {
		return friendList.get(i);
	}


	private class Friend {
		private String name;
		private String status;

		private Friend(String name, String status) {
			this.name = name;
			this.status = status;
		}

		public String getName(){
			return this.name;
		}

		public String getStatus(){
			return this.status;
		}
	}

}
