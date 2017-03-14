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
  private static final String signupQ = "insert into `user` (`name`, `pass`) values ( ? , ? );";
  private static final String loginQ = "select `user`.`pass` from `user` where `user`.`name` = ?;";
  private static final String getFriendsQ = "SELECT u.name, u.online "+
                              "FROM user u "+
                              "WHERE (u.id = (select f.sender from friend f where f.receiver =(select u1.id from user u1 where u1.name = ? ) and f.accepted != 0)) "+
                                "or (u.id = (select f.receiver from friend f where f.sender =(select u2.id from user u2 where u2.name = ? ) and f.accepted !=0));";

  private static final String requestFriendQ = "insert into friend(sender,receiver,accepted) "+
                                  "values((select u.id from user u where u.name= ? ),(select u2.id from user u2 where u2.name = ? ),'0');";

  private static final String acceptFriendRequestQ= "update `friend` set `accepted` = '1' where `friend`.`id` = ?;";

  private static final String logoutQ = "update `user` set `online` = '0' where `name` = ?; ";

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
    String temp = signupQ;

    try{
      conn1 = DBManager.getConnection();
      statement  = conn1.prepareStatement(signupQ);
      statement.setString(1, uname);
      statement.setBytes(2, password);
      int i = statement.executeUpdate();
      statement.close();
      conn1.close();
      if(i == 0){
        return false;
      }
      else{
        System.out.println("update worked");
        return true;
      }
    } catch(Exception e){
      System.out.println(e.getMessage());
      return false;
    } finally{

      if(conn1 != null){
        try{conn1.close();} catch(Exception e){ }
      }

      if(statement != null){
        try{statement.close();} catch(Exception e) { }
      }

    }

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
    ResultSet set = null;

    String temp = loginQ;
    boolean ans=false;
    System.out.println(temp);

    try{
      conn1 = DBManager.getConnection();
      statement  = conn1.prepareStatement(loginQ);
      statement.setString(1, uname);
      set = statement.executeQuery();
      byte[] passbytes;
      while(set.next()){
        passbytes = set.getBytes("pass");
        if(Arrays.equals(passbytes, password)){
          ans = true;
        }
      }

    } catch(Exception e){

      return false;

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
    System.out.println(temp);

    try{
      conn1 = DBManager.getConnection();
      statement  = conn1.prepareStatement(getFriendsQ);
      statement.setString(1, uname);
      statement.setString(2, uname);
      set = statement.executeQuery();
      while(set.next()){
        ans += set.getString("name") + ":" + set.getInt("online")+";";
      }

    } catch(Exception e){
      System.out.println(e.getMessage());
      return null;
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
      return null;
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

    String temp = logoutQ;

    try{
      conn1 = DBManager.getConnection();
      statement = conn1.prepareStatement(logoutQ);
      statement.setString(1, username);
      statement.executeUpdate();

    } catch(Exception e){
      return false;
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

    return true;
  }



}
