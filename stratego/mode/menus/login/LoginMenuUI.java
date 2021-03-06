package stratego.mode.menus.login;

import stratego.mode.Mode;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import stratego.mode.menus.main.MainMenuUI;
import stratego.mode.menus.signup.SignupMenuUI;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

/**
 * Login Menu UI
 * The Login menu contains two clearly labeled text fields for users to input their username and password as well as two
 * buttons:Sign in which verifies credentials before sending the user to the main menu and
 * sign up which sends the user to the sign up menu
 */
public class LoginMenuUI extends Mode {
	/**
	 * Constructor
	 * uses the grid pane tool to create the login menu screen
	 * @author Ryan McCullough rmm@iastate.edu
	 */
	public LoginMenuUI() {

		super(new GridPane());
		this.setWorker(new LoginMenuWorker());
		GridPane grid = (GridPane) this.getRoot();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		Button btn1 = new Button("Sign in");
		Button btn2 = new Button("New? Sign Up!");

		HBox hbBtn1 = new HBox(10, btn1, btn2);
		hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
		grid.add(hbBtn1, 1, 4);

		// doesn't make buttons larger. Likely that gridpane forces them. Kept
		// for debugging.
		HBox.setHgrow(btn1, Priority.ALWAYS);
		HBox.setHgrow(btn2, Priority.ALWAYS);
		btn1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		btn2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		final Text actiontarget = new Text();
		grid.add(actiontarget, 1, 6);

		Text scenetitle = new Text("Welcome to StrategoFX");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);

		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);

		btn1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// this sets the next UI to be displayed this UI's worker stops
				// running
				setNextMode(new MainMenuUI());
				addTask("signin", userTextField.getText(), pwBox.getText());
			}
		});

		btn2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				setNextMode(new SignupMenuUI());
				addTask("signup");
			}
		});
	}

}
