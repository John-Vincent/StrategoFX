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

  private static boolean online;

  private static final int HASHLENGTH = 32;


  private static final int packetSize = 1024;

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
  public static final byte CLOSE = (byte)0x07;
  public static final byte OPENSERV = (byte)0x08;
  public static final byte CONSERV = (byte)0x09;
  public static final byte SESSERROR = (byte)0x0A;
  public static final byte CHAT = (byte)0xFE;


  private static int id;



  public Networker(DatagramSocket s){
    this.socket = s;
    this.received = new ConcurrentLinkedQueue<DatagramPacket>();
  }

  public void run(){
    DatagramPacket p;

    online = connectToServer();

    if(!online){
      System.out.println("Could not connect to server");
    }else{
      System.out.println("connected to server");
    }

    p = new DatagramPacket(new byte[packetSize], packetSize);

    while(!Thread.currentThread().isInterrupted() && online){
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
  public Boolean sendPacket(Packet p){
    try{
      this.socket.send(p.getPacket());
      System.out.println(p.getTypeString() + " Packet sent to " + p.getSocketAddress());
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
  public Packet getPacket(){
    if(this.received.isEmpty()){
      return null;
    }
    return new Packet(this.received.poll());
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
      byte[] data = new byte[username.length() + HASHLENGTH];
      byte[] temp = username.getBytes();
      byte[] pass = SecurityManager.hashBytes(password.getBytes(StandardCharsets.UTF_8));
      System.out.println(username + " " + new String(pass, 0, pass.length, StandardCharsets.UTF_8));
      for(int i = 0; i< temp.length; i++){
        data[i] = temp[i];
      }
      for(int i = 0; i < HASHLENGTH; i++){
        data[temp.length + i] = pass[i];
      }
      Packet p = new Packet(LOGIN, data, server);
      return sendPacket(p);
  }

  /**
   * sends a logout packet to the server
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-14T15:51:07+000
   */
  public void logout(){
    Packet p = new Packet(LOGOUT, null, Networker.server);
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
      byte[] data = new byte[username.length()+ HASHLENGTH];
      byte[] temp = username.getBytes();
      byte[] pass = SecurityManager.hashBytes(password.getBytes(StandardCharsets.UTF_8));
      for(int i = 0; i<temp.length; i++){
    	  data[i] = temp[i];
      }
      for(int i = 0; i < HASHLENGTH; i++){
        data[temp.length + i] = pass[i];
      }
      Packet p = new Packet(SIGNUP, data, Networker.server);
      return sendPacket(p);
  }

  /**
   * pings the server
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-14T15:52:15+000
   */
  public void ping(){
    Packet p = new Packet(PING, null, server);
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
    Packet p = new Packet(FRIENDQ, null, Networker.server);
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
    Packet p = new Packet(FRIENDR, username.getBytes(), Networker.server);
    sendPacket(p);
  }

  public void setPrivateServer(String name, String password){

  }

  public void connectPrivateServer(String name, String password){

  }

  /**
   * sets the session id for the current client, this will be used by the server to identify the clients messages for the remaineder of the time the client is logged in
   * @param  id           the id of the currently logged in client
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-14T15:54:15+000
   */
  protected static void setID(int id){
    Networker.id = id;
  }

  /**
   * returns the session id for the current client
   * @return
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-19T21:54:57+000
   */
  protected static int getID(){
    return Networker.id;
  }


  private boolean connectToServer(){
    DatagramPacket secure;
    DatagramPacket p;
    byte[] data = SecurityManager.securePacket();
    int i = 1;
    boolean ans = false;

    secure = new DatagramPacket(data, data.length, server);
    try{
      this.socket.setSoTimeout(10000);
      p = new DatagramPacket(new byte[packetSize], packetSize);
      while(i != 0 && i < 100){
        this.socket.send(secure);
        System.out.println("attempting connection to server");
        try{
          this.socket.receive(p);
          i = 0;
        } catch(SocketTimeoutException e){
          i++;
        }
      }
      if(1 > 99){
        return false;
      }
      Packet packet = new Packet(p);
      ans = SecurityManager.makeSecure(packet);
      this.socket.setSoTimeout(0);
    } catch(Exception e){
      System.out.println(e.getMessage());
    }
     return ans;
  }

  public void endSession(){
    Packet p = new Packet(CLOSE, null, Networker.server);
    sendPacket(p);
  }

}
