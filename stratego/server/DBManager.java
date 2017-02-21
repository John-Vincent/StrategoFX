package stratego.server;

import java.sql.*;
import org.apache.commons.dbcp2.BasicDataSource;

//can you also add a column to the user table thats a boolean value for if they are online or not
public final class DBManager{

  private static final BasicDataSource source = new BasicDataSource();
  private final String signupQ = "insert into `user` (`name`, `pass`) values ( ? , ? )";
  private final String loginQ = "select `user`.`pass` from `user` where `user`.`name` = ?";
  private final String getFriendsQ = "SELECT u.name, u.last "+
                              "FROM user u "+
                              "WHERE (u.id = (select f.sender from friend f where f.receiver =(select u1.id from user u1 where u1.name = ? ) and f.accepted != 0)) "+
                                "or (u.id = (select f.receiver from friend f where f.sender =(select u2.id from user u2 where u2.name = ? ) and f.accepted !=0));";

  private final String requestFriendQ = "insert into friend(sender,receiver,accepted) "+
                                  "values((select u.id from user u where u.name= ? ),(select u2.id from user u2 where u2.name = ? ),'0') this is the query for friend request";

  private final String acceptFriendRequestQ= "update friend set accepted = '1' where friend.id = ?";

  private final String logoutQ = "";

  static{
    source.setDriverClassName("com.mysql.jdbc.Driver");
    source.setUrl("jdbc:mysql://mysql.cs.iastate.edu:3306/db309sg1");
    source.setUsername("dbu309sg1");
    source.setPassword("M2EyMTIwYmEz");
  }

  public static Connection getConnection() throws SQLException{
    return source.getConnection();
  }


  public static boolean signup(String uname, String password){
    String temp = signupQ;
    temp = temp.replaceFirst("?", uname);
    temp = temp.replaceFirst("?", password);
    try{
      Connection conn1 = DBManager.getConnection();
      Statement statement  = conn1.createStatement();
      int i = statement.executeUpdate(temp);
      conn1.commit();
      statement.close();
      conn1.close();
      if(i == 0)
        return false;
      else
        return true;
    } catch(Exception e){
      System.out.println(e.getMessage());
    }
    return false;

  }

  public static boolean login(String uname, String password){

  }

  /**
   * this runs a query to return the name and online status of every user that has a friend relationship with the current user. if the request as not been accepted then the second value
   * should be request instead of online status
   * @param  String                  uname         the current user
   * @return                         String[][]    this should be a x by 2 array where x is the number of friends
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-02-21T16:47:48+000
   */
  public static String[][] getFriends(String uname){

  }

  /**
   * this makes a request that will create a new row in the friend table with the user's id as the first element and the friends id as the second and the accepted value as false
   * @param  String                  user          [description]
   * @param  String                  friend        [description]
   * @return                         [description]
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-02-21T16:51:32+000
   */
  public static boolean requestFriend(String user, String friend){

  }

  /**
   * changes the online status of the user to false in the user table
   * @param  String                  username      [description]
   * @return                         [description]
   * @author Collin Vincent <collinvincent96@gmail.com>
   * @date   2017-02-21T16:53:46+000
   */
  public static boolean logout(String username){

  }



}
