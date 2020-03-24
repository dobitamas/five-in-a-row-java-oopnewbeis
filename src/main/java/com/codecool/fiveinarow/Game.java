package com.codecool.fiveinarow;
import java.util.Scanner;
import java.util.Arrays;

public class Game implements GameInterface {

    private int[][] board;

    public Game(int nRows, int nCols) {
        board = new int[nRows][nCols];
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int[] getMove(int player) {
    	boolean coordExist = false;
    	boolean coordEmpty = false;
    	int[]coord = new int[2];
    	while( coordExist != true || coordEmpty != true){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter coordinates");
		String coordinates = scanner.nextLine();
        	String col = coordinates.substring(0,1).toLowerCase();
		int row = Integer.parseInt(coordinates.substring(1));
 
		String alphabet = "abcdefghijklmnopqrstuvwxyz";

		coord[0] = alphabet.indexOf(col);
		coord[1] = row - 1;
		coordExist = isCoordExist(coord);
		coordEmpty = isCoordTaken(coord);
	
	}
	
	
	System.out.println(Arrays.toString(coord));
	return coord;
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
    }

    public void printResult(int player) {
    }

    public void enableAi(int player) {
    }

    public void play(int howMany) {

    }
	
    public boolean isCoordExist(int[]coord){
	if(board.length < coord[0] || board[0].length < coord[1]){
		return false;
	} else {
		return true;
	}	
    }
    public boolean isCoordTaken(int [] coord){
    	return (board[coord[0]][coord[1]] != 0);
    }
	

}











