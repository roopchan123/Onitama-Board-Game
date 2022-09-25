package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A subclass of player representing a human player.
 */

public class PlayerHuman extends Player{

    private static final String INVALID_MOVE_INPUT_MESSAGE = "Invalid number, please enter 0-4";
    private static final String INVALID_STYLE_INPUT_MESSAGE = "Invalid style, please enter one of this player's styles " +
            "(Dragon, Crab, Horse, Mantis, Rooster)";
    private static final BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Constructs a new human player for a game of Onitama.
     *
     * @param player a char associated with human player.
     */

    public PlayerHuman(char player) {
        super(player);
    }


    /**
     * Returns a valid turn for humanPlayer.
     *
     * @return The turn humanPlayer can do in a game of Onitama.
     */

    @Override
    public Turn getTurn() {
        String style_name = "";
        try {
            System.out.print("Choose your style:");
            style_name = PlayerHuman.stdin.readLine();
        } catch (IOException e) {
            System.out.println(INVALID_STYLE_INPUT_MESSAGE);
        }
        int row_o = getMove("row origin: ");
        int col_o = getMove("col origin: ");
        int row_d = getMove("row destination: ");
        int col_d = getMove("col destination: ");
        return new Turn(row_o, col_o, row_d, col_d, style_name, this.player);
    }


    /**
     * Returns an int depending on message.
     *
     * @param message a string representing the position on the Onitama board.
     *
     * @return The an int and -1 if invalid.
     */

    private int getMove(String message) {
        int move, lower = 0, upper = 4;
        while (true) {
            try {
                System.out.print(message);
                String line = PlayerHuman.stdin.readLine();
                move = Integer.parseInt(line);
                if (lower <= move && move <= upper) {
                    return move;
                } else {
                    System.out.println(INVALID_MOVE_INPUT_MESSAGE);
                }
            } catch (IOException e) {
                System.out.println(INVALID_MOVE_INPUT_MESSAGE);
                break;
            } catch (NumberFormatException e) {
                System.out.println(INVALID_MOVE_INPUT_MESSAGE);
            }
        }
        return -1;
    }
}