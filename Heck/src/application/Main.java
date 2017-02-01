package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;


public class Main extends Application {
	@Override
    public void start(Stage primaryStage) throws InterruptedException {
		StackPane root = new StackPane();
        root.setId("pane");
        Scene scene = new Scene(root, 900, 506);
        scene.getStylesheets().addAll(this.getClass().getResource("begin.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        Thread.sleep(3000);
        scene.getStylesheets().addAll(this.getClass().getResource("end.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
