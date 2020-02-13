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
        System.out.println("3 I am too afraid to play");
        int chosenOpponent = input.nextInt();
        if (chosenOpponent == 1){
            int [] parameters = choseSize();
            Game game = new Game(parameters[0], parameters[1]);
            game.play(parameters[2], false);
        } else if (chosenOpponent == 2){
            int [] parameters = choseSize();
            Game game = new Game(parameters[0], parameters[1]);
            game.play(parameters[2], true);
        } else if (chosenOpponent == 3){
            System.exit(0);
        }

    }
    public static int[] choseSize(){
        Scanner input = new Scanner(System.in);
        System.out.println("How many rows you want to see?");
        int rows = input.nextInt();
        System.out.println("And how many columns?");
        int cols = input.nextInt();
        int howMany = 5;
        return new int[] {rows, cols, howMany};
    }

}
