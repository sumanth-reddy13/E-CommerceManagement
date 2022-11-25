package com.example.supplychainmanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HeaderController {
    @FXML
    Button loginButton;
    @FXML
    Label email;
    @FXML
    TextField search;


    @FXML
    public void initialize()
    {
        if(!HelloApplication.email.equals(""))
        {
            loginButton.setOpacity(0);
            email.setText(HelloApplication.email);
        }
    }
    @FXML
    public void search() throws SQLException,IOException
    {
        Header header = new Header();
        ProductPage product = new ProductPage();
        ListView<HBox> products = product.getProductsOnName(search.getText());
        AnchorPane productlist = new AnchorPane();
        productlist.setLayoutX(100);
        productlist.setLayoutY(100);
        productlist.getChildren().add(products);
        HelloApplication.root.getChildren().clear();
        HelloApplication.root.getChildren().addAll(header.root, productlist);

    }
    @FXML
    public void login() throws IOException
    {
        AnchorPane start = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        HelloApplication.root.getChildren().addAll(start);
    }

    @FXML
    public void myCart() throws SQLException,IOException
    {
        if(HelloApplication.email.equals(""))
        {
            Dialog dialog = new Dialog();
            ButtonType button = new ButtonType("OK",ButtonBar.ButtonData.OK_DONE);
            dialog.setTitle("Mycart");
            dialog.setContentText("please login first");
            dialog.getDialogPane().getButtonTypes().add(button);
            dialog.showAndWait();
        }else {
            CartPage product = new CartPage();
            Header header = new Header();
            ListView<HBox> productlist = product.getCartList();
            AnchorPane productPane = new AnchorPane();
            productPane.getChildren().addAll(productlist);
            productPane.setLayoutX(100);
            productPane.setLayoutY(100);
            HelloApplication.root.getChildren().clear();
            HelloApplication.root.getChildren().addAll(productPane);
        }
    }
    @FXML
    public void logout() throws IOException
    {
        if(HelloApplication.email.equals(""))
        {
            Dialog dialog = new Dialog();
            ButtonType button = new ButtonType("OK",ButtonBar.ButtonData.OK_DONE);
            dialog.setTitle("Login");
            dialog.setContentText("please login first");
            dialog.getDialogPane().getButtonTypes().add(button);
            dialog.showAndWait();
        }
        else {
            email.setText("");
            HelloApplication.email = "";
            login();
        }
//        Header header = new Header();
//        AnchorPane start = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
//
//
//        HelloApplication.root.getChildren().addAll(header.root,start);
    }
}
