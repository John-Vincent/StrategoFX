package stratego.mode.multiplayer;

import stratego.mode.Mode;
import stratego.mode.menus.main.MainMenuUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import stratego.network.Networker;
import stratego.application.Background;
import stratego.components.gameboard.GameScene;
import stratego.mode.multiplayer.ConnectionMenu;

/**
 * Multiplayer UI This UI shows the game screen as well as many additional
 * multiplayer features
 */
public class MultiplayerUI extends Mode {
	GridPane pane;
	String serverName;
	String password;
	ConnectionMenu connect;

	/**
	 * Constructor Makes the Multiplayer UI
	 */
	public MultiplayerUI() {
		super(new GridPane());
		this.setWorker(new MultiplayerWorker()); // may want to add friend model
		pane = (GridPane) this.getRoot();
		this.setMinSize(500, 400);
		pane.setPadding(new Insets(10, 10, 10, 10));
		setConstraints();
		//pane.setGridLinesVisible(true);
		
		EventHandler<ActionEvent> createHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				//TODO connect
				serverName = connect.getConnectionServer();
				password = connect.getConnectionPassword();
				//pane.getChildren().clear();
				pane.getChildren().remove(connect);
				gameUI();
				//go to actual ui
			}
		};
		
		EventHandler<ActionEvent> joinHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				//TODO connect
				serverName = connect.getConnectionServer();
				password = connect.getConnectionPassword();
				pane.getChildren().clear();
				gameUI();
				//go to actual ui
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
		
		TextArea chatWindow = new TextArea();
		chatWindow.setFocusTraversable(false);
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
		
		GameScene game = new GameScene();
		game.autosize();
		pane.add(game, 2, 0, 8, 10);
		game.requestFocus();
		game.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event){
				game.requestFocus();
			}
		});
		
		//HBox chatButtons = new HBox()
		//VBox buttons = new VBox(5, chatButtons, disconnect);
		
	}
}
