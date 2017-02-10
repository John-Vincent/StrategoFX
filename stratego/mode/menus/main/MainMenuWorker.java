package stratego.mode.menus.main;


import stratego.network.Networker;

public class MainMenuWorker implements Runnable {

  private ConcurrentLinkedQueue<DatagramPacket> toSend;
  private ConcurrentLinkedQueue<DatagramPacket> received;

  public MainMenuWorker(Networker online){
      this.toSend = online.getSendQ();
      this.received = online.getRecieveQ();
  }

}
