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
        int[] AIMove = new int[2];
        boolean turn = true;
        while (!isFull()){
            if (turn) {
                int [] coordinates = getMove(player1);
                int coordinateX = coordinates[0];
                int coordinateY = coordinates[1];
                mark(player1, coordinateX, coordinateY);
                AIMove = CalculateAIMoves(coordinateX,coordinateY);
                System.out.print("\033[H\033[2J"); //In ubuntu terminal works!!
                printBoard();
                if (hasWon(player1, howMany, coordinateX, coordinateY)){
                    printResult(player1);
                    break;
                }
                turn = false;


            } else {
                if (AI) {
                    int X = AIMove[0];
                    int Y = AIMove[1];
                    mark(player2,X,Y);
                    System.out.print("\033[H\033[2J"); //In ubuntu terminal works!!
                    printBoard();
                    if (hasWon(player2, howMany, X, Y)) {
                        printResult(player2);
                        break;
                    }
                }else {
                    int[] coordinates = getMove(player2);
                    int coordinateX = coordinates[0];
                    int coordinateY = coordinates[1];
                    mark(player2, coordinateX, coordinateY);
                    System.out.print("\033[H\033[2J"); //In ubuntu terminal works!!
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
        int coordinateX = checkQuitX(input.nextLine());

        System.out.println("Enter a letter: ");
        char letter = checkQuitY(input.nextLine());

        result[0] = coordinateX;
        result[1] = converter(letter);
        if (!checkIfValidMove(coordinateX,converter(letter))){
            System.out.println("This is not a valid coordinate");
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
            handleTurn(howMany,true);
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
        return coordinateX <= board.length && coordinateY <= board[1].length && board[coordinateX][coordinateY] == 0;
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
    public int checkQuitX(String input) {
        int coordinateX;
        try {
            coordinateX = Integer.parseInt(input);
            return coordinateX -1;
        }catch (java.lang.NumberFormatException e){
            System.exit(0);
        }
        return 0;
    }

    public char checkQuitY(String input) {
        if(input.equals("quit")){
            System.exit(0);
        }
        return input.charAt(0);
    }

    public int[] CalculateAIMoves(int coordinateX, int coordinateY) {
        int allRows = board.length;
        int allCols = board[1].length;
        int enemyMove = board[coordinateX][coordinateY];
        int[] enemyCoordinates = {coordinateX,coordinateY};
        System.out.println("ezoké");
        int [] horizontalResult = horizontalAI(coordinateX,coordinateY,allCols,enemyMove);
        System.out.println("ezis");
        int[] verticalResult = verticalAI(coordinateX,coordinateY,enemyMove,allRows);
        System.out.println("ezissss");
        int [] diagonalResult = diagonalAI(coordinateX,coordinateY,enemyMove,allCols,allRows);
        System.out.println("itt???");
        if (horizontalResult[0] != enemyCoordinates[0] || horizontalResult[1] != enemyCoordinates[1]){
            return new int[] {horizontalResult[0],horizontalResult[1]};
        }
        if (verticalResult[0] != enemyCoordinates[0] || verticalResult[1] != enemyCoordinates[1]){
            return new int[]{verticalResult[0],verticalResult[1]};
        }
        if (diagonalResult[0] != enemyCoordinates[0] || diagonalResult[1] != enemyCoordinates[1]){
            return new int[] {diagonalResult[0],diagonalResult[1]};
        }
        double coordinateXX = (Math.random() * (board.length - 1));
        double coordinateYY = (Math.random() * (board.length - 1));
        while (!checkIfValidMove((int)coordinateXX,(int)coordinateYY)) {
            coordinateXX = (Math.random() * (board.length - 1));
            coordinateYY = (Math.random() * (board.length - 1));
        }
        return new int[] {(int)coordinateXX,(int) coordinateYY};
    }

    public int[] horizontalAI(int coordinateX,int coordinateY,int allCols,int enemyMove){
        if (coordinateY >= 0 && coordinateY < allCols) {
            if (coordinateY >= 2 && board[coordinateX][coordinateY - 2] == enemyMove && board[coordinateX][coordinateY - 1] == enemyMove) {
                if (coordinateY + 1 < allCols) {
                    if (board[coordinateX][coordinateY + 1] == 0) {
                        return new int[]{coordinateX, coordinateY + 1};
                    }
                }
                if (coordinateY - 3 >= 0) {
                    if (board[coordinateX][coordinateY - 3] == 0) {
                        return new int[]{coordinateX, coordinateY - 3};
                    }
                }
            }
            if (coordinateY < allCols - 2 && coordinateY > 1 && board[coordinateX][coordinateY - 1] == enemyMove && board[coordinateX][coordinateY + 1] == enemyMove) {
                if (board[coordinateX][coordinateY - 2] == 0) {
                    return new int[]{coordinateX, coordinateY - 2};
                }
                if (board[coordinateX][coordinateY + 2] == 0) {
                    return new int[]{coordinateX, coordinateY + 2};
                }
            }
            if (coordinateY < allCols-3 && board[coordinateX][coordinateY+1] == enemyMove && board[coordinateX][coordinateY+2] == enemyMove){
                if (coordinateY + 3 < allCols ) {
                    if (board[coordinateX][coordinateY + 3] == 0) {
                        return new int[]{coordinateX, coordinateY + 3};
                    }
                }
                if (coordinateY - 1 >= 0) {
                    if (board[coordinateX][coordinateY - 1] == 0) {
                        return new int[]{coordinateX, coordinateY - 1};
                    }
                }
            }
        }
        return new int[] {coordinateX, coordinateY};
    }
    public int[] verticalAI(int coordinateX,int coordinateY,int enemyMove,int allRows){
        if (coordinateX >= 0 && coordinateX < allRows){
            if (coordinateX > 2 && board[coordinateX -2][coordinateY] == enemyMove && board[coordinateX-1][coordinateY] == enemyMove){
                if (coordinateX + 1 < allRows) {
                    if (board[coordinateX + 1][coordinateY] == 0) {
                        return new int[]{coordinateX + 1, coordinateY};
                    }
                }
                if (coordinateX - 3 >= 0) {
                    if (board[coordinateX - 3][coordinateY] == 0) {
                        return new int[]{coordinateX - 3, coordinateY};
                    }
                }
            }
            if (coordinateX < allRows -2 && coordinateX > 1 && board[coordinateX-1][coordinateY] == enemyMove && board[coordinateX+1][coordinateY] == enemyMove){
                if (board[coordinateX+2][coordinateY] == 0){
                    return new int[]{coordinateX+2, coordinateY};
                }
                if(board[coordinateX-2][coordinateY] == 0){
                    return new int[]{coordinateX-2, coordinateY};
                }
            }
            if (coordinateX <= allRows-3 && board[coordinateX+1][coordinateY] == enemyMove && board[coordinateX+2][coordinateY] == enemyMove){
                if (coordinateX + 3 < allRows) {
                    if (board[coordinateX + 3][coordinateY] == 0) {
                        return new int[]{coordinateX + 3, coordinateY};
                    }
                }
                if (coordinateX -1 >= 0) {
                    if (board[coordinateX - 1][coordinateY] == 0) {
                        return new int[]{coordinateX - 1, coordinateY};
                    }
                }
            }
        }
        return new int[] {coordinateX,coordinateY};
    }
    public int[] diagonalAI(int coordinateX,int coordinateY,int enemyMove,int allCols,int allRows){
        if (coordinateY >= 0 && coordinateY < allCols && coordinateX >= 0 && coordinateX < allRows) {
            if (coordinateX -2 >= 0 && coordinateY -2 >= 0) {
                if (board[coordinateX - 2][coordinateY - 2] == enemyMove && board[coordinateX - 1][coordinateY - 1] == enemyMove) {
                    if (coordinateX + 1 < allRows && coordinateY + 1 < allCols) {
                        if (board[coordinateX + 1][coordinateY + 1] == 0) {
                            return new int[]{coordinateX + 1, coordinateY + 1};
                        }
                    }
                    if (coordinateX - 3 >= 0 && coordinateY - 3 >= 0) {
                        if (board[coordinateX - 3][coordinateY - 3] == 0) {
                            return new int[]{coordinateX - 3, coordinateY - 3};
                        }
                    }
                }
            }
            if (coordinateY < allCols - 2 && coordinateY > 1 && coordinateX < allRows - 2 && coordinateX > 1 && board[coordinateX - 1][coordinateY - 1] == enemyMove && board[coordinateX + 1][coordinateY + 1] == enemyMove) {
                if (board[coordinateX + 2][coordinateY + 2] == 0) {
                    return new int[]{coordinateX + 2, coordinateY + 2};
                }
                if (board[coordinateX - 2][coordinateY - 2] == 0) {
                    return new int[]{coordinateX - 2, coordinateY - 2};
                }
            }
            if (coordinateX + 2 < allRows && coordinateY + 2 < allCols) {
                if (coordinateY <= allCols - 3 && coordinateX <= allRows - 3 && board[coordinateX + 1][coordinateY + 1] == enemyMove && board[coordinateX + 2][coordinateY + 2] == enemyMove) {
                    if (coordinateX + 3 < allRows && coordinateY + 3 < allCols) {
                        if (board[coordinateX + 3][coordinateY + 3] == 0) {
                            return new int[]{coordinateX + 3, coordinateY + 3};
                        }
                    }
                    if (coordinateX - 1 >= 0 && coordinateY - 1 >= 0) {
                        if (board[coordinateX - 1][coordinateY - 1] == 0) {
                            return new int[]{coordinateX - 1, coordinateY - 1};
                        }
                    }
                }
            }
            if (coordinateX + 2 < allRows && coordinateY + 2 < allCols) {
                if (coordinateY >= 2 && coordinateX >= 2 && board[coordinateX + 2][coordinateY - 2] == enemyMove && board[coordinateX + 1][coordinateY - 1] == enemyMove) {
                    if (coordinateY + 1 < allCols) {
                        if (board[coordinateX - 1][coordinateY + 1] == 0) {
                            return new int[]{coordinateX - 1, coordinateY + 1};
                        }
                    }
                    if (coordinateX - 3 >= 0 && coordinateY + 3 < allCols) {
                        if (board[coordinateX - 3][coordinateY + 3] == 0) {
                            return new int[]{coordinateX - 3, coordinateY + 3};
                        }
                    }
                }
            }
            if (coordinateY + 1 < allCols && coordinateX + 1 < allRows && coordinateX -1 >= 0 && coordinateY -1 >= 0) {
                if (coordinateY < allCols - 2 && coordinateY > 1 && coordinateX < allRows - 2 && coordinateX > 1 && board[coordinateX + 1][coordinateY - 1] == enemyMove && board[coordinateX - 1][coordinateY + 1] == enemyMove) {
                    if (board[coordinateX - 2][coordinateY + 2] == 0) {
                        return new int[]{coordinateX - 2, coordinateY + 2};
                    }
                    if (board[coordinateX + 2][coordinateY - 2] == 0) {
                        return new int[]{coordinateX + 2, coordinateY - 2};
                    }
                }
            }
            if (coordinateY + 2 < allCols && coordinateX -2 >= 0 ) {
                if (coordinateY <= allCols - 3 && coordinateX <= allRows - 3 && coordinateX > 2 && board[coordinateX - 1][coordinateY + 1] == enemyMove && board[coordinateX - 2][coordinateY + 2] == enemyMove) {
                    if (coordinateY + 3 < allCols) {
                        if (board[coordinateX - 3][coordinateY + 3] == 0) {
                            return new int[]{coordinateX - 3, coordinateY + 3};
                        }
                    }
                    if (coordinateX + 1 < allRows && coordinateY - 1 >= 0) {
                        if (board[coordinateX + 1][coordinateY - 1] == 0) {
                            return new int[]{coordinateX + 1, coordinateY - 1};
                        }
                    }
                }
            }
        }
        return new int[] {coordinateX,coordinateY};
    }
}
