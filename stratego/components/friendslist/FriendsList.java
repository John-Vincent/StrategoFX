package stratego.components.friendslist;

import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.scene.layout.Pane;
import stratego.mode.ModeWorker;


/**
 * FriendsList Object. Creates a FriendsList which is a VBox containing multiple
 * VBoxes, each of which contains info on this users friends. Using a
 * FriendsList class makes it possible to add this users friends list anywhere
 * in the project easily.
 */
public class FriendsList extends Pane implements Observer {
	VBox mainVBox, friendVBoxes;
	Pane pane;
	FriendsList friendsList;
	ModeWorker worker;
	/**
	 * The padding used between elements within the VBox that holds the friend
	 * list.
	 */
	public static final int smallPadding = 2;

	/**
	 * Constructor. Uses the parent constructor to create a VBox.
	 *
	 * @param n
	 *            The padding to be used in this unique VBox.
	 * @author Bradley Rhein bdrhein@iastate.edu
	 */
	public FriendsList(double n, FriendModel friendModel, ModeWorker worker) {
		super();
		this.getChildren().clear();
		this.setStyle("-fx-background-color: brown;");
		/*
		 * the global variable "friendsList" is here for the sake of
		 * convenience only for the .setOnAction overridden handle methods
		 */
		this.friendsList = this;
		this.worker = worker;
		friendVBoxes = new VBox(15);

		Text fl = new Text("Friends List");
		fl.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		Button af = new Button("Add a Friend");

		this.setPadding(new Insets(10, 15, 10, 10));
		friendModel.addObserver(this);
		this.mainVBox = new VBox(n, fl, friendVBoxes, af);
		this.mainVBox.setAlignment(Pos.CENTER);
		this.getChildren().addAll(this.mainVBox);
		VBox.setVgrow(fl, Priority.ALWAYS);
		VBox.setVgrow(af, Priority.ALWAYS);
		af.setMaxSize(Double.MAX_VALUE, 50);
		af.setFocusTraversable(false);

		af.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				addFriend(mainVBox, friendsList, friendModel);

			}
		});
	}

	/**
	*	Adds a friend to this users friendlist.
	*	For demo 3, this method will be moved to the components package into the FriendsList class so that other screens can utilize this feature without duplication of code.
	*	@param friends The VBox containing the components of the friend list.
	*	@param friendsList The logged in users friend list.
	*	@param friendModel The observable FriendModel object of this user.
	* @author 	Bradley Rhein  bdrhein@iastate.edu
	*/
	public void addFriend(VBox friends, FriendsList friendsList, FriendModel friendModel) {
		TextField friendName = new TextField();
		HBox userName = new HBox(5, new Text("Friend's Username:"), friendName);
		Button addFriend = new Button("Add");
		Button cancel = new Button("Cancel");
		HBox buttons = new HBox(5, addFriend, cancel);
		VBox popup = new VBox(5, userName, buttons);
		popup.setAlignment(Pos.CENTER);
		this.getChildren().clear();
		this.getChildren().addAll(popup);
		VBox.setVgrow(addFriend, Priority.ALWAYS);
		addFriend.setMaxSize(Double.MAX_VALUE, 50);

		addFriend.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				worker.addTask("addfriend", friendName.getText());
				friendsList.getChildren().clear();
				friendsList.getChildren().addAll(mainVBox);
			}
		});

		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				friendsList.getChildren().clear();
				friendsList.getChildren().addAll(mainVBox);
			}
		});
	}



	@Override
	public void update(Observable friends, Object arg1) {
		FriendModel fm = (FriendModel) friends;
		FriendVBox[] friendBoxes = createBoxes(fm, fm.getFriendListSize());

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				friendVBoxes.getChildren().clear();
				for (FriendVBox box : friendBoxes) {
					friendVBoxes.getChildren().add(box.getVBox());
				}
			}

		});

	}

	/**
	 * Creates an array of FriendVBoxes. This method uses the arraylist stored
	 * in FriendModel to generate a FriendVBox for each friend in that
	 * arraylist.
	 *
	 * @param friends
	 *            The FriendModel object from which the arraylist will come
	 *            from.
	 * @param size
	 *            The size of the created FriendVBox array.
	 * @return The created FriendVBox array.
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
			this.name.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
			this.status = new Label(status);
			this.status.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
			box = new VBox(smallPadding, this.name, this.status);
			box.setStyle("-fx-background-color: #43d3e0; -fx-stroke: black; -fx-stroke-width: 3;");
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
