package stratego.components;

import java.util.ArrayList;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import stratego.components.FriendModel;

public class FriendsList extends VBox implements Observer {
	public static final int smallPadding = 3;

	@Override
	public void update(Observable arg0, Object arg1) {
		FriendModel m = (FriendModel) arg0;
		FriendVBox[] friendBoxes = createBoxes(friends);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// TODO
			}

		});

	}

	public FriendVBox[] createBoxes(FriendModel friends, int size) {
		FriendVBox[] friendBoxes = new Label[size];

		for (int i = 0; i < size; i++) {
			 f = new FriendVBox(friends.getFriend(i).getName(), freinds.getFriend(i).getStatus()); //friend name and status)
		}
	}

	private class FriendVBox {
		private VBox box;

		private FriendVBox(String name, String status) {
			box = new VBox(smallPadding, name, status);
		}
	}

}
