package stratego.network;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.net.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class Networker implements Runnable{

  public static final InetSocketAddress server = new InetSocketAddress("proj-309-sg-1.cs.iastate.edu", 8092);
  public InetSocketAddress host;

  private ConcurrentLinkedQueue<DatagramPacket> received;
  private DatagramSocket socket;


  private static final int packetSize = 576;

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
  public static final byte SECURE = (byte)0x06;
  public static final byte CHAT = (byte)0xFE;
  public static final byte VOICE = (byte)0xFF;

  private int id;



  public Networker(DatagramSocket s){
    this.socket = s;
  }

  public void run(){
    /*byte[] data = SecurityManager.securePacket();

    DatagramPacket p = new DatagramPacket(data, data.length, server);
    try{
      this.socket.send(p);
      p = new DatagramPacket(new byte[packetSize], packetSize);
      this.socket.receive(p);
      data = p.getData();
      SecurityManager.verifySecurity(data);
    } catch(Exception e){
      System.out.println(e.getMessage());
    }
    */
    p = new DatagramPacket(new byte[packetSize], packetSize);
    this.received = new ConcurrentLinkedQueue<DatagramPacket>();

    while(!Thread.currentThread().isInterrupted()){
      try{
        this.socket.receive(p);
      } catch(IOException e){
        break;
      }
      this.received.add(p);
      p = new DatagramPacket(new byte[packetSize], packetSize);
    }

  }

  /**
   * this will be called from other threads to actually send a packet
   * @param	p             [description]
   * @return                         [description]
   * @author  Collin Vincent  collinvincent96@gmail.com
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

  public Boolean sendData(byte[] data){
    try{
      DatagramPacket packet = new DatagramPacket(data, data.length, server);
      this.socket.send(packet);
      System.out.println("Packet sent to " + packet.getSocketAddress());
    } catch(IOException e){
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * returns a Networker object that handles sending all the packets, as well as receiving all the packets and passing them into the
   * Packet queue
   * @return [description]
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-14T15:39:43+000
   */
  public DatagramPacket getPacket(){
    if(this.received.isEmpty()){
      return null;
    }
    return this.received.poll();
  }

  /**
   * send a login packet to the server
   * @param                 username      the username entered by the client
   * @param                 password      the password entered by the client
   * @return                         true if the packet was sent, false if the packet couldn't be sent
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-14T15:40:39+000
   */
  public Boolean login(String username, String password){
      byte[] data = new byte[username.length()+257];
      byte[] temp = username.getBytes();
      byte[] pass = SecurityManager.hashBytes(password.getBytes(StandardCharsets.UTF_8));
      data[0] = LOGIN;
      for(int i = 0; i< temp.length; i++){
        data[i+1] = temp[i];
      }
      for(int i = 0; i < 256; i++){
        data[temp.length + 1 + i] = pass[i];
      }
      DatagramPacket p = new DatagramPacket(data, data.length, Networker.server);
      return sendPacket(p);
  }

  /**
   * sends a logout packet to the server
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-14T15:51:07+000
   */
  public void logout(){
    byte[] data = new byte[1];
    data[0] = LOGOUT;
    DatagramPacket p = new DatagramPacket(data, data.length, Networker.server);
    sendPacket(p);
  }

  /**
   * sends a signup packet to the server
   * @param  username username the client wants to use
   * @param  password the password the client wants to use
   * @return
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-14T15:51:27+000
   */
  public Boolean signup(String username, String password){
      byte[] data = new byte[username.length()+257];
      byte[] temp = username.getBytes();
      byte[] pass = SecurityManager.hashBytes(password.getBytes(StandardCharsets.UTF_8));
      data[0] = SIGNUP;
      for(int i = 0; i<temp.length; i++){
    	  data[i+1] = temp[i];
      }
      for(int i = 0; i < 256; i++){
        data[temp.length + 1 + i] = pass[i];
      }
      DatagramPacket p = new DatagramPacket(data, data.length, Networker.server);
      return sendPacket(p);
  }

  /**
   * pings the server
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-14T15:52:15+000
   */
  public void ping(){
    byte[] data = {(byte)0x00};
    DatagramPacket p = new DatagramPacket(data, data.length, Networker.server);
    sendPacket(p);
    System.out.println("sent packet to " + Networker.server);
  }

  /**
   * sends a packet to the server that request the server to find all the friends
   * of the current logged in user
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-14T15:52:40+000
   */
  public void requestFriendsList(){
    byte[] data = new byte[5];
    data[0] = FRIENDQ;
    data[1] = (byte) (0xff000000 & this.id);
    data[2] = (byte) (0x00ff0000 & this.id);
    data[3] = (byte) (0x0000ff00 & this.id);
    data[4] = (byte) (0x000000ff & this.id);
    DatagramPacket p = new DatagramPacket(data, data.length, Networker.server);
    sendPacket(p);
  }

  /**
   * sends a packet to the server that request the user with the following username is
   * send a friend request from the currently logged in user
   * @param  username the user to be sent a friend request
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-14T15:53:16+000
   */
  public void sendFriendRequest(String username){
    byte[] data = new byte[username.length() + 5];
    data[0] = FRIENDR;
    data[1] = (byte) (0xff000000 & this.id);
    data[2] = (byte) (0x00ff0000 & this.id);
    data[3] = (byte) (0x0000ff00 & this.id);
    data[4] = (byte) (0x000000ff & this.id);
    String temp = username;
    byte[] temp2 = temp.getBytes();
    for(int i = 0; i< temp2.length; i++){
      data[i+5] = temp2[i];
    }
    DatagramPacket p = new DatagramPacket(data, data.length, Networker.server);
    sendPacket(p);
  }

  /**
   * sets the session id for the current client, this will be used by the server to identify the clients messages for the remaineder of the time the client is logged in
   * @param  id           the id of the currently logged in client
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-14T15:54:15+000
   */
  public void setID(int id){
    this.id = id;
  }


}
