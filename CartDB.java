package com.example.supplychainmanagement;

import java.sql.ResultSet;

public class CartDB {

    public void addToCart(String pName, String pId, String price)
    {
        ResultSet res = HelloApplication.connection.executequery("select * from cart");
        String query = String.format("Insert into cart values ('%s',%s,%s,'%s')",pName, pId, price,HelloApplication.email);
        int response = HelloApplication.connection.executeupdate(query);
        if(response > 0){
            System.out.println("items added to the cart");
        }
    }
}
