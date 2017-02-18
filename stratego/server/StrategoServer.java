package stratego.server;

// Sports page burger, Anytime burger

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StrategoServer implements Runnable{

  private DatagramSocket socket;
  final private int packetSize = 1024;
  private final ExecutorService pool;


  public StrategoServer() throws IOException{
    this.socket = new DatagramSocket(8092);
    this.pool = Executors.newFixedThreadPool(10);
  }

  public void run(){
    DatagramPacket p = new DatagramPacket(new byte[packetSize], packetSize);
    while(!this.socket.isClosed()){
      try{
        this.socket.receive(p);
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

}
