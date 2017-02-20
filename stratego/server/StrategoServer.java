package stratego.server;

// Sports page burger, Anytime burger

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.sql.*;

public class StrategoServer implements Runnable{

  private DatagramSocket socket;
  final private int packetSize = 1024;
  private final ExecutorService pool;



  public StrategoServer() throws IOException{
    this.socket = new DatagramSocket(8092);
    this.pool = Executors.newFixedThreadPool(10);
    signup("CollinV", "collin123");
  }

  public void run(){
    DatagramPacket p = new DatagramPacket(new byte[packetSize], packetSize);
    while(!this.socket.isClosed()){
      try{
        this.socket.receive(p);
        System.out.println("Packet received from " + p.getSocketAddress());
        pool.execute(new PacketHandler(p, this.socket));
        p = new DatagramPacket(new byte[packetSize], packetSize);
      } catch(IOException e){
        break;
      }
    }
  }

  public static void main(String[] args){
    try{
      StrategoServer s = new StrategoServer();
      s.run();
    } catch(IOException e){
      e.printStackTrace();
    }
  }

  private boolean signup(String username, String password){
    try{
      Connection conn1 = DBManager.getConnection();
		  conn1.setAutoCommit(false);
      Statement statement  = conn1.createStatement();
      int i = statement.executeUpdate("insert into user(name, pass) values ('"+username+"', '"+password+"')");
      conn1.commit();
      statement.close();
      conn1.close();
      if(i == 0)
        return false;
      else
        return true;
    } catch(Exception e){
      System.out.println(e.getMessage());
    }
    return false;
  }

}
