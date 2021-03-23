package com.example.tictactoe;

import java.util.Random;

public class TictactoeTwoPlayer {

    private static final Random RANDOM = new Random();
    private char[] chars;
    private char currentPlayer;
    private boolean isEnded;

    public TictactoeTwoPlayer() {
        chars = new char[9];
        startGame();
    }

    public boolean isGameEnded() {
        return isEnded;
    }

    public char play(int x, int y) {
        if (!isEnded && chars[3 * y + x] == ' ') {
            chars[3 * y + x] = currentPlayer;
            switchPlayer();
        }

        return checkEnd();
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X' ? 'O' : 'X');
    }

    public char getLocation(int x, int y) {
        return chars[3 * y + x];
    }

    public void startGame() {
        for (int i = 0; i < chars.length; i++) {
            chars[i] = ' ';
        }

        currentPlayer = 'X';
        isEnded = false;
    }

    public char checkEnd() {
        for (int i = 0; i < 3; i++) {
            if (getLocation(i, 0) != ' ' &&
                    getLocation(i, 0) == getLocation(i, 1) &&
                    getLocation(i, 1) == getLocation(i, 2)) {
                isEnded = true;
                return getLocation(i, 0);
            }

            if (getLocation(0, i) != ' ' &&
                    getLocation(0, i) == getLocation(1, i) &&
                    getLocation(1, i) == getLocation(2, i)) {
                isEnded = true;
                return getLocation(0, i);
            }
        }

        if (getLocation(0, 0) != ' ' &&
                getLocation(0, 0) == getLocation(1, 1) &&
                getLocation(1, 1) == getLocation(2, 2)) {
            isEnded = true;
            return getLocation(0, 0);
        }

        if (getLocation(2, 0) != ' ' &&
                getLocation(2, 0) == getLocation(1, 1) &&
                getLocation(1, 1) == getLocation(0, 2)) {
            isEnded = true;
            return getLocation(2, 0);
        }

        for (int i = 0; i < 9; i++) {
            if (chars[i] == ' ')
                return ' ';
        }

        return 'T';
    }

}