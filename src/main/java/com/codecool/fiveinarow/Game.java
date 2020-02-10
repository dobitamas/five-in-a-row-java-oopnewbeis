package com.codecool.fiveinarow;

import java.util.Arrays;

public class Game implements GameInterface {

    private int[][] board;

    public Game(int nRows, int nCols) {
        board = new int[nRows][nCols];
        printBoard();
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {

        this.board = board;
    }

    public int[] getMove(int player) {

        return null;
    }

    public int[] getAiMove(int player) {

        return null;
    }

    public void mark(int player, int row, int col) {
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
                System.out.print(board[i][j] + "\t");
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
