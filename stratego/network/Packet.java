package stratego.network;

import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.util.Arrays;

public class Packet{

  private byte type;
  private byte[] data;
  private SocketAddress address;

  /**
   * creates a useable Packet object from an encrypted datagram packet picked up off the socket
   * @param  p the encrypted datagram packet
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-19T13:31:28+000
   */
  public Packet(DatagramPacket p){
    byte[] data = SecurityManager.decrypt(Arrays.copyOfRange(p.getData(), p.getOffset(), p.getLength()));
    this.type = data[0];
    this.data = Arrays.copyOfRange(data, 1, data.length);
    this.address = p.getSocketAddress();
  }

  /**
   * stores the info for an outgoing datagram Packet
   * @param  type        the packet type
   * @param  data        the data the packet is going to contain
   * @param  destination the location the packet is to be sent to
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-19T13:32:12+000
   */
  public Packet(byte type, byte[] data, SocketAddress destination){
    this.type = type;
    this.data = data;
    this.address = destination;
  }


  public void setAddress(SocketAddress s){
    this.address = s;
  }

  /**
   * returns an encrypted datagram packet from the info stored in this packet object
   * @return
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-19T13:33:44+000
   */
  public DatagramPacket getPacket(){
    DatagramPacket p;
    byte[] packet;
    if(this.data == null){
      packet = new byte[5];
    } else{
      packet = new byte[this.data.length + 5];
    }
    int id = Networker.getID();

    packet[0] = (byte) (id >> 24);
    packet[1] = (byte) (id >> 16);
    packet[2] = (byte) (id >> 8);
    packet[3] = (byte) (id);
    packet[4] = type;

    if(this.data != null){
      for(int i = 0; i<this.data.length; i++){
        packet[i+5] = this.data[i];
      }
    }

    packet = SecurityManager.encrypt(packet);
    p = new DatagramPacket(packet, packet.length, address);

    return p;
  }

  public String getSocketAddress(){
    return this.address.toString();
  }

  public byte getType(){
    return this.type;
  }

  public byte[] getData(){
    return this.data;
  }

  public String getTypeString(){
    switch(this.type){
      case Networker.PING:
        return "PING";
      case Networker.SIGNUP:
        return "SIGNUP";
      case Networker.LOGIN:
        return "LOGIN";
      case Networker.FRIENDQ:
        return "FRIENDQ";
      case Networker.FRIENDR:
        return "FRIENDR";
      case Networker.LOGOUT:
        return "LOGOUT";
      case Networker.SECURE:
        return "SECURE";
      case Networker.CLOSE:
        return "CLOSE";
      case Networker.OPENSERV:
        return "OPENSERV";
      case Networker.CONSERV:
        return "CONSERV";
      case Networker.SESSERROR:
        return "SESSERROR";
      case Networker.CHAT:
        return "CHAT";
    }
      return "UNKNOWN";
  }

}
