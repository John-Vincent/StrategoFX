package stratego.components.friendslist;

import java.util.ArrayList;
import java.util.Observable;

/**
* @author Bradley Rhein <bdrhein@iastate.edu>
* FriendsModel Object.
* A FriendModel is used in tandem with a FriendsList to create a functional friend list for a user. The FriendModel object is essentially an arraylist containing information about this users friend, and is used by FriendsList to generate a unique friend-list VBox.
*/
public class FriendModel extends Observable {

	/**
	* The ArrayList that stores this users friends. Each element is a Friend object.
	*/
	public ArrayList<Friend> friendList;

	/** 
	* Constructor.
	* Creates a new arraylist to store this users friends.
	*/
	public FriendModel(){
		friendList = new ArrayList<Friend>();
	}

	/**
	* Adds a friend to this users friendList.
	*
	* Updates the friendList and notifies it's observer (FriendsList) that the list has been updated. 
	* @param name The name of the friend.
	* @param status The online status of the friend.
	*/
	public void addFriend(String name, String status) {
		friendList.add(new Friend(name, status));
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	*	Clears this users friend list.
	*/
	public void clearFriends(){
		friendList.clear();
	}
	
	/**
	*	Returns the name of the users friend at index i.
	*	@param i The element/friend in the friend list.
	*	@return The name of the friend specified at index i.
	*/
	public String getFriendName(int i) {
		return friendList.get(i).getName();
	}

	/**
	*	Returns the online status of the users friend at index i.
	*	@param i The element/friend in the friend list.
	*	@return The online status of the friend specified at index i.
	*/
	public String getFriendStatus(int i) {
		return friendList.get(i).getStatus();
	}

	/**
	*	Returns the size of this users friend list.
	*	@return Size of friendList.
	*/
	public int getFriendListSize() {
		return friendList.size();
	}
	
	/**
	* Friend Object.
	* Creates an object Friend which stores the name and online status of a users friend.
	*/
	private class Friend {
		private String name;
		private String status;

		/**
		* Constructor.
		* @param name The name of the friend.
		* @param status The online status of the friend.
		*/
		private Friend(String name, String status) {
			this.name = name;
			this.status = status;
		}
		/**
		*	Returns the name of this Friend.
		*	@return The name of this Friend.
		*/
		public String getName() {
			return this.name;
		}

		/**
		*	Returns the online status of this Friend.
		*	@return The online status of this Friend.
		*/
		public String getStatus() {
			return this.status;
		}
	}

}
