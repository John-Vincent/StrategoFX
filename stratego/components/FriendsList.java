package stratego.components;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import stratego.components.FriendModel;

public class FriendsList extends VBox implements Observer {
	public static final int smallPadding = 3;

	@Override
	public void update(Observable arg0, Object arg1) {
		FriendVBox[] friendBoxes = createBoxes(friends, friends.size());

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
			friendBoxes[i] = new FriendVBox(friends.getFriend(i).something; //friend name and status)
		}
	}

	private class FriendVBox {
		private VBox box;

		private FriendVBox(Label name, Label status) {
			box = new VBox(smallPadding, name, status);
		}
	}

}
