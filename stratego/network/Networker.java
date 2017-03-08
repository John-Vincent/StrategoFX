package stratego.network;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.net.*;
import java.io.IOException;

public class Networker implements Runnable{

  public static final InetSocketAddress server = new InetSocketAddress("proj-309-sg-1.cs.iastate.edu", 8092);
  public InetSocketAddress host;

  private ConcurrentLinkedQueue<DatagramPacket> received;
  private DatagramSocket socket;

  /**
   * byte that identifies a Ping Packet
   */
  public static final byte PING = (byte)0x00;

  /**
   * byte that Identifies a Sign up Packet
   */
  public static final byte SIGNUP = (byte)0x01;

  /**
   * byte that identifies a Login Packet
   */
  public static final byte LOGIN = (byte)0x02;

  /**
   * byte that identifies a friend query Packet
   */
  public static final byte FRIENDQ = (byte)0x03;

  /**
   * byte that identifies a friend request Packet
   */
  public static final byte FRIENDR = (byte)0x04;
  public static final byte LOGOUT =(byte)0x05;
  public static final byte CHAT = (byte)0xFE;
  public static final byte VOICE = (byte)0xFF;

  private String id;


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
      System.out.println("Packet sent to " + p.getSocketAddress());
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
      this.id = username;
      String temp = username + ";" + password;
      byte[] data = new byte[temp.length()+1];
      byte[] temp2 = temp.getBytes();
      data[0] = LOGIN;
      for(int i = 0; i< temp2.length; i++){
        data[i+1] = temp2[i];
      }
      DatagramPacket p = new DatagramPacket(data, data.length, Networker.server);
      return sendPacket(p);
  }

  public void logout(){
    byte[] data = new byte[1];
    data[0] = LOGOUT;
    DatagramPacket p = new DatagramPacket(data, data.length, Networker.server);
    sendPacket(p);
  }

  public Boolean signup(String username, String password){
      this.id = username;
      String temp = username + ";" + password;
      byte[] data = new byte[temp.length()+1];
      byte[] temp2 = temp.getBytes();
      data[0] = SIGNUP;
      for(int i = 0; i<temp2.length; i++){
    	  data[i+1] = temp2[i];
      }
      DatagramPacket p = new DatagramPacket(data, data.length, Networker.server);
      return sendPacket(p);
  }

  public void ping(){
    byte[] data = {(byte)0x00};
    DatagramPacket p = new DatagramPacket(data, data.length, Networker.server);
    sendPacket(p);
    System.out.println("sent packet to " + Networker.server);
  }

  public void requestFriendsList(){
    byte[] data = new byte[this.id.length() + 1];
    data[0] = FRIENDQ;
    byte[] temp = this.id.getBytes();
    for(int i = 0; i< temp.length; i++){
      data[i+1] = temp[i];
    }
    DatagramPacket p = new DatagramPacket(data, data.length, Networker.server);
    sendPacket(p);
  }

  public void sendFriendRequest(String username){
    byte[] data = new byte[this.id.length() + username.length() + 2];
    data[0] = FRIENDR;
    String temp = this.id + ";" + username;
    byte[] temp2 = temp.getBytes();
    for(int i = 0; i< temp2.length; i++){
      data[i+1] = temp2[i];
    }
    DatagramPacket p = new DatagramPacket(data, data.length, Networker.server);
    sendPacket(p);
  }


}
