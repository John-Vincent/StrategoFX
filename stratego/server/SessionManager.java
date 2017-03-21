package stratego.server;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager{

  private static ConcurrentHashMap<Integer, Session> map = new ConcurrentHashMap<Integer, Session>();
  private static int id = 1;

  public static int newSession(SocketAddress add, byte[] key){
    while(map.get(id) != null){
      id++;
    }
    map.put(id, new Session(add, key));
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

  public static void removeUName(int id){
    Session session = map.get(id);
    session.username = null;
  }

  public static boolean isSession(int id, SocketAddress address){
    if(map.get(id) == null || !map.get(id).getAddress().equals(address)){
      return false;
    }
    return true;
  }

  public static SocketAddress getAddress(int id){
    return map.get(id).address;
  }

  public static byte[] getRSA(int id){
    return map.get(id).getRSA();
  }

  private static class Session{
    private String username = null;
    private SocketAddress address;
    private byte[] RSAKey;

    public Session(SocketAddress add, byte[] r){
      this.RSAKey = r;
      this.address = add;
    }

    public String getName(){
      return username;
    }

    public SocketAddress getAddress(){
      return address;
    }

    public byte[] getRSA(){
      return RSAKey;
    }

  }

}
