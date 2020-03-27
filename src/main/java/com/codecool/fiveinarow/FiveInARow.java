package com.codecool.fiveinarow;

import java.util.Scanner;

public class FiveInARow {

    public static void main(String[] args) {
        System.out.print("\033[H\033[2J");
        Scanner scanner = new Scanner(System.in);
        int[] boardSize = new int[2];
        int winSize = 0;
        int gameMode = 0;
        boolean validBoardSize = false;
        boolean validWinSize = false;
        boolean validGameMode = false;
        System.out.println("   _____  ____  __  __  ____  _  ___    _ \n" +
                "  / ____|/ __ \\|  \\/  |/ __ \\| |/ / |  | |\n" +
                " | |  __| |  | | \\  / | |  | | ' /| |  | |\n" +
                " | | |_ | |  | | |\\/| | |  | |  < | |  | |\n" +
                " | |__| | |__| | |  | | |__| | . \\| |__| |\n" +
                "  \\_____|\\____/|_|  |_|\\____/|_|\\_\\\\____/ \n" +
                "                                          ");
        System.out.println("Please enter board size in format (N N)!");
        while (!validBoardSize) {
            try {
                String input = scanner.next();
                boardSize[0] = Integer.parseInt(input);
                input = scanner.next();
                boardSize[1] = Integer.parseInt(input);
                if (boardSize[0] >= 3 && boardSize[1] >= 3) {
                    validBoardSize = true;
                } else {
                    System.out.println("Board sides lengths must be minimum 3!");
                }
            } catch (Exception e) {
                System.out.println("Invalid format (N N)!");
            }
        }

        System.out.println("Please enter win size in format (N)!");
        while (!validWinSize) {
            try {
                String input = scanner.next();
                winSize = Integer.parseInt(input);
                if (winSize >= 3 && (winSize <= boardSize[0] || winSize <= boardSize[1])) {
                    validWinSize = true;
                } else {
                    System.out.println("Win size must be between 3 and the length of the greatest side of the board!");
                }
            } catch (Exception e) {
                System.out.println("Invalid format (N)!");
            }
        }
        System.out.println("Please choose a game mode: \n1) Player vs. Player (type: 1) \n2) Player vs. AI (type: 2)");
        while (!validGameMode) {
            try {
                String input = scanner.next();
                gameMode = Integer.parseInt(input);
                if (gameMode == 1 || gameMode == 2) {
                    validGameMode = true;
                } else {
                    System.out.println("Please enter in format (N)!");
                }
            } catch (Exception e) {
                System.out.println("Invalid format (N)!");
            }
        }
        Game game = new Game(boardSize[0], boardSize[1], gameMode);
        game.play(winSize);
    }
}
