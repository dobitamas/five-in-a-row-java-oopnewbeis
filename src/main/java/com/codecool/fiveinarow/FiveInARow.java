package com.codecool.fiveinarow;
import java.util.Scanner;
public class FiveInARow {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("   ______                      __        ");
        System.out.println("  / ____/___  ____ ___  ____  / /____  __");
        System.out.println(" / / __/ __ \\/ __ `__ \\/ __ \\/ //_/ / / /");
        System.out.println("/ /_/ / /_/ / / / / / / /_/ / ,< / /_/ / ");
        System.out.println("\\____/\\____/_/ /_/ /_/\\____/_/|_|\\__,_/ ");
        System.out.println("Welcome to the world of Gomoku!");
        System.out.println("Choose your opponent!");
        System.out.println("1 Other player");
        System.out.println("2 The mighty computer");
        int chosenOpponent = input.nextInt();
        if (chosenOpponent == 1){
            Game game = new Game(6, 5);
            game.play(3);
        } else if (chosenOpponent == 2){
            System.out.println("The computer is not wishes to play against you, please check back later");
        }

    }

}
