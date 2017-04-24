
import java.net.*;


public class test{

  public static void main(String[] args){
    try{
      DatagramSocket socket = new DatagramSocket(8092);
      InetSocketAddress a = new InetSocketAddress("10.30.71.24", 8092);

      byte[] data = new byte[1024];
      DatagramPacket p = new DatagramPacket(data, 1024, a);

      socket.send(p);
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
