package main;

import java.util.ArrayList;
import java.util.Random;


/**
 * A class to represent a RandomPlayer in Onitama.
 * RandomPlayer conducts its own moves.
 * Each player is associated with a specific char.
 */

public class PlayerRandom extends Player {

    protected Onitama game;

    /**
     * Constructs a new RandomPlayer for Onitama.
     * Random Player constructed based off a specific char.
     *
     * @param player an char for a Random player.
     */

    public PlayerRandom(char player) {

        super(player);
    }

    /**
     * Constructs a new RandomPlayer for Onitama.
     * Random Player constructed based off a specific char.
     *
     * @param game an Onitama game that random Player is apart of.
     * @param player an char for a Random player.
     */

    public PlayerRandom(char player, Onitama game) {
        this(player);
        this.game = game;
    }


    /**
     * Sets the game of random player.
     *
     *
     * @param game a game of Onitama for Random Player.
     */

    public void setGame(Onitama game) {
        this.game = game;
    }



    /**
     * returns a Turn of player randomly
     * Goes through all possible valid moves for Random Player and selects
     * one at random.
     *
     * @return A Turn for this player
     */

    @Override

    public Turn getTurn() {

        Onitama game = this.game;
        Player player = this.game.getWhoseTurn();
        ArrayList<Turn> combinations =  new ArrayList<>();

        if (player.getPlayer() == OnitamaBoard.G1) {

            return turnConstructors(player, game, combinations, 1);
        }

        else {

            return turnConstructors(player, game, combinations, 2);
        }


    }


    /**
     * returns a Turn of player randomly
     * Goes through all possible valid moves for Random Player and selects one
     * from the constructed array list of moves.
     *
     * @param player the Random Player.
     * @param game a game of Onitama.
     * @param combinations all possible combinations of moves.
     * @param order an integer representing player one or two can either be 1 or 2.
     *
     * @return A Turn for this player picked randomly.
     */


    public Turn turnConstructors(Player player, Onitama game, ArrayList<Turn> combinations, int order) {

        for (int i = 0; i < game.getBoard().length; i ++) {
            for (int x = 0; x < game.getBoard().length; x ++) {

                char token = game.getBoard()[i][x];
                if (token == player.getPlayer() || token == player.studentSymbol()) {

                    for (Style style: game.getStyles()) {
                        if (style.getOwner() == player.getPlayer()) {

                            for (Move move: style.getMoves()) {

                                int rowD;
                                int colD;

                                if (order == 1) {
                                    int change_row = (move.getRow()) * -1;
                                    int change_col = (move.getCol()) * -1;

                                    rowD = change_row + i;
                                    colD = change_col + x;
                                }

                                else {
                                    int change_row = move.getRow();
                                    int change_col = move.getCol();

                                    rowD = change_row + i;
                                    colD = change_col + x;
                                }

                                if (game.isLegalMove(i, x, rowD, colD)) {

                                    Turn turn = new Turn(i, x, rowD, colD, style.getName(), token);

                                    combinations.add(turn);


                                }

                            }


                        }
                    }

                }


            }
        }

        Random number = new Random();

        int num = number.nextInt(combinations.size());

        return combinations.get(num);
    }
}
