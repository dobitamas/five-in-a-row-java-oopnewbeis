package com.codecool.fiveinarow;

import java.util.Scanner;

public class FiveInARow {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] boardSize = new int[2];
        int winSize = 0;
        boolean validBoardSize = false;
        System.out.println("Please enter board size in format(N N)");
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
                System.out.println("Invalid format!");
            }
        }
        boolean validWinSize = false;
        System.out.println("Please enter win size!");
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
                System.out.println("Invalid format!");
            }
        }

        Game game = new Game(boardSize[0], boardSize[1]);
        game.play(winSize);
    }
}