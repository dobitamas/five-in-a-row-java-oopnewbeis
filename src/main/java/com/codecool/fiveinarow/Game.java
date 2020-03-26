package com.codecool.fiveinarow;

import java.util.Scanner;

import java.util.concurrent.ThreadLocalRandom;
import java.util.*;


public class Game implements GameInterface {
    private int gameMode; // 1 -> player vs. player, 2-> player vs. "AI"
    private int[][] board;
    private int[] latestMove = new int[2];
    private String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public Game(int nRows, int nCols, int chosenGameMode) {
        board = new int[nRows][nCols];
        gameMode = chosenGameMode;
        
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int[] getMove(int player) {
        boolean coordinateExist = false;
        boolean coordinateEmpty = false;
        int[] coordinate = new int[2];
        while (!coordinateExist || !coordinateEmpty) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter coordinates:");
            String coordinates = scanner.nextLine();
            String col = coordinates.substring(0, 1).toLowerCase();
            try {
                int row = Integer.parseInt(coordinates.substring(1));
                coordinate[0] = alphabet.indexOf(col);
                coordinate[1] = row - 1;
                coordinateExist = isCoordinateExists(coordinate);
                if (coordinateExist) {
                    coordinateEmpty = isCoordinateTaken(coordinate);
                }
            } catch (NumberFormatException e) {
                if (coordinates.equals("quit")) {
                    System.out.println("See you later!");
                    System.exit(0);
                }
                System.out.println("Please enter valid coordinates!");
            }

        }
        latestMove[0] = coordinate[0];
        latestMove[1] = coordinate[1];
        return coordinate;
    }

    public int[] getAiMove(int player) {
        return null;
    }

    public void mark(int player, int row, int col) {
        board[row][col] = player;
    }

    public boolean hasWon(int player, int howMany) {
        if (hasWonRow(howMany) || hasWonCol(howMany) || hasWonCrossPos(howMany) || hasWonCrossNeg(howMany)) {
            printResult(player);
            return playAnotherGame();

        }
        return false;
    }

    public boolean playAnotherGame() {
        System.out.println("\nDo you want to play another game (Y/N)?");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine();
            if (answer.toUpperCase().equals("Y")) {
                FiveInARow.main(null);
            } else if (answer.toUpperCase().equals("N")) {
                System.out.println("See you later!");
                System.exit(0);
                return true;
            } else {
                System.out.println("\nPlease answer in format : (Y/N)");
            }
        }
    }
    public int[] getAiRandomMove(){
        System.out.println("Életre keltem!");
        int rows = board.length - 1;
        int cols = board[0].length - 1;
        int [] coordToMark = new int[2];
        coordToMark[0] = ThreadLocalRandom.current().nextInt(0, rows + 1);
        coordToMark[1] = ThreadLocalRandom.current().nextInt(0, cols + 1);
        while(!isCoordinateTaken(coordToMark)){
            coordToMark[0] = ThreadLocalRandom.current().nextInt(0, rows + 1);
            coordToMark[1] = ThreadLocalRandom.current().nextInt(0, cols + 1);
            System.out.println(Arrays.toString(coordToMark));
        }
        latestMove[0] = coordToMark[0];
        latestMove[1] = coordToMark[1];
        return coordToMark;

    }
    public boolean hasWonRow(int howMany) {
        int count = 1;
        for (int i = latestMove[1] + 1; i < board[latestMove[0]].length; i++) {
            if (board[latestMove[0]][latestMove[1]] == board[latestMove[0]][i]) {
                count++;
            } else {
                break;
            }
        }
        for (int i = latestMove[1] - 1; i >= 0; i--) {
            if (board[latestMove[0]][latestMove[1]] == board[latestMove[0]][i]) {
                count++;
            } else {
                break;
            }
        }
        System.out.println(count + "db a sorban"); 
        return (count >= howMany);
    }

    public boolean hasWonCol(int howMany) {
        int count = 1;
        for (int i = latestMove[0] + 1; i < board.length; i++) {
            if (board[latestMove[0]][latestMove[1]] == board[i][latestMove[1]]) {
                count++;
            } else {
                break;
            }
        }
        for (int i = latestMove[0] - 1; i >= 0; i--) {
            if (board[latestMove[0]][latestMove[1]] == board[i][latestMove[1]]) {
                count++;
            } else {
                break;
            }
        }
        System.out.println(count + "db az oszlopban");
        return (count >= howMany);
    }


    public boolean hasWonCrossPos(int howMany) {
        int count = 1;
        int j = latestMove[1] + 1;
        for (int i = latestMove[0] + 1; i < board.length; i++) {
            if (j >= board[i].length) {
                break;
            } else if (board[latestMove[0]][latestMove[1]] == board[i][j]) {
                count++;
                j++;
            } else {
                break;
            }
        }
        j = latestMove[1] - 1;
        for (int i = latestMove[0] - 1; i >= 0; i--) {
            if (j < 0) {
                break;
            } else if (board[latestMove[0]][latestMove[1]] == board[i][j]) {
                count++;
                j--;
            } else {
                break;
            }
        }
        System.out.println(count + "db a pozitív átlóban");
        return (count >= howMany);
    }

    public boolean hasWonCrossNeg(int howMany) {
        int count = 1;
        int j = latestMove[1] - 1;
        for (int i = latestMove[0] + 1; i < board[0].length; i++) {
            if (j < 0) {
                break;
            } else if (board[latestMove[0]][latestMove[1]] == board[i][j]) {
                System.out.println("Countot növelő koordináta: " + i + ":" + j);
                count++;
                j--;
            } else {
                break;
            }
        }
        j = latestMove[1] + 1;
        for (int i = latestMove[0] - 1; i >= 0; i--) {
            if (j <= board[i].length) {
                break;
            } else if (board[latestMove[0]][latestMove[1]] == board[i][j]) {
                System.out.println("Countot növelő koordináta: " + i + ":" + j);
                count++;
                j++;
            } else {
                break;
            }
        }
        System.out.println(count + "db a negatív átlóban");
        return (count >= howMany);
    }


    public boolean isFull() {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        System.out.print("\033[H\033[2J");
        System.out.print("  ");
        for (int i = 1; i < board[0].length; i++) {
            System.out.print(i + " ");
        }
        System.out.println(board[0].length);
        for (int i = 0; i < board.length; i++) {
            System.out.print(alphabet.substring(i, i + 1).toUpperCase() + " ");
            for (int cell : board[i]) {
                switch (cell) {
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
            System.out.println();
        }
    }

    public void printResult(int player) {
        String winner = (player == 1) ? "X" : "O";
        System.out.print(winner + " won!");
    }

    public void enableAi(int player) {
    }

    public void play(int howMany) {
        printBoard();
        while (!isFull()) {
            System.out.println("X's turn!");
            int[] coordinate = getMove(1);
            mark(1, coordinate[0], coordinate[1]);
            printBoard();
            if (hasWon(1, howMany)) {
                break;
            }
            if (gameMode == 1){
                System.out.println("O's turn!");
                coordinate = getMove(2);
            } else if (gameMode == 2){
                coordinate = getAiRandomMove();
            }
            mark(2, coordinate[0], coordinate[1]);
            printBoard();
            if (hasWon(2, howMany)) {
                break;
            }
            
        }
        playAnotherGame();

    }

    public boolean isCoordinateExists(int[] coordinate) {
        return board.length >= coordinate[0] && board[0].length >= coordinate[1];
    }

    public boolean isCoordinateTaken(int[] coordinate) {
        return (board[coordinate[0]][coordinate[1]] == 0);
    }

    



}
