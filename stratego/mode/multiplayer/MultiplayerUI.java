package stratego.mode.multiplayer;

import stratego.mode.Mode;
import stratego.mode.menus.main.MainMenuUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import stratego.network.Networker;
import stratego.application.Background;
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
		pane.setGridLinesVisible(true);
		
		EventHandler<ActionEvent> createHandler = new EventHandler<ActionEvent>() {
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
		
		TextArea chatLog = new TextArea();
		pane.add(chatLog, 0, 0, 2, 4);
		chatLog.appendText("lol");
		pane.setGridLinesVisible(true);
		Button test = new Button();
		pane.add(test, 1, 0);
		//VBox chatUI = new VBox(5, chatLog, chatButtons, disconnect);
		
	}
}
