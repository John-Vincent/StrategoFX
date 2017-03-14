package stratego.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.sql.*;
import java.io.IOException;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
*Class that handles Packets.
*/
public class PacketHandler implements Runnable{

  private DatagramPacket packet;
  private DatagramSocket socket;
  private static SessionManager manager = new SessionManager();

  private static final byte PING = (byte)0x00;
  private static final byte SIGNUP = (byte)0x01;
  private static final byte LOGIN = (byte)0x02;
  private static final byte FRIENDQ = (byte)0x03;
  private static final byte FRIENDR =(byte)0x04;
  private static final byte LOGOUT =(byte)0x05;


	/**
	*PacketHandler Constructor
	*@param	p         the incoming packet
	*@param	s         the socket
	*@author Collin Vincent collinvincent96@gmail.com
	*/
  public PacketHandler(DatagramPacket p, DatagramSocket s){
    this.packet = p;
    this.socket = s;
  }

  /**
   * decodes the packet and responds accordingly
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-09T20:28:58+000
   */
  public void run(){
    int id;

    String temp;
    String[] temp2;

    byte[] data = this.packet.getData();

    byte type = data[0];

    data = Arrays.copyOfRange(data, 1, data.length);
    byte[] newdata = null;


    switch(type){
      case PING:
        newdata = new byte[]{PING};
        System.out.println("got PING");
        break;
      case SIGNUP:
        temp = new String(data, 0, this.packet.getLength()-257, StandardCharsets.UTF_8);
        newdata = signup( temp, Arrays.copyOfRange(data, temp.length(), data.length));
        System.out.println("got SIGNUP");
        break;
      case LOGIN:
        temp = new String(data, 0, this.packet.getLength()-257, StandardCharsets.UTF_8);
        newdata = login(temp, Arrays.copyOfRange(data, temp.length(), data.length));
        System.out.println("got LOGIN");
        break;
      case FRIENDQ:
        id = data[0] << 24 | data[1] << 16 | data[2] << 8 | data[3];
        newdata = friendq(id);
        System.out.println("got FRIENDQ");
        break;
      case FRIENDR:
        temp = new String(data, 4, this.packet.getLength()-1, StandardCharsets.UTF_8);
        id = data[0] << 24 | data[1] << 16 | data[2] << 8 | data[3];
        newdata = friendr(id, temp);
        System.out.println("got FRIENDR");
        break;
      case LOGOUT:
        return;
      default:
        return;
    }

    try{
      if(newdata != null)
        this.socket.send(new DatagramPacket(newdata, newdata.length, this.packet.getSocketAddress()));
    } catch(IOException e){
      e.printStackTrace();
    }

  }

  private byte[] signup(String username, byte[] password){
    if(DBManager.signup(username, password)){
      return new byte[]{SIGNUP, (byte)0x01};
    } else{
      return new byte[]{SIGNUP, (byte)0x00};
    }
  }

  private byte[] login(String username, byte[] password){
    if( DBManager.login(username, password)){
      int id = SessionManager.newSession(username, this.packet.getSocketAddress());
      return new byte[]{LOGIN, (byte) (id & 0xff000000), (byte) (id & 0x00ff0000), (byte) (id & 0x0000ff00), (byte) (id & 0x000000ff), (byte)0x01};
    } else{
      return new byte[]{LOGIN, (byte)0x00};
    }
  }

  private byte[] friendq(int id){
    String username = SessionManager.getUName(id, this.packet.getSocketAddress());
    byte[] temp = DBManager.getFriends(username).getBytes();
    if(temp == null){
      return new byte[]{FRIENDQ};
    }
    byte[] data = new byte[temp.length+1];
    data[0] = FRIENDQ;
    for(int i = 0; i<temp.length; i++){
      data[i+1] = temp[i];
    }
    return data;
  }

  private byte[] friendr(int user, String friend){
    String username = SessionManager.getUName(user, this.packet.getSocketAddress());
    if(username == null){
      return new byte[]{FRIENDR, (byte)0x00};
    }
    String ans = DBManager.requestFriend( username, friend);
    if(ans != null){
      byte[] temp = ans.getBytes();
      byte[] val = new byte[temp.length+1];
      val[0] = FRIENDR;
      for(int i = 0; i<temp.length; i++){
        val[i+1] = temp[i];
      }
      return val;
    } else{
      return new byte[]{FRIENDR, (byte)0x00};
    }
  }

  private byte[] logout(int id){
    SessionManager.removeSession(id);
    return null;
  }

}
