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

public class CartPage {
    ListView<HBox> cartList;

    ListView<HBox> getCartList() throws IOException, SQLException
    {
        ObservableList<HBox> productsList = FXCollections.observableArrayList();
        Label MyCart = new Label();
        HBox myBox = new HBox();
        myBox.setAlignment(Pos.TOP_RIGHT);
        myBox.getChildren().addAll(MyCart);
        productsList.addAll(myBox);

        String query = String.format("select * from cart where emailId = '%s'",HelloApplication.email);
        ResultSet res = HelloApplication.connection.executequery(query);
        cartList = new ListView<>();

        Label pName = new Label();
        Label pId = new Label();
        Label pPrice = new Label();
        Button back = new Button();
        Button buy = new Button();
        HBox hboxx = new HBox();

        buy.setText("buy");
        back.setText("Back");
        pName.setText("Name");
        pId.setText("Id");
        pPrice.setText("price");

        pName.setMinWidth(70);
        pId.setMinWidth(50);
        pName.setMinWidth(50);
        back.setMinWidth(50);

        buy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                String query = String.format("select sum(price) from cart where emailId = '%s'",HelloApplication.email);
                ResultSet res = HelloApplication.connection.executequery(query);
                int sum = 0;
                try {
                    if(res.next()) {

                        sum = res.getInt("sum(price)");
                        Dialog d = new Dialog();
                        ButtonType b = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                        d.setTitle("Order");
                        if(sum == 0){
                            d.setContentText("Cart is empty. Add items to your cart");

                        }else {
                            d.setContentText("Order is successful. Your cart value is " + sum);
                        }
                        d.getDialogPane().getButtonTypes().addAll(b);
                        d.showAndWait();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        // when the user wants to go back
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                ProductPage product = new ProductPage();
                Header header = null;
                try {
                    header = new Header();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ListView productlist = null;
                try {
                    productlist = product.getProducts();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                AnchorPane productPane = new AnchorPane();
                productPane.getChildren().addAll(productlist);
                productPane.setLayoutX(100);
                productPane.setLayoutY(100);
                HelloApplication.root.getChildren().clear();
                HelloApplication.root.getChildren().addAll(header.root, productPane);
            }
        });

        hboxx.getChildren().addAll(pName, pId, pPrice, back, buy
        );
        productsList.addAll(hboxx);

        while(res.next())
        {
            Label Name = new Label();
            Label Id = new Label();
            Label price = new Label();
            Button delete = new Button();

            HBox hbox = new HBox();

            delete.setText("delete");
            Name.setText(res.getString("productName"));
            Id.setText(""+res.getInt("productId"));
            price.setText(""+res.getInt("price"));
            int prId = res.getInt("productId");

            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String query = String.format("delete from cart where productId = %s",prId);
                    int response = HelloApplication.connection.executeupdate(query);
                    if(response > 0)
                    {
                        Dialog d = new Dialog();
                        ButtonType b = new ButtonType("OK",ButtonBar.ButtonData.OK_DONE);
                        d.setTitle("item deleted");
                        d.setContentText("product is deleted from the cart");
                        d.getDialogPane().getButtonTypes().addAll(b);
                        d.showAndWait();
                        Name.setOpacity(0);
                        Id.setOpacity(0);
                        price.setOpacity(0);
                        delete.setOpacity(0);
                    }
                }
            });

            Name.setMinWidth(70);
            Id.setMinWidth(50);
            price.setMinWidth(50);

            hbox.getChildren().addAll(Name, Id, price, delete);
            productsList.addAll(hbox);
        }
        cartList.setItems(productsList);
        return cartList;
    }
}
