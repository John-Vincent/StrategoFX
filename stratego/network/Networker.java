package stratego.network;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.net.*;
import java.io.IOException;

public class Networker implements Runnable{

  public InetSocketAddress server = new InetSocketAddress("proj-309-sr-5.cs.iastate.edu", 8092);
  public InetSocketAddress host;

  private ConcurrentLinkedQueue<DatagramPacket> received;
  private DatagramSocket socket;

  public enum packageType {
    PING((byte)0x00),
    SIGNUP((byte)0x01),
    LOGIN((byte)0x02),
    FRIEND((byte)0x03),
    CHAT((byte)0x04),
    VOICE((byte)0x05);



    private final byte id;

    packageType(byte a){
      this.id = a;
    }

    public byte getByte(){
      return this.id;
    }
  }

  public Networker(DatagramSocket s){
    this.socket = s;
  }

  public void run(){

    DatagramPacket p = new DatagramPacket(new byte[1024], 1024);

    while(!Thread.currentThread().isInterrupted()){
      try{
        this.socket.receive(p);
      } catch(IOException e){
        break;
      }
      this.received.add(p);
      p = new DatagramPacket(new byte[1024], 1024);
    }

  }

  /**
   * this will be called from other threads to actually send a packet
   * @param  DatagramPacket          p             [description]
   * @return                         [description]
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-02-15T20:24:47+000
   */
  public Boolean sendPacket(DatagramPacket p){
    try{
      this.socket.send(p);
    } catch(IOException e){
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public DatagramPacket getPacket(){
    if(this.received.isEmpty()){
      return null;
    }
    return this.received.poll();
  }


}
