package stratego.application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import stratego.mode.Mode;
import stratego.components.Sizes;



public class StrategoFX extends Application{
  private Stage stage;
  private Mode mode;
  private Thread back = new Thread(new Background(this));

  public void start(Stage stage){

    this.stage = stage;

    back.start();

    this.stage.setWidth(Sizes.screenSize.getWidth()*Sizes.stageSize);
    this.stage.setHeight(Sizes.screenSize.getHeight()*Sizes.stageSize);

    this.stage.setX((Sizes.screenSize.getWidth()-this.stage.getWidth())/2 + Sizes.screenSize.getMinX());
    this.stage.setY((Sizes.screenSize.getHeight()-this.stage.getHeight())/2 + Sizes.screenSize.getMinY());

    this.stage.show();
    this.stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

      @Override
      public void handle(WindowEvent arg){
        back.interrupt();
        System.exit(0);
      }
    });
  }

  public void setMode(Mode mode){
    this.mode = mode;
    this.stage.setScene(this.mode);
    this.stage.setX((Sizes.screenSize.getWidth()-this.stage.getWidth())/2 + Sizes.screenSize.getMinX());
    this.stage.setY((Sizes.screenSize.getHeight()-this.stage.getHeight())/2 + Sizes.screenSize.getMinY());
    double[] size = mode.getMinSize();
    this.stage.setMinWidth(size[0]);
    this.stage.setMinHeight(size[1]);
    System.out.println(size[1]);
  }


  public static void main(String[] args){
    launch(args);
  }
}
