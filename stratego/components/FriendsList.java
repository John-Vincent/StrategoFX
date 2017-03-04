package stratego.components;

import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import stratego.components.FriendModel;

/**
* @author Bradley Rhein <bdrhein@iastate.edu>
* FriendsList Object.
* Creates a FriendsList which is a VBox containing multiple VBoxes, each of which contains info on this users friends. Using a FriendsList class makes it possible to add this users friends list anywhere in the project easily.
*/
public class FriendsList extends VBox implements Observer {
	/**
	* The padding used between elements within the VBox that holds the friend list.
	*/
	public static final int smallPadding = 2;

	public FriendsList(double n){
		super(n);
	}
	
	/**
	* Updates the friend list.
	*
	* When called, the vbox which is displaying the friend list is cleared, and filled with an updated list of friends.
	*
	* @param friends The FriendModel object which stores information about this users friends.
	* @param arg1 
	*/
	@Override
	public void update(Observable friends, Object arg1) {
		FriendModel fm = (FriendModel) friends;
		FriendVBox[] friendBoxes = createBoxes(fm, fm.getFriendListSize());

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				getChildren().clear();
				for (FriendVBox box : friendBoxes) {
					getChildren().add(box.getVBox());
				}
			}

		});

	}

	/**
	* Creates an array of FriendVBoxes.
	*
	* Uses the arraylist stored in FriendModel to generate a FriendVBox for each friend in that arraylist.
	* @param friends The FriendModel object from which the arraylist will come from.
	* @param size The size of the created FriendVBox array.
	* @return The created FriendVBox array.
	*/
	public FriendVBox[] createBoxes(FriendModel friends, int size) {
		FriendVBox[] friendBoxes = new FriendVBox[size];

		for (int i = 0; i < size; i++) {
			friendBoxes[i] = new FriendVBox(friends.getFriendName(i), friends.getFriendStatus(i));
		}
		return friendBoxes;
	}
	
	/**
	* FriendVBox Object.
	* Creates a unique VBox storing the name of a friend and online status. This makes dynamic resizing of the friends list possible.
	*/
	private class FriendVBox {
		private VBox box;
		private Label name;
		private Label status;

		/**
		* Constructor.
		* @param name The name of the friend.
		* @param status The online status of the friend.
		*/
		private FriendVBox(String name, String status) {
			this.name = new Label(name);
			this.status = new Label(status);
			box = new VBox(smallPadding, this.name, this.status);
			VBox.setVgrow(this.name, Priority.ALWAYS);
			VBox.setVgrow(this.status, Priority.ALWAYS);
			this.name.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			this.status.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		}
		
		/** Returns this instances created VBox. */
		private VBox getVBox() {
			return box;
		}
	}

}
