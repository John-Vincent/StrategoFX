package stratego.mode.menus.signup;



import javafx.scene.paint.*;
import javafx.stage.*;
import stratego.mode.Mode;
import javafx.scene.*;
import stratego.network.Networker;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.geometry.*;


public class SignupMenuUI extends Mode{

    public SignupMenuUI(){
        //Stage primaryStage;
        //primaryStage.setTitle("Sign Up");

        super(new GridPane());
        this.worker = new SignupMenuWorker(this.task);

        GridPane grid = (GridPane) this.getRoot();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Button btn1 = new Button("Submit");
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn1.getChildren().add(btn1);
        grid.add(hbBtn1, 1, 5);




        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

//        btn1.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent e) {
//                actiontarget.setFill(Color.FIREBRICK);
//                actiontarget.setText("Submit button pressed");
//            }
//        });

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

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 2, 2);


        PasswordField pwBox2 = new PasswordField();
        grid.add(pwBox2, 2, 3);

//        Scene scene = new Scene(grid, 300, 275);
//        primaryStage.setScene(scene);
//        primaryStage.show();

    }
    @Override
    public void startWorker(){
        this.worker.run();
    }

}
