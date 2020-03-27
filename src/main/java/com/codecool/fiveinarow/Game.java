package com.codecool.fiveinarow;

import java.util.Scanner;

import java.util.concurrent.ThreadLocalRandom;
import java.util.*;


public class Game implements GameInterface {
    private int gameMode; // 1 -> player vs. player, 2-> player vs. "AI"
    private int[][] board;
    private int[] latestMove = new int[2];
    private String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private int[] aiLatestMove = new int[2];
    public int[] globalCoordinate = new int[2];

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
            System.out.println("Enter coordinates (RowColumn)!");
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
            } catch (Exception e) {
                if (coordinates.equals("quit")) {
                    System.out.println("See you later!");
                    System.exit(0);
                }
                System.out.println("Please enter valid coordinates (RowColumn)!");
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
                System.out.println("\nPlease answer in format : (Y/N)!");
            }
        }
    }

    public int[] getAiRandomMove() {
        int rows = board.length - 1;
        int cols = board[0].length - 1;
        int[] coordinateToMark = new int[2];
        coordinateToMark[0] = ThreadLocalRandom.current().nextInt(0, rows + 1);
        coordinateToMark[1] = ThreadLocalRandom.current().nextInt(0, cols + 1);
        while (!isCoordinateTaken(coordinateToMark)) {
            coordinateToMark[0] = ThreadLocalRandom.current().nextInt(0, rows + 1);
            coordinateToMark[1] = ThreadLocalRandom.current().nextInt(0, cols + 1);
            System.out.println(Arrays.toString(coordinateToMark));
        }
        latestMove[0] = coordinateToMark[0];
        latestMove[1] = coordinateToMark[1];
        return coordinateToMark;
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
        return (count >= howMany);
    }

    public boolean hasWonCrossNeg(int howMany) {
        int count = 1;
        int j = latestMove[1] - 1;
        for (int i = latestMove[0] + 1; i < board[0].length; i++) {
            if (j < 0) {
                break;
            } else if (board[latestMove[0]][latestMove[1]] == board[i][j]) {
                count++;
                j--;
            } else {
                break;
            }
        }
        j = latestMove[1] + 1;
        for (int i = latestMove[0] - 1; i >= 0; i--) {
            if (j >= board[i].length) {
                break;
            } else if (board[latestMove[0]][latestMove[1]] == board[i][j]) {
                count++;
                j++;
            } else {
                break;
            }
        }
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
        System.out.println("\nIt's a tie!");
        return true;
    }

    public void printBoard() {
        System.out.print("\033[H\033[2J");
        System.out.print("  ");
        for (int i = 1; i < board[0].length; i++) {
            if (i < 10) {
                System.out.print(" " + i + " ");
            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println(board[0].length);
        for (int i = 0; i < board.length; i++) {
            System.out.print(alphabet.substring(i, i + 1).toUpperCase() + " ");
            for (int cell : board[i]) {
                switch (cell) {
                    case 1:
                        System.out.print(" X ");
                        break;
                    case 2:
                        System.out.print(" O ");
                        break;
                    default:
                        System.out.print(" . ");
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

    public boolean checkRows(int[] move, int player, int howMany) {
        boolean refreshCoordinate = false;
        for (int i = move[1] - howMany + 1; i <= move[1]; i++) {
            int count = 0;
            for (int j = i; j <= i + howMany - 1; j++) {
                try {
                    if (board[move[0]][j] == player) {
                        count++;
                    } else if (board[move[0]][j] == 0) {
                        globalCoordinate[0] = move[0];
                        globalCoordinate[1] = j;
                        refreshCoordinate = true;
                    }
                } catch (Exception ignored) {
                }
            }
            if (count == howMany - 1 && refreshCoordinate) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCols(int[] move, int player, int howMany) {
        boolean refreshCoordinate = false;
        for (int i = move[0] - howMany + 1; i <= move[0]; i++) {
            int count = 0;
            for (int j = i; j <= i + howMany - 1; j++) {
                try {
                    if (board[j][move[1]] == player) {
                        count++;
                    } else if (board[j][move[1]] == 0) {
                        globalCoordinate[0] = j;
                        globalCoordinate[1] = move[1];
                        refreshCoordinate = true;
                    }
                } catch (Exception ignored) {
                }
            }
            if (count == howMany - 1 && refreshCoordinate) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCrossPos(int[] move, int player, int howMany) {
        boolean refreshCoordinate = false;
        int starterY = move[1] - howMany + 1;
        for (int i = move[0] - howMany + 1; i <= move[0]; i++) {
            int count = 0;
            int y = starterY;
            for (int j = i; j <= i + howMany - 1; j++) {
                try {
                    if (board[j][y] == player) {
                        count++;
                    } else if (board[j][y] == 0) {
                        globalCoordinate[0] = j;
                        globalCoordinate[1] = y;
                        refreshCoordinate = true;
                    }
                } catch (Exception ignored) {
                }
                y++;
            }
            starterY++;
            if (count == howMany - 1 && refreshCoordinate) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCrossNeg(int[] move, int player, int howMany) {
        boolean refreshCoordinate = false;
        int starterY = move[1] + howMany - 1;
        for (int i = move[0] - howMany + 1; i <= move[0]; i++) {
            int count = 0;
            int y = starterY;
            for (int j = i; j <= i + howMany - 1; j++) {
                try {
                    if (board[j][y] == player) {
                        count++;
                    } else if (board[j][y] == 0) {
                        globalCoordinate[0] = j;
                        globalCoordinate[1] = y;
                        refreshCoordinate = true;
                    }
                } catch (Exception ignored) {
                }
                y--;
            }
            starterY--;
            if (count == howMany - 1 && refreshCoordinate) {
                return true;
            }
        }
        return false;
    }

    public boolean checkDanger(int howMany) {
        for (int i = howMany; i > 0; i--) {
            if (checkRows(aiLatestMove, 2, i)) {
                return true;
            } else if (checkCols(aiLatestMove, 2, i)) {
                return true;
            } else if (checkCrossPos(aiLatestMove, 2, i)) {
                return true;
            } else if (checkCrossNeg(aiLatestMove, 2, i)) {
                return true;
            } else if (checkRows(latestMove, 1, i)) {
                return true;
            } else if (checkCols(latestMove, 1, i)) {
                return true;
            } else if (checkCrossPos(latestMove, 1, i)) {
                return true;
            } else if (checkCrossNeg(latestMove, 1, i)) {
                return true;
            }
        }
        return false;
    }

    public void play(int howMany) {
        printBoard();
        while (!isFull()) {
            System.out.println("X's turn!");
            globalCoordinate = getMove(1);
            mark(1, globalCoordinate[0], globalCoordinate[1]);
            printBoard();
            if (hasWon(1, howMany) || isFull()) {
                break;
            }
            if (gameMode == 1) {
                System.out.println("O's turn!");
                globalCoordinate = getMove(2);
            } else if (gameMode == 2) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!checkDanger(howMany)) {
                    globalCoordinate = getAiRandomMove();
                }
                aiLatestMove = globalCoordinate.clone();
                latestMove = aiLatestMove.clone();
            }
            mark(2, globalCoordinate[0], globalCoordinate[1]);
            printBoard();
            if (hasWon(2, howMany) || isFull()) {
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
