package stratego.mode.menus.settings;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import stratego.components.MusicPlayer;
import stratego.mode.ModeWorker;
/**
*Class that helps the Settings Menu and the network communicate
*/
public class SettingsMenuWorker extends ModeWorker{

	/**
	*Sets the tasklist that communicates tasks between the SettingsMenuUI and the worker.
	*@param	q	The queue that SettingsMenuUI uses to pass request to the SettingsMenuWorkerWorker.
	*
	*/
  public SettingsMenuWorker(){
    super();
  }

  @Override
  public boolean addTask(String name){
    switch(name){
      case "back":
    	  queueTask(new BackRequest());
        return true;
      case "update":
    	  updateSettingsFile();
    	return true;
      default:
        return false;
    }
  }

  public void updateSettingsFile(){
	  File oldFile = new File("SFXsettings.brad");
	  oldFile.delete();
	  try{
		  PrintWriter writer = new PrintWriter(new File("SFXsettings.brad"));
		  //TODO CHANGE ME! VVVVVVV
		  writer.println(SettingsMenuUI.cheats);
		  writer.println(MusicPlayer.getCurrentSongID());
		  writer.println(MusicPlayer.getCurrentVolume());
		  writer.println(SettingsMenuUI.freeForm);
		  writer.close();
	  }catch(IOException e){
		  e.printStackTrace();
	  }
	  
  }
  
  private class BackRequest implements Runnable{

		@Override
		public void run() {
			setRunning(false);
		}

	}

}
