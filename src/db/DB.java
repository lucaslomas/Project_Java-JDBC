package db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    public static Connection conn = null;

    public static Connection getConnection(){
        if(conn == null){
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
            }
            catch (SQLException e){
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }
    public static Connection closeConnection(){
        if (conn != null){
            try{
                conn.close();
            }
            catch (SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return conn;
    }


    private static Properties loadProperties(){
        try(FileInputStream fs = new FileInputStream("db.properties")){
            Properties props = new Properties();
            props.load(fs);
            return props;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    public static Statement closeStatement(Statement st){
        if(st != null){
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return st;
    }
    public static ResultSet closeResultSet(ResultSet rs){
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return rs;
    }
}
