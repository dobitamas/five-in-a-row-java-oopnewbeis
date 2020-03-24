package com.codecool.fiveinarow;
import java.util.Scanner;
import java.util.Arrays;

public class Game implements GameInterface {

    private int[][] board;
    private int[] latestMove = new int[2];
    private String alphabet = "abcdefghijklmnopqrstuvwxyz";

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
		coord[0] = alphabet.indexOf(col);
		coord[1] = row - 1;
		coordExist = isCoordExist(coord);
		if(coordExist == true){
			coordEmpty = isCoordTaken(coord);
		}
	}
	latestMove[0] = coord[0];
	latestMove[1] = coord[1];
	return coord;
    }

    public int[] getAiMove(int player) {
        return null;
    }

    public void mark(int player, int row, int col) {
	board[row][col] = player;
    }

    public boolean hasWon(int player, int howMany) {
        System.out.println(Arrays.toString(latestMove));
	return false;
    }

    public boolean isFull() {
	for (int[] row : board){
	    for (int cell : row){
		if (cell == 0){
		    return false;
		}
	    }
	}
        return true;
    }

    public void printBoard() {
	System.out.print("  ");
	for (int i = 1; i < board[0].length; i++){
	    System.out.print(i + " ");
	}
	System.out.println(board[0].length);
	for (int i = 0; i < board.length; i++){	  
	    System.out.print(alphabet.substring(i, i + 1).toUpperCase() + " ");
	    for (int cell : board[i]){
		switch (cell){
		    case 1:
			System.out.print("X ");
			break;
		    case 2:
			System.out.print("O ");
			break;
		    default:
			System.out.print(". ");
		}
	    }
	    System.out.println("");
	}
    }

    public void printResult(int player) {
    }

    public void enableAi(int player) {
    }

    public void play(int howMany) {
	while(true){
		printBoard();
		int[] coord = getMove(1);
		mark(1, coord[0], coord[1]);
		printBoard();
		if (hasWon(1, 5)){
		    break;
		}	
		int[] coords = getMove(2);
		mark(2, coords[0], coords[1]);
		printBoard();
		if (hasWon(2, 5)){
		    break;
		}
	}
    }
	
    public boolean isCoordExist(int[]coord){
	if(board.length < coord[0] || board[0].length < coord[1]){
		return false;
	} else {
		return true;
	}	
    }

    public boolean isCoordTaken(int [] coord){
    	return (board[coord[0]][coord[1]] == 0);
    }

}