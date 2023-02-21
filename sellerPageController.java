package com.example.supplychainmanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class sellerPageController {
    @FXML
    TextField pName;
    @FXML
    TextField price;
    @FXML
    TextField email;

    @FXML
    public void add() throws SQLException
    {
        ResultSet res = HelloApplication.connection.executequery("Select max(productId) from product");  // Query executed and stored the result in ResultSet res.
        Dialog<String> dialog = new Dialog<>();
        ButtonType type = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.setTitle("Add products");
        int product = 0;
        if(res.equals("")){
            dialog.setContentText("please fill the details of the product before adding it.");
        }
        if(res.next()) {
            product = res.getInt("max(productId)") + 1;            // res.getInt("columnName") to get info from database. 
            String query = String.format("Insert into product values ('%s',%s,%s,'%s')", pName.getText(), product, price.getText(), email.getText());
            int response = HelloApplication.connection.executeupdate(query);
            if(response>0)
            {
                dialog.setContentText("Item added successfully!");
            }
        }
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }
    @FXML
    public void back() throws IOException
    {
        AnchorPane start = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Header header = new Header();
        HelloApplication.root.getChildren().clear();
        HelloApplication.root.getChildren().addAll(header.root, start);

    }
}
