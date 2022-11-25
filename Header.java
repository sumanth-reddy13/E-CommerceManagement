package com.example.supplychainmanagement;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Header {
    AnchorPane root;
    Header() throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("header.fxml"));
    }
}
