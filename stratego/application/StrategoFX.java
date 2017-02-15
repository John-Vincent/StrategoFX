package stratego.application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import stratego.network.Networker;
import stratego.mode.Mode;
import stratego.mode.menus.main.MainMenuUI;
import stratego.components.Sizes;



public class StrategoFX extends Application{
  private Stage stage;
  private Mode mode;
  private Networker online = new Networker();

  public void start(Stage stage){

    this.stage = stage;

    this.stage.setWidth(Sizes.screenSize.getWidth()*Sizes.stageSize);
    this.stage.setHeight(Sizes.screenSize.getHeight()*Sizes.stageSize);

    this.stage.setX((Sizes.screenSize.getWidth()-this.stage.getWidth())/2 + Sizes.screenSize.getMinX());
    this.stage.setY((Sizes.screenSize.getHeight()-this.stage.getHeight())/2 + Sizes.screenSize.getMinY());

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
