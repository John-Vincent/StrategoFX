package stratego.server;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager{

  private static ConcurrentHashMap<Integer, Session> map = new ConcurrentHashMap<Integer, Session>();
  private static int id = 0;

  public static int newSession(String name, SocketAddress add){
    while(map.get(id) == null){
      id++;
    }
    map.put(id, new Session(name, add));
    return id;
  }

  public static void removeSession(int id){
    map.remove(id);
  }

  public static String getUName(int id, SocketAddress address){
    String username = map.get(id).username;
    SocketAddress session = map.get(id).address;
    if(username != null && session.equals(address))
      return map.get(id).username;
    return null;
  }

  public static SocketAddress getAddress(int id){
    return map.get(id).address;
  }

  private static class Session{
    private String username;
    private SocketAddress address;

    public Session(String name, SocketAddress add){
      this.username = name;
      this.address = add;
    }
  }

}
