package com.example.supplychainmanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPageController {

    @FXML
    TextField email;
    @FXML
    PasswordField password;
    @FXML
    public void login() throws IOException, SQLException
    {
        String query = String.format("Select * from users where emailId='%s' and password='%s'",email.getText(),password.getText());
        ResultSet res = HelloApplication.connection.executequery(query);
        if(res.next())
        {
            HelloApplication.email = res.getString("emailId");
            AnchorPane pane = FXMLLoader.load(getClass().getResource("optionPage.fxml"));
            HelloApplication.root.getChildren().addAll(pane);

        }else{
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Login");
            ButtonType type = new ButtonType("OK",ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("Please enter a registered email id or password");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        }
    }

    @FXML
    public void signUp() throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("signUpPage.fxml"));
        HelloApplication.root.getChildren().add(pane);
    }
}
