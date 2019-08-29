package dao;

import configuration.ConfigurationFile;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbConnection {
    private static   Connection connect;
    // TODO: 8/27/19 try out multitons here for connections



    public static Connection getConnection() throws Exception {

            // This will load the MySQL driver, each DB has its own driver
            //Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setUrl("jdbc:mysql://localhost:3306/"+ConfigurationFile.getInstance().getDATABASE());
        poolProperties.setDriverClassName("com.mysql.cj.jdbc.Driver");
        poolProperties.setUsername(ConfigurationFile.getInstance().getDB_USER_NAME());
        poolProperties.setPassword(ConfigurationFile.getInstance().getDB_PASSWORD());
        //poolProperties.setJmxEnabled(true);
        //poolProperties.setTestOnBorrow(true);
        //poolProperties.setValidationQuery("SELECT 1"); //connection object validation
        poolProperties.setValidationInterval(30000);
        //poolProperties.setTimeBetweenEvictionRunsMillis(30000);
        poolProperties.setMaxActive(100);
        poolProperties.setInitialSize(50); // initial size of the pool
        poolProperties.setMaxWait(10000);
      /*  poolProperties.setRemoveAbandonedTimeout(60);
        poolProperties.setMinEvictableIdleTimeMillis(30000);
        poolProperties.setMinIdle(10);
        poolProperties.setLogAbandoned(true);
        poolProperties.setRemoveAbandoned(true);
        poolProperties.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
                "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");*/
        DataSource datasource = new DataSource();
        datasource.setPoolProperties(poolProperties);


        Connection connect = datasource.getConnection();
            return connect;
        }


}