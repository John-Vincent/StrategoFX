package stratego.application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import stratego.network.Networker;
import stratego.mode.Mode;



public class StrategoFX extends Application{
  private Stage stage;
  private Mode mode;
  private Networker online = new Networker();

  public void start(Stage stage){

    this.stage = stage;

    stage.show();
    stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

      @Override
      public void handle(WindowEvent arg){
        System.exit(0);
      }
    });

  }

  public void setMode(Mode mode){
    this.mode = mode;
    this.stage.setScene(this.mode);
    this.mode.startWorker(this.online);
  }


  public static void main(String[] args){
    launch(args);
  }
}
