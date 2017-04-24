package stratego.mode.multiplayer;

import stratego.network.Networker;
import stratego.network.Packet;

import java.util.ArrayList;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class HostManager implements Runnable{

  private ArrayList<SocketAddress> users;

  public HostManager(){
    users = new ArrayList<SocketAddress>();
  }

  public void addUser(String ip){
    String[] s = ip.split(":");
    InetSocketAddress newUser = new InetSocketAddress(s[0], Integer.parseInt(s[1]));
    users.add(newUser);
  }

  public void removeUser(String ip){
    Iterator<SocketAddress> it;
    SocketAddress temp;

    it = users.iterator();

    while(it.hasNext()){
      temp = it.next();
      if(ip.equals(temp.toString())){
        it.remove();
      }
    }
  }

  public void sendPacket(Packet p){
    Iterator<SocketAddress> it;
    SocketAddress temp;

    it = users.iterator();

    while(it.hasNext()){
      temp = it.next();
      if(!Networker.sendPacket(p)){
        it.remove();
      }
    }
  }

}
