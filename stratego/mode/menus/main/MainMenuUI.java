package stratego.mode.menus.main;

import javafx.scene.Scene;


public class MainMenuUI extends Scene implements Mode{

  private Thread worker;

  /**
   * this should start the worker thread
   * @param  Networker               online this is the object that communicates through the internet we only need it to get the concurrent queues to the worker thread
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-02-10T19:40:32+000
   */
  public void startWorker(Networker online){
    worker = new Thread(new MainMenuWorker(online));
    worker.start();
  }

  /**
   * this should call interupt on the worker thread, the worker thread needs to check if it has been interupted every loop and if it has it should break the loop and terminate.
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-02-10T19:43:21+000
   */
  public void terminate(){

  }

}
