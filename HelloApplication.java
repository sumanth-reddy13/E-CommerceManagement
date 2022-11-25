package com.example.supplychainmanagement;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloApplication extends Application {
    public static String email;
    public static DatabaseConnection connection;
    public static Group root;
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        connection = new DatabaseConnection();
        email ="";
        root = new Group();
        Header header = new Header();
        ProductPage product = new ProductPage();
        ListView productlist = product.getProducts();
        AnchorPane productPane = new AnchorPane();
        productPane.getChildren().addAll(header.root,productlist);
        productlist.setLayoutX(100);
        productlist.setLayoutY(100);
        root.getChildren().add(productPane);
        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("SupplyChainManagement");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {
            try {
                HelloApplication.connection.con.close();
                System.out.println("connection to the database is closed");
            }catch (Exception ei){
                ei.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}