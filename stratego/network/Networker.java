package stratego.network;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.net.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class Networker implements Runnable{

  public static final InetSocketAddress server = new InetSocketAddress("proj-309-sg-1.cs.iastate.edu", 8092);
  public static InetSocketAddress host;

  private ConcurrentLinkedQueue<DatagramPacket> received;
  private static DatagramSocket socket;

  private static boolean online;

  byte[] memes = {(byte)0x04, (byte)0x14, (byte)0x45};

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
  public static final byte CLOSERV = (byte)0x0B;
  public static final byte CHAT = (byte)0xF0;
  public static final byte JOINSERV = (byte)0xF1;
  public static final byte LEAVESERV = (byte)0xF2;
  public static final byte GAMEDATA = (byte)0xF3;
  public static final byte TURN = (byte)0xF4;
  public static final byte DISCONNECT = (byte)0xF5;


  private static int id;
  public static String username;



  public Networker(DatagramSocket s){
    Networker.socket = s;
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
        Networker.socket.receive(p);
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
  public static Boolean sendPacket(Packet p){
    try{
      Networker.socket.send(p.getPacket());
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
      Networker.socket.send(packet);
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
      Networker.username = username;
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

  /**
   * activates a server with a given name as long as the name is not taken by another user, or the server name has already been claimed by this user
   * @param  name         Name of the server
   * @param  password     Password for other users to be able to connect to the server
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-04-13T10:24:53+000
   */
  public void setPrivateServer(String name, String password){
    byte[] data;
    byte[] pass;
    byte[] n;
    pass = SecurityManager.hashBytes(password.getBytes(StandardCharsets.UTF_8));
    n = name.getBytes();
    data = new byte[n.length + pass.length];

    for(int i = 0; i < n.length; i++){
      data[i] = n[i];
    }

    for(int i  = 0; i < pass.length; i++){
      data[i + n.length] = pass[i];
    }

    sendPacket(new Packet(OPENSERV, data, Networker.server));
  }

  /**
   * connects to the host for a game session
   * @param  ip        the SocketAddress of the host to attempt to connect to
   * @param  publicKey byte[] containing the contents of the public rsa key used by the host to encrypt messages
   * @return
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-04-15T00:54:44+000
   */
  public boolean connectToHost(byte[] data){
    byte[] key = Arrays.copyOfRange(data, 0, SecurityManager.X509SIZE);
    byte[] SAdd = Arrays.copyOfRange(data, SecurityManager.X509SIZE, data.length-4);
    int port = ((data[data.length-4] << 24) & 0xff000000);
    port = port | (data[data.length-3] << 16 & 0x00ff0000);
    port = port | (data[data.length -2] << 8 & 0x0000ff00);
    port = port | (data[data.length - 1] & 0x000000ff);
    byte[] freshMemes = Arrays.copyOf(memes, memes.length + username.length());
    int j = 0;
    for(int i = memes.length; i < freshMemes.length; i++){
    	freshMemes[i] = (byte) username.charAt(j);
    	j++;
    }
    try{
      Networker.host = new InetSocketAddress(InetAddress.getByAddress(SAdd), port);
    } catch(Exception e){
      e.printStackTrace();
    }
    if(SecurityManager.addHostKey(key)){
      if(!Networker.host.isUnresolved()){
        Networker.sendPacket(new Packet( Networker.JOINSERV, freshMemes, Networker.host));
        return true;
      }
    }
    return false;
  }

  /**
   * atemps to connect to a server with the given Server name and password
   * @param  name           name of the server to connect to
   * @param  password       password to user for the connection attempt
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-04-13T10:27:23+000
   */
  public void connectPrivateServer(String name, String password){
    byte[] data;
    byte[] pass;
    byte[] n;
    pass = SecurityManager.hashBytes(password.getBytes(StandardCharsets.UTF_8));
    n = name.getBytes();
    data = new byte[n.length + pass.length];

    for(int i = 0; i < n.length; i++){
      data[i] = n[i];
    }

    for(int i  = 0; i < pass.length; i++){
      data[i + n.length] = pass[i];
    }

    sendPacket(new Packet(CONSERV, data, Networker.server));
  }

  public void closeServer(String name, String password){
    byte[] data;
    byte[] pass;
    byte[] n;
    pass = SecurityManager.hashBytes(password.getBytes(StandardCharsets.UTF_8));
    n = name.getBytes();
    data = new byte[n.length + pass.length];

    for(int i = 0; i < n.length; i++){
      data[i] = n[i];
    }

    for(int i  = 0; i < pass.length; i++){
      data[i + n.length] = pass[i];
    }

    sendPacket(new Packet(CLOSERV, data, Networker.server));
  }

  public void clearHost(){
    if(host != null){
      sendPacket(new Packet(LEAVESERV, memes, host));
    }
    Networker.host = null;
    SecurityManager.removeHostKey();
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
      Networker.socket.setSoTimeout(10000);
      p = new DatagramPacket(new byte[packetSize], packetSize);
      while(i != 0 && i < 100){
        Networker.socket.send(secure);
        System.out.println("attempting connection to server");
        try{
          Networker.socket.receive(p);
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
      Networker.socket.setSoTimeout(0);
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
