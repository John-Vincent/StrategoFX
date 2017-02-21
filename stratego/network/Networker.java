package stratego.network;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.net.*;
import java.io.IOException;

public class Networker implements Runnable{

  public InetSocketAddress server = new InetSocketAddress("proj-309-sg-1.cs.iastate.edu", 8092);
  public InetSocketAddress host;

  private ConcurrentLinkedQueue<DatagramPacket> received;
  private DatagramSocket socket;


  public static final byte PING = (byte)0x00;
  public static final byte SIGNUP = (byte)0x01;
  public static final byte LOGIN = (byte)0x02;
  public static final byte FRIENDQ = (byte)0x03;
  public static final byte FRIENDR = (byte)0x04;
  public static final byte CHAT = (byte)0xFE;
  public static final byte VOICE = (byte)0xFF;


  public Networker(DatagramSocket s){
    this.socket = s;
  }

  public void run(){

    DatagramPacket p = new DatagramPacket(new byte[1024], 1024);
    this.received = new ConcurrentLinkedQueue<DatagramPacket>();

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

  public Boolean login(String username, String password){
      String temp = username + ";" + password;
      byte[] data = new byte[temp.length()+1];
      byte[] temp2 = temp.getBytes();
      data[0] = LOGIN;
      for(int i = 0; i< temp2.length; i++){
        data[i+1] = temp2[i];
      }
      DatagramPacket p = new DatagramPacket(data, data.length, this.server);
      return sendPacket(p);
  }

  public Boolean signup(String username, String password){
      String temp;
      return false;
  }

  public void ping(){
    byte[] data = {(byte)0x00};
    DatagramPacket p = new DatagramPacket(data, data.length, this.server);
    sendPacket(p);
    System.out.println("sent packet to " + this.server);
  }

  public void requestFriendsList(String username){

  }

  public void sendFriendRequest(String username, String friend){

  }


}
