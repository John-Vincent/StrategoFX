package stratego.mode.multiplayer;

import stratego.network.Networker;
import stratego.network.Packet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class HostManager implements Runnable{

  private ArrayList<connection> users;
  private LinkedList<connection> play;
  private connection player1;
  private connection player2;
  private Boolean inGame = false;
  private Boolean player1Turn = true;
  private Boolean turnNeeded = true;

  public HostManager(){
    users = new ArrayList<connection>();
    play = new LinkedList<connection>();
  }

  public void add(SocketAddress address, String uname){
    users.add(new connection(address, uname));
  }

  public void queue(SocketAddress address, String uname){
    int i;
    int j;

    for(i = 0; i < users.size(); i++){
      if(users.get(i).add.equals(address) && users.get(i).Username.equals(uname)){
        for(j = 0; j < play.size(); j++){
          if(users.get(i).compare(play.get(j))){
            return;
          }
        }
        play.add(users.get(i));
        return;
      }
    }
  }

  public void remove(SocketAddress address){
    Iterator<connection> it;
    connection temp;

    it = users.iterator();

    while(it.hasNext()){
      temp = it.next();
      if(address.equals(temp.add)){
        it.remove();
      }
    }
  }

  public void sendPacket(Packet p){
    Iterator<connection> it;
    connection temp;
    SocketAddress source = p.getAddress();

    it = users.iterator();

    while(it.hasNext()){
      temp = it.next();
      if(source == null || !source.equals(temp.add)){
        p.setAddress(temp.add);
        if(temp.add != null && !Networker.sendPacket(p)){
          it.remove();
        }
      }
    }
  }

  public void closeServer(){
    sendPacket(new Packet(Networker.CLOSERV, null, null));
  }

  public void turnReceived(){
    turnNeeded = true;
  }

  public void run(){
    if(!inGame){
      if(play.size() >= 2){
        inGame = true;
        player1 = play.pop();
        player2 = play.pop();
        player1Turn = true;
        turnNeeded = true;
      }
    }
    if(inGame){
      if(turnNeeded){
        if(player1Turn){
          Networker.sendPacket(new Packet(Networker.TURN, null, player1.add));
        } else{
          Networker.sendPacket(new Packet(Networker.TURN, null, player2.add));
        }
        turnNeeded = false;
      }
    }
  }

  private class connection{
    public SocketAddress add;
    public String Username;

    public connection(SocketAddress add, String username){
      this.add = add;
      this.Username = username;
    }

    public boolean compare(connection e){
      return (add.equals(e.add) && Username.equals(e.Username));
    }
  }

}
