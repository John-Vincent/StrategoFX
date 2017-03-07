package stratego.mode.menus.signup;

import javafx.scene.paint.*;
import javafx.stage.*;
import stratego.mode.Mode;
import stratego.mode.menus.login.LoginMenuUI;
import stratego.mode.menus.main.MainMenuUI;
import javafx.scene.*;
import stratego.network.Networker;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;

public class SignupMenuUI extends Mode {
	int flag = 0;
	public SignupMenuUI() {

		super(new GridPane());
		this.setWorker(new SignupMenuWorker(this.getTaskList()));

		GridPane grid = (GridPane) this.getRoot();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		Button btn1 = new Button("Submit");
		Button btn2 = new Button("Cancel");
		HBox hbBtn1 = new HBox(10);
		hbBtn1.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn1.getChildren().addAll(btn1, btn2);
		grid.add(hbBtn1, 1, 5);

		final Text actiontarget = new Text();
		grid.add(actiontarget, 1, 6);

		Text scenetitle = new Text("Welcome to Stratego");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Text userName = new Text("User Name:");
		grid.add(userName, 0, 1);

		TextField userTextField = new TextField();
		grid.add(userTextField, 2, 1);

		Text pw = new Text("Password:");
		grid.add(pw, 0, 2);

		Text pw2 = new Text("Re-type Password:");
		grid.add(pw2, 0, 3);

		Text missmatch = new Text();
		missmatch.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		missmatch.setFill(Color.RED);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 2, 2);

		PasswordField pwBox2 = new PasswordField();
		grid.add(pwBox2, 2, 3);


		btn1.setOnAction(new EventHandler<ActionEvent>() {
			@Override


			public void handle(ActionEvent e) {
				if (!(userTextField.getText().isEmpty() || pwBox.getText().isEmpty() || pwBox2.getText().isEmpty())) {
					if (!pwBox.getText().equals(pwBox2.getText())) {
						missmatch.setText("Passwords do not match.");
						if(flag == 0){
							grid.add(missmatch, 1, 7);
							flag++;
						}
					} else {
						setNextMode(new LoginMenuUI());
						addTask("signup", userTextField.getText(), pwBox.getText());
					}
				} else {
					missmatch.setText("Field(s) left blank. All fields are required.");
					if(flag==0){
						grid.add(missmatch, 1, 7);
						flag++;
					}
				}

			}
		});

		btn2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				setNextMode(new LoginMenuUI());
				addTask("login");
			}
		});

	}

}
