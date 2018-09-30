package com.doublej.mathtestmvc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
     Stage mainStage;
    @Override
    public void start(Stage primaryStage) throws Exception {

        mainStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Main Menu.fxml"));
        Scene scene = new Scene(root, 850, 600);
        mainStage.setTitle("Math Test");
        mainStage.setScene(scene);
        mainStage.show();
    }
}
