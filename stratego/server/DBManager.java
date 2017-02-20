package stratego.server;

import java.sql.*;
import org.apache.commons.dbcp2.BasicDataSource;


public final class DBManager{

  private static final BasicDataSource source = new BasicDataSource();

  static{
    source.setDriverClassName("com.mysql.jdbc.Driver");
    source.setUrl("jdbc:mysql://mysql.cs.iastate.edu:3306/db309sg1");
    source.setUsername("dbu309sg1");
    source.setPassword("M2EyMTIwYmEz");
  }

  public static Connection getConnection() throws SQLException{
    return source.getConnection();
  }

}
