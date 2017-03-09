package stratego.components.friendslist;

import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
*	FriendsList Object.
*	Creates a FriendsList which is a VBox containing multiple VBoxes, each of which contains info on this users friends. Using a FriendsList class makes it possible to add this users friends list anywhere in the project easily.
*/
public class FriendsList extends VBox implements Observer {
	/**
	*	The padding used between elements within the VBox that holds the friend list.
	*/
	public static final int smallPadding = 2;

	/**
	*	Constructor.
	*	Uses the parent constructor to create a VBox.
	*	@param	n	The padding to be used in this unique VBox.
	* @author Bradley Rhein bdrhein@iastate.edu
	*/
	public FriendsList(double n){
		super(n);
	}


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
	*	Creates an array of FriendVBoxes.
	*	This method uses the arraylist stored in FriendModel to generate a FriendVBox for each friend in that arraylist.
	*	@param	friends	The FriendModel object from which the arraylist will come from.
	*	@param	size	The size of the created FriendVBox array.
	*	@return	The created FriendVBox array.
	* @author Bradley Rhein bdrhein@iastate.edu
	*/
	public FriendVBox[] createBoxes(FriendModel friends, int size) {
		FriendVBox[] friendBoxes = new FriendVBox[size];

		for (int i = 0; i < size; i++) {
			friendBoxes[i] = new FriendVBox(friends.getFriendName(i), friends.getFriendStatus(i));
		}
		return friendBoxes;
	}


	private class FriendVBox {
		private VBox box;
		private Label name;
		private Label status;

		private FriendVBox(String name, String status) {
			this.name = new Label(name);
			this.status = new Label(status);
			box = new VBox(smallPadding, this.name, this.status);
			VBox.setVgrow(this.name, Priority.ALWAYS);
			VBox.setVgrow(this.status, Priority.ALWAYS);
			this.name.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			this.status.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		}

		private VBox getVBox() {
			return box;
		}
	}

}
