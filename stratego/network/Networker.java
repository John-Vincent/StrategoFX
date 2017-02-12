package stratego.network;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.net.*;

public class Networker implements Runnable{

  private ConcurrentLinkedQueue<DatagramPacket> toSend;
  private ConcurrentLinkedQueue<DatagramPacket> received;

  public Networker(){

  }

  public void run(){

  }

  public ConcurrentLinkedQueue<DatagramPacket> getSendQ(){
    return this.toSend;
  }

  public ConcurrentLinkedQueue<DatagramPacket> getRecieveQ(){
    return this.received;
  }


}
