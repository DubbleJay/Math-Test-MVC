package com.doublej.mathtestmvc;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;


public class EndOfGameController {
    @FXML
    Label correctLabel;
    @FXML
    Label wrongLabel;
    @FXML
    Label questionTotalLabel;
    @FXML
    Label finalScoreLabel;
    @FXML
    Button backToMainMenuButton;
    @FXML
    ListView<String> gameSummaryListView;

    AudioClip endOfGameAudioClip = new AudioClip(this.getClass().getResource("EndOfGameSound.mp3").toString());

    public void setValues (int numberCorrect, int numberWrong, int totalQuestions, double finalScore, String[] gameSummary) {

        correctLabel.setText(correctLabel.getText().concat( " " + numberCorrect));
        wrongLabel.setText(wrongLabel.getText().concat( " " + numberWrong));
        questionTotalLabel.setText(questionTotalLabel.getText().concat( " " + totalQuestions));
        finalScoreLabel.setText(finalScoreLabel.getText().concat( " " + finalScore + "%"));
        gameSummaryListView.setItems(FXCollections.observableArrayList(gameSummary));
    }

    public void initialize () {
          endOfGameAudioClip.play();
    }

    public void backToMainMenu(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Main Menu.fxml"));
        Parent mainMenuSceneParent = loader.load();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene mainMenuScene = new Scene(mainMenuSceneParent, window.getWidth(), window.getHeight());
        window.setScene(mainMenuScene);
    }

}
