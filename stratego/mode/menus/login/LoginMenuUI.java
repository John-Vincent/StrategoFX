package stratego.mode.menus.login;




import javafx.scene.paint.Color;
import javafx.stage.Stage;
import stratego.mode.Mode;
import javafx.scene.*;
import stratego.network.Networker;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import stratego.mode.menus.main.MainMenuUI;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;


public class LoginMenuUI extends Mode{

    LoginMenuWorker worker;

    public LoginMenuUI(){
        //Stage primaryStage;
        //primaryStage.setTitle("Welcome");
        super(new GridPane());
        this.worker = new LoginMenuWorker(this.task);
        this.setWorker(this.worker);

        GridPane grid = (GridPane) this.getRoot();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Button btn1 = new Button("Sign in");
        Button btn2 = new Button("New? Sign Up!");

        btn1.setOnAction(new EventHandler<ActionEvent>(){
          @Override
          public void handle(ActionEvent e){
            //this sets the next UI to be displayed this UI's worker stops running
            next = new MainMenuUI();
            task.add(worker.getSignInRequest());
          }
        });

        HBox hbBtn1 = new HBox(10, btn1, btn2);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        grid.add(hbBtn1, 1, 4);


        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        Text scenetitle = new Text("Welcome to Stratego");
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
//        Scene scene = new Scene(grid, 300, 275);
//        primaryStage.setScene(scene);
//        primaryStage.show();


    }

}
