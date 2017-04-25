package stratego.mode.multiplayer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import stratego.components.friendslist.FriendModel;
import stratego.components.friendslist.FriendsList;
import stratego.components.gameboard.GameScene;
import stratego.mode.Mode;
import stratego.mode.menus.main.MainMenuUI;

/**
 * Multiplayer UI This UI shows the game screen as well as many additional
 * multiplayer features
 */
public class MultiplayerUI extends Mode {
	private GridPane pane;
	private String serverName;
	private String password;
	private ConnectionMenu connect;
	private boolean flShowing;
	private FriendsList friendsList;

	/**
	 * Constructor Makes the Multiplayer UI
	 */
	public MultiplayerUI() {
		super(new GridPane());
		FriendModel friendModel = new FriendModel();
		this.setWorker(new MultiplayerWorker(friendModel, this)); // may want to add friend model
		pane = (GridPane) this.getRoot();
		this.setMinSize(500, 400);
		pane.setPadding(new Insets(10, 10, 10, 10));
		setConstraints();
		//pane.setGridLinesVisible(true);

		friendsList = new FriendsList(5, friendModel, this.getWorker());


		EventHandler<ActionEvent> createHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				serverName = connect.getConnectionServer();
				password = connect.getConnectionPassword();
				pane.getChildren().remove(connect);
				addTask("setServer", serverName, password);
			}
		};

		EventHandler<ActionEvent> joinHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				serverName = connect.getConnectionServer();
				password = connect.getConnectionPassword();
				pane.getChildren().remove(connect);
				addTask("connectServer", serverName, password);
			}
		};

		EventHandler<ActionEvent> backHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				setNextMode(new MainMenuUI());
				addTask("main");
			}
		};

		connect = new ConnectionMenu(createHandler, joinHandler, backHandler);
		pane.add(connect, 3, 3, 4, 4);
	}

	public void setConstraints() {
		final int numCols = 10;
		final int numRows = 10;
		for (int i = 0; i < numCols; i++) {
			ColumnConstraints colConst = new ColumnConstraints();
			colConst.setPercentWidth(100.0 / numCols);
			pane.getColumnConstraints().add(colConst);
		}
		for (int i = 0; i < numRows; i++) {
			RowConstraints rowConst = new RowConstraints();
			rowConst.setPercentHeight(100.0 / numRows);
			pane.getRowConstraints().add(rowConst);
		}
	}

	public void gameUI(){
		//setConstraints();
		//pane.setGridLinesVisible(true);
		flShowing = true;

		VBox flVBox = new VBox(friendsList);
		flVBox.setFocusTraversable(false);
		VBox.setVgrow(friendsList, Priority.ALWAYS);
		friendsList.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		TextArea chatWindow = new TextArea();
		chatWindow.setFocusTraversable(false);
		chatWindow.setEditable(false);
		pane.add(chatWindow, 0, 0, 2, 7);

		TextField message = new TextField();
		message.setFocusTraversable(false);
		Button disconnect = new Button("Disconnect");

		Button showFriendList = new Button("Friend List");
		Button sendMessage = new Button("Send Message");
		HBox chatButtons = new HBox(5, showFriendList, sendMessage);
		showFriendList.setFocusTraversable(false);
		sendMessage.setFocusTraversable(false);
		chatButtons.setAlignment(Pos.CENTER);
		HBox.setHgrow(showFriendList, Priority.ALWAYS);
		HBox.setHgrow(sendMessage, Priority.ALWAYS);
		showFriendList.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		sendMessage.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		VBox chatElements = new VBox(5, message, chatButtons, disconnect);

		disconnect.setFocusTraversable(false);
		chatElements.setAlignment(Pos.CENTER);
		VBox.setVgrow(message, Priority.ALWAYS);
		VBox.setVgrow(disconnect, Priority.ALWAYS);
		VBox.setVgrow(chatButtons, Priority.ALWAYS);
		message.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		disconnect.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		chatButtons.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		pane.add(chatElements, 0, 7, 2, 3);
		pane.add(flVBox, 8, 0, 2, 10);

		GameScene game = new GameScene();
		game.autosize();
		pane.add(game, 2, 0, 6, 10);
		game.requestFocus();
		game.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event){
				game.requestFocus();
			}
		});

		sendMessage.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				chatWindow.appendText("You: " + message.getText() + "\n");
				addTask("message", message.getText());
				message.clear();
			}
		});

		message.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				chatWindow.appendText("You: " + message.getText() + "\n");
				addTask("message", message.getText());
				message.clear();
			}
		});

		showFriendList.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				if(flShowing){
					flShowing = false;
					pane.getChildren().remove(game);
					pane.getChildren().remove(flVBox);
					pane.add(game, 2, 0, 8, 10);
				}else{
					flShowing = true;
					pane.getChildren().remove(game);
					pane.add(game, 2, 0, 6, 10);
					pane.add(flVBox, 8, 0, 2, 10);
				}
			}
		});

		disconnect.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				setNextMode(new MainMenuUI());
				addTask("main");
				GameScene.hFactor = 1;
				GameScene.wFactor = 1;
				GameScene.kX = 0;
				GameScene.kY = 0;
			}
		});

	}
}
