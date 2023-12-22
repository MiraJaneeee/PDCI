package student.manager.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCTools {
    private static DataSource ds;
    private static ThreadLocal<Connection> tl = new ThreadLocal<>();
    static{
        try {
            Properties pro = new Properties();
            pro.load(ClassLoader.getSystemResourceAsStream("druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(pro);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
         Connection connection = tl.get();
         if(connection  == null){
             connection = ds.getConnection();
             tl.set(connection);
         }
         return connection;
    }

    public static void free() throws SQLException {
        Connection connection = tl.get();
        if(connection != null){
            tl.remove();
            connection.setAutoCommit(true);
            connection.close();
        }
    }
}