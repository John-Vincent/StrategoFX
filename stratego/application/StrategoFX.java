package stratego.application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import stratego.network.Networker;
import stratego.mode.Mode;
import stratego.mode.menus.main.MainMenuUI;



public class StrategoFX extends Application{
  private Stage stage;
  private Mode mode;
  private Networker online = new Networker();
  public static Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
  final double defaultStageSize = 2/3;

  public void start(Stage stage){

    this.stage = stage;
    this.stage.setWidth(screenSize.getWidth()*2/3);
    this.stage.setHeight(screenSize.getHeight()*2/3);
    this.stage.setX((screenSize.getWidth()-this.stage.getWidth())/2 + screenSize.getMinX());
    this.stage.setY((screenSize.getHeight()-this.stage.getHeight())/2 + screenSize.getMinY());

    this.mode = new MainMenuUI();
    this.stage.setScene(this.mode);
    this.stage.show();
    this.stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

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
