package com.example.supplychainmanagement;
import java.io.IOException;
import java.sql.*;          // step 1 - import the package

// Establishing the connection b/w Java Application and Database.

public class DatabaseConnection {

    String SQLURL = "jdbc:mysql://localhost:3306/supplychain?useSSL=false";          // URL of the Database.
    String user = "root";       
    String password = "sumanth13";
    Connection con = null;           // Here Connection is an interface. We use Class "DriverManager" to get create the obj of Connection. In line 16, getConnection is                                      // the static method.
    DatabaseConnection()
    {
        try {
            con = DriverManager.getConnection(SQLURL, user, password);        // Step 2 - Establishing the connection.
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
            Statement statement = con.createStatement();                   // Step 4 - Create the statement
            res = statement.executeQuery(query);                           // Step 5 - Execute the Query.
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
