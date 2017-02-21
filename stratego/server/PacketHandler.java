package stratego.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.sql.*;
import java.io.IOException;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;

public class PacketHandler implements Runnable{

  private DatagramPacket packet;
  private DatagramSocket socket;


  public static final byte PING = (byte)0x00;
  public static final byte SIGNUP = (byte)0x01;
  public static final byte LOGIN = (byte)0x02;
  public static final byte FRIENDQ = (byte)0x03;
  public static final byte FRIENDR =(byte)0x04;
  public static final byte LOGOUT =(byte)0x05;

  public PacketHandler(DatagramPacket p, DatagramSocket s){
    this.packet = p;
    this.socket = s;
  }

  public void run(){
    byte[] data = this.packet.getData();
    byte type = data[0];
    data = Arrays.copyOfRange(data, 1, data.length);
    byte[] newdata = null;
    switch(type){
      case PING:
        newdata = new byte[]{PING};
        break;
      case SIGNUP:
        String temp = new String(data, StandardCharsets.UTF_8);
        String[] temp2 = temp.split(";");
        if(signup(temp2[0], temp2[1])){
          newdata = new byte[]{SIGNUP, (byte)0x01};
        } else{
          newdata = new byte[]{SIGNUP, (byte)0x00};
        }
        break;
      case LOGIN:

        break;
      case FRIENDQ:

        break;
      case FRIENDR:

        break;
      case LOGOUT:

        break;
    }

    try{
      if(newdata != null)
        this.socket.send(new DatagramPacket(newdata, newdata.length, this.packet.getSocketAddress()));
    } catch(IOException e){
      e.printStackTrace();
    }

  }

  private boolean signup(String username, String password){
    try{
      Connection conn1 = DBManager.getConnection();
		  conn1.setAutoCommit(false);
      Statement statement  = conn1.createStatement();
      int i = statement.executeUpdate("insert into user(name, password) values ('"+username+"', '"+password+"')");
      statement.close();
      conn1.close();
      if(i == 0)
        return false;
      else
        return true;
    } catch(Exception e){
      e.printStackTrace();
      System.out.println(e.getMessage());
    }
    return false;
  }

  private boolean login(String username, String password){
    try{

    }
  }

}
