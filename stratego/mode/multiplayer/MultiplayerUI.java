package stratego.mode.multiplayer;

import stratego.mode.Mode;
import javafx.scene.*;
import stratego.network.Networker;
import stratego.application.Background;

/**
*Multiplayer UI
*This UI shows the game screen as well as many additional multiplayer features
*/
public class MultiplayerUI extends Mode{
	
	/**
	*Constructor
	*Makes the Multiplayer UI
	*/
  public MultiplayerUI(Parent p){
    super(p);
    this.setWorker(new MultiplayerWorker(this.getTaskList()));

  }




}
