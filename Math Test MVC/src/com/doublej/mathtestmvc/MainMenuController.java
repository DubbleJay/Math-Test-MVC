package com.doublej.mathtestmvc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class MainMenuController {
    @FXML
    Button additionModeButton;

    @FXML
    Button subtractionModeButton;

    @FXML
    Button multiplicationModeButton;

    @FXML
    Button divisionModeButton;

    @FXML
    ComboBox<Integer> numberOfQuestionsComboBox;

    int gameType;

    public void initialize () {
        for(int i = 5; i <= 25; i = i + 5)
            numberOfQuestionsComboBox.getItems().add(i);
        numberOfQuestionsComboBox.setValue(5);
    }

    public void startGame (ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UI.fxml"));
        Parent gameSceneParent = loader.load();

        Controller controller = loader.getController();
        if(event.getSource().equals(additionModeButton))
            controller.setModel(0, numberOfQuestionsComboBox.getValue());
        else if (event.getSource().equals(subtractionModeButton))
            controller.setModel(1,numberOfQuestionsComboBox.getValue() );
        else if (event.getSource().equals(multiplicationModeButton))
            controller.setModel(2, numberOfQuestionsComboBox.getValue() );
        else if (event.getSource().equals(divisionModeButton))
            controller.setModel(3, numberOfQuestionsComboBox.getValue());

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene gameScene = new Scene(gameSceneParent, window.getWidth(), window.getHeight());

        window.setScene(gameScene);

    }


}
