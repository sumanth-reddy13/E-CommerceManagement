package com.example.supplychainmanagement;
import java.io.IOException;
import java.sql.*;

public class DatabaseConnection {

    String SQLURL = "jdbc:mysql://localhost:3306/supplychain?useSSL=false";
    String user = "root";
    String password = "sumanth13";
    Connection con = null;
    DatabaseConnection()
    {
        try {
            con = DriverManager.getConnection(SQLURL, user, password);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        if(con != null)
        {
            System.out.println("connection to the database is established");
        }
    }
    public ResultSet executequery(String query)
    {
        ResultSet res = null;
        try {
            Statement statement = con.createStatement();
            res = statement.executeQuery(query);
            return res;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }
    public int executeupdate(String query)
    {
        int res = 0;
        try {
            Statement statement = con.createStatement();
            res = statement.executeUpdate(query);
            return res;
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }
}
