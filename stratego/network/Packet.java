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
    byte[] data = SecurityManager.decrypt(p.getData());
    this.type = data[0];
    this.data = Arrays.copyOfRange(data, p.getOffset()+1, p.getLength());
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

  /**
   * returns an encrypted datagram packet from the info stored in this packet object
   * @return
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-03-19T13:33:44+000
   */
  public DatagramPacket getPacket(){
    DatagramPacket p;
    if(this.data == null){
      byte[] data = new byte[5];
    } else{
      byte[] data = new byte[this.data.length + 5];
    }
    int id = Networker.getID();

    data[0] = (byte) (id >> 24);
    data[1] = (byte) (id >> 16);
    data[2] = (byte) (id >> 8);
    data[3] = (byte) (id);
    data[4] = type;

    if(this.data != null){
      for(int i = 0; i<this.data.length; i++){
        data[i+1] = this.data[i];
      }
    }

    data = SecurityManager.encrypt(data);
    p = new DatagramPacket(data, data.length, address);

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

}
