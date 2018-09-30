package com.doublej.mathtestmvc;

import javafx.application.Platform;
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



    int solution;

    Model model;
    String operator;
    AudioClip rightAnswerAudioClip = new AudioClip(this.getClass().getResource("CorrectAnswer.mp3").toString());
    AudioClip wrongAnswerAudioClip = new AudioClip(this.getClass().getResource("WrongAnswer.mp3").toString());
    private int numberOfQuestions;
    private int questionIndex =  1;

    public void setModel (int gameType, int numberOfQuestions) {
        model = new Model(gameType, numberOfQuestions);
        this.numberOfQuestions = numberOfQuestions;
        switch (gameType) {
            case 0:
                operator = "+";
                break;
            case 1:
                operator = "-";
                break;
            case 2:
                operator = "X";
                break;
            case 3:
                operator = "/";
                break;
        }
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
        if(numberOfQuestions > 0) {
            enterButton.setDisable(false);
            nextButton.setDisable(true);
            answerTextField.setDisable(false);
            int[] numbers = model.generateQuestion();
            solution = numbers[2];
            questionText.setText("What is " + numbers[0] + " " + operator + " " + numbers[1] + "?");
            outputText.setText("");
            answerTextField.setText("");
            solutionText.setText("");
            answerTextField.requestFocus();
            questionIndexLabel.setText("Question " + questionIndex);
        }

        else {
            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("End of Game.fxml"));
                Parent endOfGameSceneParent = loader.load();

                EndOfGameController endOfGameController = loader.getController();
                endOfGameController.setValues(model.correctAnswers, model.wrongAnswers, model.numberOfQuestions, model.getUserScore(), model.summary);

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

            boolean correct = model.checkAnswer(solution, Integer.parseInt(answerTextField.getText().trim()), questionText.getText());
            errorLabel.setVisible(false);

            if (correct) {
                solutionText.setFill(Color.FORESTGREEN);
                solutionText.setText(" = " + String.valueOf(solution));
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
            numberOfQuestions--;
            questionIndex++;

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
