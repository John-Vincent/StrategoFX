package stratego.application;

import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import stratego.mode.Mode;
import stratego.components.Sizes;
import stratego.components.gameboard.GameScene;

/**
 * this class is where JavaFX enters the program
 */
public class StrategoFX extends Application{
  private Stage stage;
  private Mode mode;
  private Thread back = new Thread(new Background(this));
  public static boolean firstLoad;


  /**
   * sets up the stage the Background object/thread and the basic application settings
   * @param  stage                   the stage made by javafx to be used as the main stage for the application
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-08T21:28:59+000
   */
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

    this.stage.setX((Sizes.screenSize.getWidth()-this.stage.getWidth())/2 + Sizes.screenSize.getMinX());
    this.stage.setY((Sizes.screenSize.getHeight()-this.stage.getHeight())/2 + Sizes.screenSize.getMinY());
    stratego.components.MusicPlayer.musicPlayer = new MediaPlayer(new Media(new File("MUTE.mp3").toURI().toString()));
    firstLoad = true;
  }

  /**
   * sets the current Mode that is being displayed, this is called by Background to change the current Mode.
   * @param  mode                   the mode to be displayed
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-08T21:30:19+000
   */
  public void setMode(Mode mode){
    this.mode = mode;
    this.stage.setScene(this.mode);
    double[] size = mode.getMinSize();
    this.stage.setMinWidth(size[0]);
    this.stage.setMinHeight(size[1]);
    this.stage.setWidth(mode.getPrefWidth());
    this.stage.setHeight(mode.getPrefHeight());
    this.stage.setResizable(mode.resizable());
  }

  /**
   * launches the app
   * @param  args                this will be empty
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-08T21:51:21+000
   */
  public static void main(String[] args){
    launch(args);

  }

}
