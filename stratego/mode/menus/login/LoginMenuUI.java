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


public class LoginMenuUI extends mode{



    public LoginMenuUI(){
        //Stage primaryStage;
        //primaryStage.setTitle("Welcome");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Button btn1 = new Button("Sign in");
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn1.getChildren().add(btn1);
        grid.add(hbBtn1, 1, 4);


        Button btn2 = new Button("New? Sign Up!");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn2.getChildren().add(btn2);
        grid.add(hbBtn2, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

//        btn1.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent e) {
//                actiontarget.setFill(Color.FIREBRICK);
//                actiontarget.setText("Sign in button pressed");
//            }
//        });

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

    @Override
    public void startWorker(Networker online){
        MainMenuWorker w = new MainMenuWorker();
        w.setQueues(online);
        this.worker = new Thread(w);
        this.worker.start();
    }

    @Override
    public void terminate(){
        this.worker.interrupt();
    }
}
