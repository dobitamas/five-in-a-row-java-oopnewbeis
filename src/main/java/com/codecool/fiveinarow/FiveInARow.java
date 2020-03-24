package com.codecool.fiveinarow;
import java.util.Arrays;

public class FiveInARow {

    public static void main(String[] args) {
        Game game = new Game(10, 10);
	int[][] board = game.getBoard();
	System.out.println(Arrays.deepToString(board));
        game.play(5);
        int[] coord = game.getMove(1);
	game.mark(1, coord[0], coord[1]);
    }

}
