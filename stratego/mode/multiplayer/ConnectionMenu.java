package stratego.mode.multiplayer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ConnectionMenu extends AnchorPane {
	
	AnchorPane pane;
	VBox buttonBox;
	TextField passwordField, server;
	

	public ConnectionMenu(EventHandler<ActionEvent> createHandler, EventHandler<ActionEvent> joinHandler, EventHandler<ActionEvent> backHandler) {
		pane = this;
		Button create = new Button("Create a Server");
		Button join = new Button("Join a Server");
		Button back = new Button("Back");
		VBox buttonBox = new VBox(5, create, join, back);
		
		buttonBox.setAlignment(Pos.CENTER);
		VBox.setVgrow(create, Priority.ALWAYS);
		VBox.setVgrow(join, Priority.ALWAYS);
		VBox.setVgrow(back, Priority.ALWAYS);
		this.buttonBox = buttonBox;
		create.setMaxSize(Double.MAX_VALUE, 50);
		join.setMaxSize(Double.MAX_VALUE, 50);
		back.setMaxSize(Double.MAX_VALUE, 50);
		
		create.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				inputServerInfo("create", createHandler);
			}
		});
		join.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				inputServerInfo("join", joinHandler);
			}
		});
		back.setOnAction(backHandler);
	
		AnchorPane.setLeftAnchor(buttonBox, 25.0);
		AnchorPane.setRightAnchor(buttonBox, 25.0);
		AnchorPane.setTopAnchor(buttonBox, 25.0);
		AnchorPane.setBottomAnchor(buttonBox, 25.0);
		this.getChildren().addAll(buttonBox);
		
	}

	
	public void inputServerInfo(String type, EventHandler<ActionEvent> handler){
		Text srvr = new Text("Server Name:");
		server = new TextField();
		HBox serverBox = new HBox(5, srvr, server);
		serverBox.setAlignment(Pos.CENTER);
		HBox.setHgrow(srvr, Priority.ALWAYS);
		HBox.setHgrow(server, Priority.ALWAYS);
		
		Text pswd = new Text("Password:     ");
		passwordField = new TextField();
		passwordField.setPromptText("optional");
		HBox passwordBox = new HBox(5, pswd, passwordField);
		passwordBox.setAlignment(Pos.CENTER);
		HBox.setHgrow(pswd, Priority.ALWAYS);
		HBox.setHgrow(passwordField, Priority.ALWAYS);
		
		
		Button submit = new Button("Submit");
		Button back = new Button("Back");
		HBox buttons = new HBox(5, submit, back);
		buttons.setAlignment(Pos.CENTER);
		HBox.setHgrow(submit, Priority.ALWAYS);
		HBox.setHgrow(back, Priority.ALWAYS);
		submit.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		back.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		VBox serverInfo = new VBox(5, serverBox, passwordBox, buttons);
		
		serverInfo.setAlignment(Pos.CENTER);
		VBox.setVgrow(serverBox, Priority.ALWAYS);
		VBox.setVgrow(passwordBox, Priority.ALWAYS);
		VBox.setVgrow(buttons, Priority.ALWAYS);
		serverBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		passwordBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		buttons.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		
		this.getChildren().clear();
		AnchorPane.setLeftAnchor(serverInfo, 25.0);
		AnchorPane.setRightAnchor(serverInfo, 25.0);
		AnchorPane.setTopAnchor(serverInfo, 25.0);
		AnchorPane.setBottomAnchor(serverInfo, 25.0);
		this.getChildren().addAll(serverInfo);
		
		submit.setOnAction(handler);
		
		back.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				pane.getChildren().clear();
				AnchorPane.setLeftAnchor(serverInfo, 25.0);
				AnchorPane.setRightAnchor(serverInfo, 25.0);
				AnchorPane.setTopAnchor(serverInfo, 25.0);
				AnchorPane.setBottomAnchor(serverInfo, 25.0);
				pane.getChildren().addAll(buttonBox);
			}
		});
	}
	
	public String getConnectionServer(){
		return server.getText();
	}
	
	public String getConnectionPassword(){
		return passwordField.getText();
	}
	
}
