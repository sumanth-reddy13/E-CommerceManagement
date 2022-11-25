package com.example.supplychainmanagement;


import java.sql.ResultSet;
import java.sql.SQLException;

public class Orders {

    void orders(String productId) throws SQLException
    {
        ResultSet res = HelloApplication.connection.executequery("select max(orderId) from orders");
        int orderId = 0;

        if(res.next())
            orderId = res.getInt("max(orderId)")+1;

        String query = String.format("Insert into orders values (%s,%s,'%s')",orderId,productId,HelloApplication.email);
        int response = 0;
        response = HelloApplication.connection.executeupdate(query);
        if(response>0){
            System.out.println("your order is successful");
        }
    }
}

