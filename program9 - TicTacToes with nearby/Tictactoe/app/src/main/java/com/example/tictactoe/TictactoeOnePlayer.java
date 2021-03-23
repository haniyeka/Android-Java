package com.example.tictactoe;

import java.util.Random;

public class TictactoeOnePlayer {

    private char currentPlayer;
    private char player;
    public char AI;
    private static final Random RANDOM = new Random();
    private char[] boardArray;
    private boolean is_game_over;

    public void define_Player(char Player){
        this.player = Player;
    }

    public TictactoeOnePlayer(char Player) {
        this.player = Player;
        boardArray = new char[9];
        start_game();
    }

    public boolean is_Game_Over() {
        return is_game_over;
    }

    public char play(int x, int y) {
        if (!is_game_over &&  boardArray[3 * y + x] == ' ') {
            boardArray[3 * y + x] = currentPlayer;
            switch_Player();
        }
        return check_gameOver();
    }

    public void switch_Player() {
        if(currentPlayer==player){
            currentPlayer = AI;
        }
        else {
            currentPlayer = player;
        }
    }

    public char getLocation(int x, int y) {
        return boardArray[3 * y + x];
    }

    public void start_game() {
        for (int i = 0; i  < boardArray.length; i++) {
            boardArray[i] = ' ';
        }
        if (player == 'X') {
            AI = 'O';
        }
        else{
            AI = 'X';
        }
        currentPlayer = player;
        is_game_over = false;
    }

    public char check_gameOver() {
        for (int i = 0; i < 3; i++) {
            if (getLocation(i, 0) != ' ' &&
                    getLocation(i, 0) == getLocation(i, 1)  &&
                    getLocation(i, 1) == getLocation(i, 2)) {
                is_game_over = true;
                return getLocation(i, 0);
            }
            if (getLocation(0, i) != ' ' &&
                    getLocation(0, i) == getLocation(1, i)  &&
                    getLocation(1, i) == getLocation(2, i)) {
                is_game_over = true;
                return getLocation(0, i);
            }
        }
        if (getLocation(0, 0) != ' '  &&
                getLocation(0, 0) == getLocation(1, 1)  &&
                getLocation(1, 1) == getLocation(2, 2)) {
            is_game_over = true;
            return getLocation(0, 0);
        }
        if (getLocation(2, 0) != ' '  &&
                getLocation(2, 0) == getLocation(1, 1)  &&
                getLocation(1, 1) == getLocation(0, 2)) {
            is_game_over = true;
            return getLocation(2, 0);
        }
        for (int i = 0; i < 9; i++) {
            if (boardArray[i] == ' ')
                return ' ';
        }
        return 'T';
    }

    public char AI() {
        int x = 0;
        if (!is_game_over) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (getLocation(i, j) == player || getLocation(i, j) == AI) {
                        x = x + 1;
                    }
                }
            }
            if (x == 1) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (getLocation(0, j) == player) {
                            boardArray[3 * j + 2] = currentPlayer;

                        } else if (getLocation(1, j) == player) {
                            boardArray[3 * j + 0] = currentPlayer;

                        } else if (getLocation(2, j) == player) {
                            boardArray[3 * j + 1] = currentPlayer;
                        }
                    }
                }
            }
            if (x == 3) {
                int[] str = new int[10];
                int k = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (getLocation(i, j) == player) {
                            str[k] = 3 * j + i;
                            k = k + 1;
                        } else if (getLocation(i, j) == AI) {
                            str[k] = 3 * j + i;
                            k = k + 1;
                        }
                    }
                }
                if (boardArray[0] == player && boardArray[4] == player) {
                    boardArray[8] = currentPlayer;
                } else if (boardArray[0] == player && boardArray[8] == player) {
                    boardArray[4] = currentPlayer;
                } else if (boardArray[4] == player && boardArray[8] == player) {
                    boardArray[0] = currentPlayer;
                } else if (boardArray[2] == player && boardArray[4] == player) {
                    boardArray[6] = currentPlayer;
                } else if (boardArray[2] == player && boardArray[6] == player) {
                    boardArray[4] = currentPlayer;
                } else if (boardArray[4] == player && boardArray[6] == player) {
                    boardArray[2] = currentPlayer;
                } else if (boardArray[0] == player && boardArray[3] == player) {
                    boardArray[6] = currentPlayer;
                } else if (boardArray[0] == player && boardArray[6] == player) {
                    boardArray[3] = currentPlayer;
                } else if (boardArray[3] == player && boardArray[6] == player) {
                    boardArray[0] = currentPlayer;
                } else if (boardArray[1] == player && boardArray[4] == player) {
                    boardArray[7] = currentPlayer;
                } else if (boardArray[1] == player && boardArray[7] == player) {
                    boardArray[4] = currentPlayer;
                } else if (boardArray[4] == player && boardArray[7] == player) {
                    boardArray[1] = currentPlayer;
                } else if (boardArray[2] == player && boardArray[5] == player) {
                    boardArray[8] = currentPlayer;
                } else if (boardArray[2] == player && boardArray[8] == player) {
                    boardArray[5] = currentPlayer;
                } else if (boardArray[5] == player && boardArray[8] == player) {
                    boardArray[2] = currentPlayer;
                } else if (boardArray[1] == player && boardArray[3] == player) {
                    boardArray[4] = currentPlayer;
                } else {
                    for (int i = 0; i < 9; i++) {
                        if (str[i] != i) {
                            boardArray[i] = currentPlayer;
                            break;
                        }
                    }
                }
            }
            if (x == 5) {
                int[] pos = new int[4];
                int k = 0;
                for (int i = 0; i < 9; i++) {
                    if (boardArray[i] == ' ') {
                        pos[k] = i;
                        k = k + 1;
                    }
                }
                if (boardArray[2] == player && boardArray[5] == player && boardArray[4] == player && boardArray[3] == ' ') {

                    boardArray[3] = currentPlayer;

                } else if (boardArray[0] == player && boardArray[3] == player && boardArray[4] == player && boardArray[5] == ' ') {

                    boardArray[5] = currentPlayer;

                } else if (boardArray[4] == player && boardArray[6] == player && boardArray[8] == player) {
                    if (boardArray[2] != ' ') {
                        boardArray[0] = currentPlayer;
                    } else {
                        boardArray[2] = currentPlayer;
                    }

                } else if (boardArray[0] == player && boardArray[2] == player && boardArray[4] == player && boardArray[8] == ' ') {

                    boardArray[8] = currentPlayer;

                } else if (boardArray[1] == player && boardArray[4] == player && boardArray[6] == player && boardArray[2] == ' ') {

                    boardArray[2] = currentPlayer;

                } else if (boardArray[4] == player && boardArray[7] == player && boardArray[8] == player && boardArray[6] == ' ') {

                    boardArray[6] = currentPlayer;

                } else {

                    Random r = new Random();
                    int randomNumber = r.nextInt(pos.length);
                    randomNumber = pos[randomNumber];
                    boardArray[randomNumber] = currentPlayer;
                }


            }
            if (x == 7) {
                int[] pos7 = new int[2];
                int k = 0;
                if (boardArray[6] == 'X' && boardArray[7] == 'X' && boardArray[5] == 'X' && boardArray[4] == 'X') {

                    boardArray[1] = currentPlayer;

                } else if (boardArray[1] == 'X' && boardArray[3] == 'X' && boardArray[4] == 'X' && boardArray[6] == 'X') {

                    boardArray[5] = currentPlayer;

                } else {
                    for (int i = 0; i < 9; i++) {
                        if (boardArray[i] == ' ') {
                            pos7[k] = i;
                            k = k + 1;
                        }
                    }
                    Random r = new Random();
                    int randomNumber = r.nextInt(pos7.length);
                    randomNumber = pos7[randomNumber];
                    boardArray[randomNumber] = currentPlayer;
                }
            }
            switch_Player();
        }
        return check_gameOver();
    }
}
