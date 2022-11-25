package com.example.supplychainmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductPage {
    ListView<HBox> products;

    ListView<HBox> getProductsOnName(String search) throws IOException, SQLException
    {
        ObservableList<HBox> productsList = FXCollections.observableArrayList();
        ResultSet res = HelloApplication.connection.executequery("Select * from product");
        products = new ListView<>();

        Label pName = new Label();
        Label pId = new Label();
        Label pPrice = new Label();
        HBox hboxx = new HBox();

        pName.setText("Name");
        pId.setText("Id");
        pPrice.setText("price");
        pName.setMinWidth(70);
        pId.setMinWidth(50);
        pName.setMinWidth(50);

        hboxx.getChildren().addAll(pName, pId, pPrice);
        productsList.addAll(hboxx);

        boolean flag = false;

        while(res.next()) {
            if (res.getString("productName").toLowerCase().contains(search.toLowerCase())) {
                flag = true;
                Label Name = new Label();
                Label Id = new Label();
                Label price = new Label();
                HBox hbox = new HBox();
                Button add = new Button();
                add.setText("Buy");

                add.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        Dialog<String> dialog = new Dialog<>();
                        ButtonType type = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                        dialog.setTitle("Login");
                        if (HelloApplication.email.equals("")) {
                            dialog.setContentText("please login to continue");
                        } else {
                            Orders orders = new Orders();
                           // CartDB cart = new CartDB();
                            try {
                                //cart.addToCart(Name.getText(),Id.getText(),price.getText());
                                orders.orders(Id.getText());
                                dialog.setContentText("your order is successful");
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
                    }
                });
                Name.setText(res.getString("productName"));
                Id.setText("" + res.getInt("productId"));
                price.setText("" + res.getInt("price"));

                Name.setMinWidth(70);
                Id.setMinWidth(50);
                price.setMinWidth(50);

                hbox.getChildren().addAll(Name, Id, price, add);
                productsList.addAll(hbox);
            }
        }
        if(!flag)
        {
                Dialog<String> dialog = new Dialog<>();
                ButtonType type = new ButtonType("OK",ButtonBar.ButtonData.OK_DONE);
                dialog.setTitle("search result");
                dialog.setContentText("No product is available for your search.");
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
                return getProducts();
        }
        products.setItems(productsList);
        return products;
    }
    ListView<HBox> getProducts() throws IOException, SQLException
    {
        ObservableList<HBox> productsList = FXCollections.observableArrayList();

        ResultSet res = HelloApplication.connection.executequery("Select * from product");
        products = new ListView<>();

        Label pName = new Label();
        Label pId = new Label();
        Label pPrice = new Label();
        HBox hboxx = new HBox();


        pName.setText("Name");
        pId.setText("Id");
        pPrice.setText("price");
        pName.setMinWidth(70);
        pId.setMinWidth(50);
        pName.setMinWidth(50);

        hboxx.getChildren().addAll(pName, pId, pPrice);
        productsList.addAll(hboxx);

        while(res.next())
        {
            Label Name = new Label();
            Label Id = new Label();
            Label price = new Label();
            HBox hbox = new HBox();
            Button buy = new Button();
            buy.setText("buy");

            buy.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    Dialog<String> dialog = new Dialog<>();
                    ButtonType type = new ButtonType("OK",ButtonBar.ButtonData.OK_DONE);
                    dialog.setTitle("Login");
                    if(HelloApplication.email.equals(""))
                    {
                        dialog.setContentText("please login to continue");
                    }else{

                        Orders orders = new Orders();
                        CartDB cart = new CartDB();
                        try {
                            orders.orders(Id.getText());
                            cart.addToCart(Name.getText(),Id.getText(),price.getText());
                            dialog.setContentText("item added to the cart");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    dialog.getDialogPane().getButtonTypes().add(type);
                    dialog.showAndWait();
                }
            });
            Name.setText(res.getString("productName"));
            Id.setText(""+res.getInt("productId"));
            price.setText(""+res.getInt("price"));

            Name.setMinWidth(70);
            Id.setMinWidth(50);
            price.setMinWidth(50);

            hbox.getChildren().addAll(Name, Id, price,buy);
            productsList.addAll(hbox);
        }
        products.setItems(productsList);
        return products;
    }
}
