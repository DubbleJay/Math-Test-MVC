package com.doublej.mathtestmvc;

public class Model {

    private int gameType;
    int numberOfQuestions;
    int correctAnswers = 0;
    int wrongAnswers = 0;
    String[] summary;

    Model(int gameType, int numberOfQuestions) {
        this.gameType = gameType;
        this.numberOfQuestions = numberOfQuestions;
        summary = new String[numberOfQuestions];
    }

    public boolean checkAnswer (long solution, long userInput, String question) {
        if(solution == userInput)
            correctAnswers++;
        else
            wrongAnswers++;

        String result = " = " + userInput;

        if(solution == userInput)
             result = result.concat(" Correct!");
        else
            result = result.concat(" Wrong. Correct Answer = " + solution);

        summary[(correctAnswers + wrongAnswers) - 1] = (correctAnswers + wrongAnswers) + ") " + question.concat(result);

        return solution == userInput;
    }

    public int[] generateQuestion () {

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

        return new int[] {num1, num2, answer};
    }

    public double getUserScore () {
        return Math.round((double)correctAnswers / numberOfQuestions * 100);
    }

}
