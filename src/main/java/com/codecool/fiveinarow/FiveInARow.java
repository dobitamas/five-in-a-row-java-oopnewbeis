package com.codecool.fiveinarow;
import java.util.Arrays;

public class FiveInARow {

    public static void main(String[] args) {
        Game game = new Game(11, 11);
	int[][] board = game.getBoard();
	System.out.println(Arrays.deepToString(board));
        game.play(5);
	game.getMove(0);
    }

}
