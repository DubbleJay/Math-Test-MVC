package com.doublej.mathtestmvc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller  {

    @FXML
    Text questionText;
    @FXML
    Text outputText;
    @FXML
    TextField answerTextField;
    @FXML
    Button enterButton;
    @FXML
    Button nextButton;
    @FXML
    Text solutionText;
    @FXML
    Button quitButton;
    @FXML
    Label errorLabel;
    @FXML
    Label questionIndexLabel;
    @FXML
    Label questionTotalLabel;

    private Model model;
    private AudioClip rightAnswerAudioClip = new AudioClip(this.getClass().getResource("CorrectAnswer.mp3").toString());
    private AudioClip wrongAnswerAudioClip = new AudioClip(this.getClass().getResource("WrongAnswer.mp3").toString());

    public void setModel (int gameType, int numberOfQuestions) {
        model = new Model(gameType, numberOfQuestions);

        setUpNextQuestion();
        answerTextField.requestFocus();
        answerTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                checkUserAnswer();
        });
        nextButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                setUpNextQuestion();
        });
        rightAnswerAudioClip.setVolume(.5);
        wrongAnswerAudioClip.setVolume(.3);
        questionText.setId("questionText");
        solutionText.setId("solutionText");
        questionTotalLabel.setText(" / " + numberOfQuestions);
    }

    public void setUpNextQuestion () {
        if(!model.isGameOver()) {
            enterButton.setDisable(false);
            nextButton.setDisable(true);
            answerTextField.setDisable(false);
            model.generateCurrentQuestion();
            questionText.setText(model.getQuestionString());
            outputText.setText("");
            answerTextField.setText("");
            solutionText.setText("");
            answerTextField.requestFocus();
            questionIndexLabel.setText("Question " + (model.getQuestionIndex() + 1));
        }

        else {
            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("End of Game.fxml"));
                Parent endOfGameSceneParent = loader.load();

                EndOfGameController endOfGameController = loader.getController();
                endOfGameController.setValues(model.getCorrectAnswers(), model.getWrongAnswers(), model.getNumberOfQuestions(),
                        (int)model.getUserScore(), model.getSummary());

                Stage window = (Stage) (nextButton.getScene().getWindow());
                Scene endOfGameScene = new Scene(endOfGameSceneParent, window.getWidth(), window.getHeight());
                window.setScene(endOfGameScene);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void checkUserAnswer () {
        try {

            boolean correct = model.checkUserAnswer(Integer.parseInt(answerTextField.getText().trim()));
            errorLabel.setVisible(false);

            if (correct) {
                solutionText.setFill(Color.FORESTGREEN);
                solutionText.setText(" = " + String.valueOf(answerTextField.getText()));
                outputText.setFill(Color.FORESTGREEN);
                rightAnswerAudioClip.play();
            } else {
                solutionText.setFill(Color.RED);
                solutionText.setText(" != " + answerTextField.getText());
                outputText.setFill(Color.RED);
                wrongAnswerAudioClip.play();
            }
            answerTextField.setDisable(true);
            enterButton.setDisable(true);
            nextButton.setDisable(false);
            nextButton.requestFocus();
            String result = correct ? "Correct!" : "Wrong";
            outputText.setText(result);

        } catch (NumberFormatException nNFE) {
            errorLabel.setVisible(true);
        }
    }

    public void quitGame (ActionEvent event) throws Exception{
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        Parent mainMenuSceneParent = FXMLLoader.load(getClass().getResource("Main Menu.fxml"));
        window.setScene(new Scene(mainMenuSceneParent, window.getWidth(), window.getHeight()));
    }
}
