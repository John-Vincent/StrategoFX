package stratego.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.sql.*;
import java.io.IOException;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;

/**
*Class that handles Packets.
*/
public class PacketHandler implements Runnable{

  private DatagramPacket packet;
  private DatagramSocket socket;


  public static final byte PING = (byte)0x00;
  public static final byte SIGNUP = (byte)0x01;
  public static final byte LOGIN = (byte)0x02;
  public static final byte FRIENDQ = (byte)0x03;
  public static final byte FRIENDR =(byte)0x04;
  public static final byte LOGOUT =(byte)0x05;

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
        temp = new String(data, 0, this.packet.getLength()-1, StandardCharsets.UTF_8);
        temp2 = temp.split(";");
        newdata = signup(temp2[0], temp2[1]);
        System.out.println("got SIGNUP");
        break;
      case LOGIN:
        temp = new String(data, 0, this.packet.getLength()-1, StandardCharsets.UTF_8);
        temp2 = temp.split(";");
        newdata = login(temp2[0], temp2[1]);
        System.out.println("got LOGIN");
        break;
      case FRIENDQ:
        temp = new String(data, 0, this.packet.getLength()-1, StandardCharsets.UTF_8);
        newdata = friendq(temp);
        System.out.println("got FRIENDQ");
        break;
      case FRIENDR:
        temp = new String(data, 0, this.packet.getLength()-1, StandardCharsets.UTF_8);
        temp2 = temp.split(";");
        newdata = friendr(temp2[0], temp2[1]);
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

  private byte[] signup(String username, String password){
    if(DBManager.signup(username, password)){
      return new byte[]{SIGNUP, (byte)0x01};
    } else{
      return new byte[]{SIGNUP, (byte)0x00};
    }
  }

  private byte[] login(String username, String password){
    if(DBManager.login(username, password)){
      System.out.println("sending 1");
      return new byte[]{LOGIN, (byte)0x01};
    } else{
      return new byte[]{LOGIN, (byte)0x00};
    }
  }

  private byte[] friendq(String username){
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

  private byte[] friendr(String user, String friend){
    String ans = DBManager.requestFriend(user, friend);
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

  private byte[] logout(){
      return null;
  }

}
