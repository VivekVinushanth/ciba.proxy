package dao;

import configuration.ConfigurationFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbConnection {
    private static   Connection connect;
    // TODO: 8/27/19 try out multitons here for connections



    public static Connection getConnection() throws Exception {

            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            Connection connect;
        String url="jdbc:mysql://localhost:3306/"+ConfigurationFile.getInstance().getDATABASE();
        String name=ConfigurationFile.getInstance().getDB_USER_NAME();
       // String pass=ConfigurationFile.getInstance().getDB_PASSWORD();
        connect = DriverManager.getConnection(url,name,null);


            return connect;
        }


}