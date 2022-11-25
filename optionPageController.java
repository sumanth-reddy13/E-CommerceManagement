package com.example.supplychainmanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;

public class optionPageController {

    @FXML
    public void buy() throws IOException, SQLException
    {
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

    @FXML
    public void sell() throws IOException
    {
        AnchorPane start = FXMLLoader.load(getClass().getResource("sellerPage.fxml"));
        HelloApplication.root.getChildren().addAll(start);
    }

}
