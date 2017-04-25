package stratego.mode.multiplayer;

import stratego.network.Networker;
import stratego.network.Packet;

import java.util.ArrayList;
import java.util.Iterator;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class HostManager{

  private ArrayList<SocketAddress> users;

  public HostManager(){
    users = new ArrayList<SocketAddress>();
  }

  public void add(SocketAddress address){
    users.add(address);
  }

  public void remove(SocketAddress address){
    Iterator<SocketAddress> it;
    SocketAddress temp;

    it = users.iterator();

    while(it.hasNext()){
      temp = it.next();
      if(address.equals(temp)){
        it.remove();
      }
    }
  }

  public void sendPacket(Packet p){
    Iterator<SocketAddress> it;
    SocketAddress temp;
    SocketAddress source = p.getAddress();

    it = users.iterator();

    while(it.hasNext()){
      temp = it.next();
      if(!source.equals(temp)){
        p.setAddress(temp);
        if(!Networker.sendPacket(p)){
          it.remove();
        }
      }
    }
  }

}
