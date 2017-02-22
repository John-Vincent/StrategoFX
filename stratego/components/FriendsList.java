package stratego.components;

import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import stratego.components.FriendModel;

public class FriendsList extends VBox implements Observer {
	public static final int smallPadding = 2;

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
