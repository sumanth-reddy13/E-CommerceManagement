package com.example.supplychainmanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.PopupWindow;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class signUpPageController {

    @FXML
    TextField email;
    @FXML
    TextField userName;
    @FXML
    TextField userType;
    @FXML
    TextField pwd;
    @FXML
    PasswordField RePwd;

    @FXML
    public void signUp() throws IOException, SQLException
    {
        ResultSet res = HelloApplication.connection.executequery("select * from users");

        Dialog<String> dialog = new Dialog<>();
        ButtonType type = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.setTitle("sign Up");
        if(!pwd.getText().toLowerCase().equals(RePwd.getText().toLowerCase()))
        {
            dialog.setContentText("Oops! Passwords do not match! Retry.");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
            return;
        }

        boolean flag = false;
        while(res.next())
        {
            if(res.getString("emailId").toLowerCase().equals(email.getText().toLowerCase()))
            {
                flag = true;
                dialog.setContentText("the email Id already exists. Try another One");
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
                return;
            }

        }

        if(!flag && email.getText().length()>1) {
            System.out.println("im in flag");
            String query = String.format("Insert into users values ('%s','%s','%s',%s)", email.getText(), userName.getText(), userType.getText(), pwd.getText());
            int response = 0;
            response = HelloApplication.connection.executeupdate(query);
            if (response > 0) {
                dialog.setContentText("account created");
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
                Header header = new Header();
                AnchorPane pane = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));

                HelloApplication.root.getChildren().addAll(header.root, pane);
            }
        }else{
            dialog.setContentText("enter your details");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        }
    }
    @FXML
    public void back() throws IOException, SQLException {
        ProductPage product = new ProductPage();
        Header header = new Header();
        ListView productlist = product.getProducts();
        AnchorPane productPane = new AnchorPane();
        productPane.getChildren().addAll(productlist);
        productPane.setLayoutX(100);
        productPane.setLayoutY(100);
        HelloApplication.root.getChildren().clear();
        HelloApplication.root.getChildren().addAll(header.root, productPane);
    }
}
