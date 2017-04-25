package stratego.server;

import java.sql.*;
import org.apache.commons.dbcp2.BasicDataSource;
import java.util.ArrayList;
import java.util.Arrays;

/**
*The DB manager class contains variables and methods for database queries and interactions.
*/
public final class DBManager{

  private static final BasicDataSource source = new BasicDataSource();
  private static final String signupQ = "insert into `user` (`name`, `pass`, `last`, `online`) values ( ? , ? , NOW(), 0);";

  //changed to fit new data table
  private static final String getFriendsQ = "SELECT u.name, u.online from `user` u " +
                                            "inner join `friends` f on u.id = f.friendid " +
                                            "where f.userid = (select u2.id from `user` u2 where u2.name =?);";

//added string for finding your sent friend requests
  private static final String getSentFriendRequestsQ = "SELECT u.name, u.online from `user` u " +
                                            "inner join `friendrequests` f on u.id = f.receiverid " +
                                            "where f.senderid = (select u2.id from `user` u2 where u2.name =?);";

											//changed friend requests to fit new table
  private static final String requestFriendQ = "insert into `friendrequests`(`senderid`,`receiverid`) "+
                                  "values((select u.id from `user` u where u.name= ? ),(select u2.id from `user` u2 where u2.name = ? ));";

								  //accepting friend requests requires a pair of insertions to friends and a deletion from friendrequests
  private static final String acceptFriendRequestQ= "insert into `friends` ('userid','friendid') values ( ? , ?);";

  private static final String friendRequestAcceptedQ= "delete from `friendrequests` where senderid = ? and receiverid = ?";


  private static final String logoutQ = "update `user` set `online` = 0, `last` = NOW() where `name` = ?; ";

  private static final String setLoginQ = "update `user` set `online` = 1 where `name` = ? and `pass` = ?";

  //query to set the active value of a server to 1/true, and the sessionID to a given int, base on the given name and password
  private static final String activateServer = "update `server` s set `active` = 1, `sessid` = ? where s.name = ? and s.password = ?;";

  //query to add a server into the database with a given server name, password(32 bytes), and sessionID(int)
  private static final String makeServer = "insert into `server`(`name`,`password`,`sessid`, `active`) values ( ? , ?, ?, 1);";

  //query to return the server sessionID(int)
  private static final String findServer = "select s.sessionid from `server` s where s.name = ? and s.password = ? and s.active = 1;";

  //query to set the "active" value of the server to 0/false
  private static final String deactivateServer = "update `server` s set `active` = 0 where s.name = ? and password = ?;";

  static{
    source.setDriverClassName("com.mysql.jdbc.Driver");
    source.setUrl("jdbc:mysql://mysql.cs.iastate.edu:3306/db309sg1");
    source.setUsername("dbu309sg1");
    source.setPassword("M2EyMTIwYmEz");
  }

  /**
  *Gets the database connection info.
  *@return Connection 	this is the database connection info.
  * @author  Collin Vincent  collinvincent96@gmail.com
  */
  public static Connection getConnection() throws SQLException{
    return source.getConnection();
  }

	/**
	*This method runs a query that adds a new user to the users table.
	*@param	uname         the new user
	*@param	password      the desired password
	*@return Boolean		True or false based on whether the name is available.
	* @author  Collin Vincent  collinvincent96@gmail.com
	*/
  public static boolean signup(String uname, byte[] password){
    Connection conn1 = null;
    PreparedStatement statement = null;
    boolean ans =false;
    String temp = signupQ;

    try{
      conn1 = DBManager.getConnection();
      statement  = conn1.prepareStatement(signupQ);
      statement.setString(1, uname);
      statement.setBytes(2, password);
      int i = statement.executeUpdate();
      if(i == 0){
        ans = false;
      }
      else{
        System.out.println("update worked");
        ans = true;
      }
    } catch(Exception e){
      System.out.println(e.getMessage());
      ans = false;
    } finally{

      if(conn1 != null){
        try{conn1.close();} catch(Exception e){ }
      }

      if(statement != null){
        try{statement.close();} catch(Exception e) { }
      }

    }

    return ans;
  }

	/**
	*This runs a query to see if the submitted username and password match a user in the users table.
	*@param	uname         the submitted username
	*@param	password      the submitted password
	*@return Boolean		True or false based on whether the username and password match a user from the table.
	* @author  Collin Vincent  collinvincent96@gmail.com
	*/
  public static boolean login(String uname, byte[] password){
    Connection conn1 = null;
    PreparedStatement statement = null;
    int rows = 0;
    ResultSet set = null;

    boolean ans=false;

    try{
      conn1 = DBManager.getConnection();
      statement  = conn1.prepareStatement(setLoginQ);
      statement.setString(1, uname);
      statement.setBytes(2, password);
      rows = statement.executeUpdate();
      if(rows == 1){
        ans = true;
      }

    } catch(Exception e){

      ans = false;

    } finally{

      if( set != null){
        try{set.close();} catch(Exception e){ }
      }

      if(conn1 != null){
        try{conn1.close();} catch(Exception e){ }
      }

      if(statement != null){
        try{statement.close();} catch(Exception e) { }
      }
    }

    return ans;
  }

  /**
   * this runs a query to return the name and online status of every user that has a friend relationship with the current user. if the request as not been accepted then the second value
   * should be request instead of online status
   * @param	uname         the current user
   * @return                         String[][]    this should be a x by 2 array where x is the number of friends
   * @author  Collin Vincent  collinvincent96@gmail.com
   * @date   2017-02-21T16:47:48+000
   */
  public static String getFriends(String uname){
    Connection conn1 = null;
    PreparedStatement statement = null;
    ResultSet set = null;

    String ans="";
    String temp = getFriendsQ;

    try{
      conn1 = DBManager.getConnection();
      statement  = conn1.prepareStatement(temp);
      statement.setString(1, uname);
      set = statement.executeQuery();
      while(set.next()){
        ans += set.getString("name") + ":" + set.getInt("online")+";";
      }
    } catch(Exception e){
      System.out.println(e.getMessage());
      ans = null;
    } finally{

      if( set != null){
        try{set.close();} catch(Exception e){ }
      }

      if(conn1 != null){
        try{
          conn1.close();
        } catch(Exception e){ }
      }

      if(statement != null){
        try{
          statement.close();
        } catch(Exception e) { }
      }
    }

    return ans;
  }

  /**
   * this makes a request that will create a new row in the friend table with the user's id as the first element and the friends id as the second and the accepted value as false
   * @param	user          [description]
   * @param	friend        [description]
   * @return                         [description]
   * @author  Collin Vincent  collinvincent96@gmail.com
   * @date   2017-02-21T16:51:32+000
   */
  public static String requestFriend(String user, String friend){
    Connection conn1 = null;
    PreparedStatement statement = null;

    String temp = requestFriendQ;
    int i = 0;

    try{
      conn1 = DBManager.getConnection();
      statement  = conn1.prepareStatement(requestFriendQ);
      statement.setString(1, user);
      statement.setString(2, friend);
      i = statement.executeUpdate();

    } catch(Exception e){
      System.out.println(e.getMessage());
      friend = null;
    } finally{
      if(conn1 != null){
        try{
          conn1.close();
        } catch(Exception e){ }
      }
      if(statement != null){
        try{
          statement.close();
        } catch(Exception e) { }
      }
    }

    return friend;
  }

  /**
   * changes the online status of the user to false in the user table
   * @param	username      [description]
   * @return                         [description]
   * @author  Collin Vincent  collinvincent96@gmail.com
   * @date   2017-02-21T16:53:46+000
   */
  public static boolean logout(String username){
    Connection conn1 = null;
    PreparedStatement statement = null;
    boolean ans = false;

    String temp = logoutQ;

    try{
      conn1 = DBManager.getConnection();
      statement = conn1.prepareStatement(logoutQ);
      statement.setString(1, username);
      ans = statement.executeUpdate() != 0;
    } catch(Exception e){
      System.out.println(e.getMessage());
      ans = false;
    } finally{
      if(conn1 != null){
        try{
          conn1.close();
        } catch(Exception e){ }
      }
      if(statement != null){
        try{
          statement.close();
        } catch(Exception e) { }
      }
    }

    return ans;
  }

  /**
   * activates a server in the database, if the activation fails because the server doesnt exist then it
   * enters the server into the database with the active value being true
   * @param  name     name of the server
   * @param  password [description]
   * @return
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-04-12T00:04:08+000
   */
  public static boolean setServer(String name, byte[] password, int sessionID){
    Connection conn1 = null;
    PreparedStatement statement = null;
    boolean ans = false;

    String temp = logoutQ;

    try{
      conn1 = DBManager.getConnection();
      statement = conn1.prepareStatement(activateServer);
      statement.setInt(3, sessionID);
      statement.setString(1, name);
      statement.setBytes(2, password);
      ans = statement.executeUpdate() != 0;
      if(!ans){
        statement = conn1.prepareStatement(makeServer);
        statement.setString(1, name);
        statement.setBytes(2, password);
        statement.setInt(3, sessionID);
        ans = statement.executeUpdate() != 0;
      }
    } catch(Exception e){
      System.out.println(e.getMessage());
      ans = false;
    } finally{
      if(conn1 != null){
        try{
          conn1.close();
        } catch(Exception e){ }
      }
      if(statement != null){
        try{
          statement.close();
        } catch(Exception e) { }
      }
    }

    return ans;
  }

  /**
   * queries the database for a server with a given name
   * if the server exist then it returns the ip address associated with the server
   * as well as the public key for the server
   * @param  name         name of the server
   * @param  password     optional password as a SHA-256 encoded byte array
   * @return
   * @author Collin Vincent collinvincent96@gmail.com
   * @date   2017-04-12T00:05:59+000
   */
  public static int getServer(String name, byte[] password){
    Connection conn1 = null;
    PreparedStatement statement = null;
    ResultSet set = null;

    int ans = -1;

    try{

      conn1 = DBManager.getConnection();
      statement  = conn1.prepareStatement(findServer);
      statement.setString(1, name);
      statement.setBytes(2, password);
      set = statement.executeQuery();
      set.next();
      ans = set.getInt("session");

    } catch(Exception e){
      System.out.println(e.getMessage());
      ans = -1;
    } finally{

      if( set != null){
        try{set.close();} catch(Exception e){ }
      }

      if(conn1 != null){
        try{
          conn1.close();
        } catch(Exception e){ }
      }

      if(statement != null){
        try{
          statement.close();
        } catch(Exception e) { }
      }
    }

    return ans;
  }

  public static void closeServer(String name, byte[] password){
    Connection conn1 = null;
    PreparedStatement statement = null;

    try{

      conn1 = DBManager.getConnection();
      statement  = conn1.prepareStatement(deactivateServer);
      statement.setString(1, name);
      statement.setBytes(2, password);
      statement.executeUpdate();
    } catch(Exception e){
      System.out.println(e.getMessage());
    } finally{
      if(conn1 != null){
        try{
          conn1.close();
        } catch(Exception e){ }
      }

      if(statement != null){
        try{
          statement.close();
        } catch(Exception e) { }
      }
    }
  }


}
