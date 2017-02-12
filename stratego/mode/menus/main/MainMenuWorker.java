package stratego.mode.menus.main;

import java.util.concurrent.ConcurrentLinkedQueue;
import stratego.network.Networker;
import java.net.*;
import stratego.mode.ModeWorker;

public class MainMenuWorker implements ModeWorker {

  private ConcurrentLinkedQueue<DatagramPacket> toSend;
  private ConcurrentLinkedQueue<DatagramPacket> received;

  public MainMenuWorker(){

  }

  public void run(){

  }

  public void setQueues(Networker online){
    this.toSend = online.getSendQ();
    this.received = online.getRecieveQ();
  }

}
