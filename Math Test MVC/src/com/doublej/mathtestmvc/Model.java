package com.doublej.mathtestmvc;

public class Model {

    private int gameType;
    private String operator;
    private int questionTotal;
    private int questionIndex = 0;
    private int correctAnswers = 0;
    private int wrongAnswers = 0;
    private int[] currentQuestion;
    private String[] summary;
    private boolean gameOver = false;

    Model (int gameType, int numberOfQuestions) {
        this.gameType = gameType;
        this.questionTotal = numberOfQuestions;
        switch (gameType) {
            case 0:
                operator = " + ";
                break;
            case 1:
                operator = " - ";
                break;
            case 2:
                operator = " X ";
                break;
            case 3:
                operator = " / ";
                break;
        }
        summary = new String[numberOfQuestions];
    }

    public int[] getCurrentQuestion() {
        return currentQuestion;
    }

    public void generateCurrentQuestion () {

        int num1, num2;

        if(gameType != 2) {
            num1 = (int) (Math.random() * 100);
            num2 = (int) (Math.random() * 100);
        }

        else {
            num1 = (int) (Math.random() * 20);
            num2 = (int) (Math.random() * 10);
        }

        if(num1 < num2) {
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }

        if(gameType == 3) {
            while (num2 == 0 || num1 % num2 != 0) {
                num2 = (int) (Math.random() * 100);
            }
        }

        int answer;

        switch (gameType) {
            case 0:
                answer = num1 + num2;
                break;
            case 1:
                answer = num1 - num2;
                break;
            case 2:
                answer = num1 * num2;
                break;
            case 3:
                answer = num1 / num2;
                break;
            default:
                answer = 0;
                break;
        }

        currentQuestion = new int[] {num1, num2, answer};
    }

    public boolean checkUserAnswer (long userInput) {

        String result = " = " + userInput;

        if(userInput == currentQuestion[2]) {
            correctAnswers++;
            result = result.concat(" Correct!");
        }
        else {
            wrongAnswers++;
            result = result.concat(" Wrong. Correct Answer = " + currentQuestion[2]);
        }

        summary[(correctAnswers + wrongAnswers) - 1] = (correctAnswers + wrongAnswers) + ") " + getQuestionString().concat(result);

        questionIndex++;

        if(questionIndex == questionTotal)
            gameOver = true;

        return userInput == currentQuestion[2];
    }

    public int getNumberOfQuestions() {
        return questionTotal;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public String[] getSummary() {
        return summary;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getQuestionString () {
        return "What is " + currentQuestion[0] + operator + currentQuestion[1] + "?";
    }

    public double getUserScore () {
        return Math.round((double)correctAnswers / questionTotal * 100);
    }
}

