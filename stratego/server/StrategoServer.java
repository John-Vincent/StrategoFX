package stratego.server;


import java.lang.ClassLoader;
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


/**
*Constructor for the Server that sets class variables
* @author  Collin Vincent  collinvincent96@gmail.com
*/
  public StrategoServer() throws IOException{
    this.socket = new DatagramSocket(8092);
    this.pool = Executors.newFixedThreadPool(10);
  }

  /**
  *creates and sends a packet.
  * @author  Collin Vincent  collinvincent96@gmail.com
  */
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
	/**
	*main method that creates and runs a server
	* @author  Collin Vincent  collinvincent96@gmail.com
	*/
  public static void main(String[] args){
    try{
      SecurityManager.load();
      ConnectionRequestManager.load();
      StrategoServer s = new StrategoServer();
      s.run();
    } catch(Exception e){
      e.printStackTrace();
      System.out.println(e.getMessage());
    }
  }


}
