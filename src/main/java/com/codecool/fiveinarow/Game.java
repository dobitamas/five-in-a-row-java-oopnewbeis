package com.codecool.fiveinarow;

import java.util.Arrays;

public class Game implements GameInterface {

    private int[][] board;

    public Game(int nRows, int nCols) {
        board = new int[nRows][nCols];
//        The board below is a dummy list for testing
//        Good to know: 1 = O; 2 = X;
//        board = new int[][]{
//                {0,1,0},
//                {2,0,1},
//                {0,0,0},
//        };
        printBoard();
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {

        this.board = board;
    }

    public void handleTurn(int howMany){
        int player1 = 1;
        int player2 = 2;
        boolean turn = false;
        while (!isFull()){
            if (turn) {
               getMove(player1);
               hasWon(player1, howMany);
               turn = true;
            } else {
                getMove(player2);
                hasWon(player2, howMany);
                turn = false;
            }
        }
    }

    public int[] getMove(int player) {

        return null;
    }

    public int[] getAiMove(int player) {

        return null;
    }

    public void mark(int player, int row, int col) {
        if (board[row][col] == 0){
            board[row][col] = player;
        } else {
            System.out.println("This is not a valid coordinate");
        }
    }

    public boolean hasWon(int player, int howMany) {
        return false;
    }

    public boolean isFull() {

        return false;
    }

    public void printBoard() {

        char[] alphabet = " abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] header = Arrays.copyOfRange(alphabet,0, board.length + 1);
        for (char c : header) {
            System.out.print(String.valueOf(c) + '\t');
        }
        System.out.println();
        for (int i =0; i < board.length; i++){
            System.out.print(i+1 + "\t");
            for (int j = 0; j < board[i].length; j++){
                switch (board[i][j]){
                    case 0:
                        System.out.print('.' + "\t");
                        break;
                    case 1:
                        System.out.print('O' + "\t");
                        break;
                    case 2:
                        System.out.print('X' + "\t");
                        break;
                }
            }
            System.out.println();
        }
    }

    public void printResult(int player) {
    }

    public void enableAi(int player) {
    }

    public void play(int howMany) {
    }
}
