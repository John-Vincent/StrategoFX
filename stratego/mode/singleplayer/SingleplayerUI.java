package stratego.mode.singleplayer;

import stratego.mode.Mode;
import stratego.mode.menus.main.MainMenuUI;
import stratego.components.friendslist.FriendModel;
import stratego.components.friendslist.FriendsList;
import stratego.components.gameboard.GameScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

/**
 * Single Player UI Displays the gameboard for the user.
 */
public class SingleplayerUI extends Mode {
	private GridPane pane;
	private boolean flShowing;
	private FriendsList friendsList;
	/**
	 * Constructor creates the gameboard.
	 */
	public SingleplayerUI() {
		super(new GridPane());
		FriendModel friendModel = new FriendModel();
		this.setWorker(new SingleplayerWorker(friendModel));
		this.setMinSize(375, 400 + GameScene.startY);
		this.setPrefWidth(750);
		this.setPrefHeight(800 + GameScene.startY);
		this.setResizable(true);
		pane = (GridPane) this.getRoot();
		setConstraints();
		
		friendsList = new FriendsList(5, friendModel, this.getWorker());
		friendsList.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		GameScene game = new GameScene();
		GameScene.vsP = 0;
		game.autosize();
		
		Button back = new Button("Exit");
		back.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		back.setFocusTraversable(false);
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setNextMode(new MainMenuUI());
				addTask("back");
				GameScene.hFactor = 1;
				GameScene.wFactor = 1;
				GameScene.kX = 0;
				GameScene.kY = 0;
			}
		});

		Button flButton = new Button("Toggle Friend List");
		flButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		flButton.setFocusTraversable(false);
		flButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				if(flShowing){
					flShowing = false;
					pane.getChildren().remove(game);
					pane.getChildren().remove(friendsList);
					pane.add(game, 0, 1, 11, 10);
				}else{
					flShowing = true;
					pane.getChildren().remove(game);
					pane.add(game, 0, 1, 7, 10);
					pane.add(friendsList, 7, 1, 2, 10);
				}
			}
		});
		
		game.requestFocus();
		game.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event){
				game.requestFocus();
			}
		});
		
		pane.add(back, 0, 0, 5, 1);
		pane.add(flButton, 5, 0, 6, 1);
		pane.add(game, 0, 1, 11, 10);
		

	}

	public void setConstraints() {
		final int numCols = 11;
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
}
