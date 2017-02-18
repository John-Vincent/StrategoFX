package stratego.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.IOException;

public class PacketHandler implements Runnable{

  private DatagramPacket packet;
  private DatagramSocket socket;

  public PacketHandler(DatagramPacket p, DatagramSocket s){
    this.packet = p;
    this.socket = s;
  }

  public void run(){
    byte[] data = this.packet.getData();
    byte type = data[0];
    byte[] newdata = null;
    DatagramPacket response = null;
    switch(type){
      case (byte)0x00:
        newdata = new byte[1];
        newdata[0] = packageType.PING.getByte();
        response = new DatagramPacket(newdata, newdata.length, this.packet.getSocketAddress());
        break;
      case (byte)0x01:

        break;
      case (byte)0x02:

        break;
      case (byte)0x03:

        break;
    }

    try{
      if(response != null)
        this.socket.send(response);
    } catch(IOException e){
      e.printStackTrace();
    }

  }


  public enum packageType {
    PING((byte)0x00),
    SIGNUP((byte)0x01),
    LOGIN((byte)0x02),
    FRIEND((byte)0x03);

    private final byte id;

    packageType(byte a){
      this.id = a;
    }

    public byte getByte(){
      return this.id;
    }
  }


}
