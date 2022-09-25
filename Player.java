package main;

import org.junit.jupiter.api.MethodOrderer;


/**
 * A class to represent a player in Onitama.
 * Each player is associated with a specific char.
 */

public class Player {

    protected final char player;


    /**
     * Constructs a new player for a game of Onitama.
     *
     * @param player an char for a player.
     */

    public Player(char player) {
        this.player = player;
    }


    /**
     * returns a Turn of player
     *
     * @return A Turn for this player
     */

    public Turn getTurn() {
        return null;
    }

    /**
     * returns player character.
     *
     * @return the char associated with player.
     */

    public char getPlayer() {
        return player;
    }


    /**
     * returns the monks token (char) depending on player.
     *
     * @return return 'o' if player is 'O' and 'x' if player is 'X'.
     */

    public char studentSymbol() {

        if (this.player == OnitamaBoard.G1) {
            return OnitamaBoard.M1;
        }

        else {
            return OnitamaBoard.M2;
        }
    }
}