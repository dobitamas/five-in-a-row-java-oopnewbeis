package com.codecool.fiveinarow;


import java.util.Arrays;
import java.util.Scanner;

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

    public void handleTurn(int howMany,boolean AI){
        int player1 = 1;
        int player2 = 2;
        boolean turn = true;
        while (!isFull()){
            if (turn) {
                System.out.print("\033[H\033[2J"); //TODO in fact this clears the terminal screen. Need to be tried!
                int [] coordinates = getMove(player1);
                int coordinateX = coordinates[0];
                int coordinateY = coordinates[1];
                mark(player1, coordinateX, coordinateY);
                printBoard();
                if (hasWon(player1, howMany, coordinateX, coordinateY)){
                    printResult(player1);
                    break;
                }
                turn = false;
            } else {
                if (AI) {
                    double coordinateX = (Math.random()*(board.length - 1));
                    double coordinateY = (Math.random()*(board.length - 1));
                    checkIfValidMove((int) coordinateX,(int) coordinateY);
                    mark(player2,(int)coordinateX,(int) coordinateY);
                    printBoard();
                    if (hasWon(player2, howMany, (int) coordinateX,(int) coordinateY)) {
                        printResult(player2);
                        break;
                    }
                }else {
                    System.out.print("\033[H\033[2J");
                    int[] coordinates = getMove(player2);
                    int coordinateX = coordinates[0];
                    int coordinateY = coordinates[1];
                    mark(player2, coordinateX, coordinateY);
                    printBoard();
                    if (hasWon(player2, howMany, coordinateX, coordinateY)) {
                        printResult(player2);
                        break;
                    }
                }
                turn = true;
            }
        }
        if (isFull()){
            System.out.println("The board is full bruh!!");
        }
    }

    public int[] getMove(int player) {
        Scanner input =new Scanner(System.in);
        System.out.println("Take your move, " +player +"!");
        System.out.println("Enter a number: ");
        int[] result = new int[2];
        int number = input.nextInt() -1;
        System.out.println("Enter a letter: ");
        char letter = input.next().charAt(0);
        result[0] = number;
        result[1] = converter(letter);
        if (!checkIfValidMove(number,converter(letter))){
            System.out.println("This place is not empty");
            return getMove(player);
        }
        return result;
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

    public boolean hasWon(int player, int howMany, int coordinateX, int coordinateY) {
        int colLenght = board[1].length;
        int rowLenght = board.length;

        if(checkDirections(colLenght, coordinateX, player, howMany, "Horizontal")){
            return true;
        } else if(checkDirections(rowLenght, coordinateY, player, howMany, "Vertical")){
            return true;
        } else if(checkDirectionDiagonal(rowLenght, colLenght, player, howMany, "DiagonalCol")){
            return true;
        } else return checkDirectionDiagonal(rowLenght, colLenght, player, howMany, "DiagonalRow");
    }

    public boolean isFull() {
        for (int[] i: board){
            for (int j:i){
                if (j == 0){
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {

        char[] alphabet = " abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] header = Arrays.copyOfRange(alphabet,0, board[1].length + 1);
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
        if(player == 1){
            System.out.println("\u001B[32m" +" The player with the O has won" + "\u001B[32m");
        } else if(player == 2){
            System.out.println("\u001B[36m" +" The player with the X has won" + "\u001B[36m");
        }
    }

    public void enableAi(int player) {
    }

    public void play(int howMany, boolean AI) {
        printBoard();
        if(AI){
            handleTurn(howMany,AI);
        } else {
            handleTurn(howMany, false);
        }
    }

    public int converter(char letter) {
        int temp_integer = 97;
        return (int)letter - temp_integer;
    }


    public boolean checkDirections (int length, int coordinate, int player, int howMany, String direction){
        if (direction.equals("Horizontal")) {
            int countHorizontal = 0;
            for (int col = 0; col < length ; col++) {
                if(board[coordinate][col] == player){
                    countHorizontal++;
                } else {
                    countHorizontal = 0;
                }
                if(countHorizontal == howMany){
                    return true;
                }
            }
        } else if (direction.equals("Vertical")){
            int countVertical = 0;
            for (int row = 0; row < length; row++) {
                if (board[row][coordinate] == player) {
                    countVertical++;
                } else {
                    countVertical = 0;
                }
                if (countVertical == howMany) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfValidMove(int coordinateX, int coordinateY){
        return board[coordinateX][coordinateY] == 0;
    }

    public boolean checkDirectionDiagonal(int rowLenght, int colLenght, int player, int howMany, String direction){
        int col;
        int row;
        if (direction.equals("DiagonalCol")){
            int diagonalColCount = 0;
            for (int startOfRow = 0; startOfRow < rowLenght - howMany ; startOfRow++){
                for (row = startOfRow, col = 0; row < rowLenght && col < colLenght; row++, col++) {
                    if(board[row][col] == player){
                        diagonalColCount++;
                    }else{
                        diagonalColCount = 0;
                    }
                    if (diagonalColCount == howMany) {
                        return true;
                    }
                }
            }
        } else if (direction.equals("DiagonalRow")){
            int diagonalRowCount = 0;
            for (int startOfCol = 1; startOfCol < colLenght - howMany ; startOfCol++) {
                for ( col = startOfCol, row = 0; col < colLenght && row < rowLenght ; row++, col++) {
                    if(board[row][col] == player){
                        diagonalRowCount++;
                    }else{
                        diagonalRowCount = 0;
                    }
                    if(diagonalRowCount == howMany){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
