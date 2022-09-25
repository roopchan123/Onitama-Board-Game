package main;

/**
 * An main.Onitama game class consisting of a game board, and keeping track of
 * which player's turn it currently is and some statistics about the game (e.g.
 * how many tokens each player has). It knows who the winner of the game is, and
 * when the game is over.
 */
public class Onitama {
    public static final int DIMENSION = 5; // This is a 5x5 game
    private OnitamaBoard board = new OnitamaBoard(Onitama.DIMENSION); // The main game board

    private final Player player1;
    private final Player player2;
    private Player whoseTurn; // player1 moves first!

    /**
     * Constructs a game of Onitama by creating 2 new players. Sets whoseTurn to
     * player1
     */
    public Onitama() {
        this.player1 = new Player(OnitamaBoard.G1);
        this.player2 = new Player(OnitamaBoard.G2);
        this.whoseTurn = this.player1;
    }

    /**
     * Constructs a game of Onitama with 2 players passed in as parameters Sets
     * whoseTurn to player1
     *
     * @param player1 the first player, G1
     * @param player2 the second player, G2
     */
    public Onitama(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.whoseTurn = this.player1;
    }

    /**
     * Constructs a game of Onitama by creating 2 new players. Sets whoseTurn to
     * player1 Sets the dimension of Onitama to the passed in dimension if valid.
     * The dimension must be odd and greater than or equal to 5.
     *
     * @param dimension the dimension of this Onitama
     */
    public Onitama(int dimension) {

        this();
        if (dimension >= DIMENSION && dimension % 2 != 0) {
            this.board = new OnitamaBoard(dimension);
        }


    }

    /**
     * Constructs a game of Onitama with 2 players passed in as parameters Sets
     * whoseTurn to player1 Sets the dimension of Onitama to the passed in dimension
     * if valid. The dimension must be odd and greater than or equal to 5.
     *
     * @param player1   the first player, G1
     * @param player2   the second player, G2
     * @param dimension the dimension of this Onitama
     */
    public Onitama(Player player1, Player player2, int dimension) {

        this(player1, player2);
        if (dimension >= DIMENSION && dimension % 2 != 0) {
            this.board = new OnitamaBoard(dimension);
        }
    }

    /**
     * Returns the Player for player1, player2 depending on who moves next.
     *
     * @return the Player for player1, player2
     */
    public Player getWhoseTurn() {

        return this.whoseTurn;
    }

    /**
     * Given one player, returns the other player. If the given player is invalid,
     * returns null.
     *
     * @param player Player object for a player - should be either player1 or
     *               player2
     * @return player1 or player2, the opposite of the given player, or null if the
     *         given player object was invalid
     */
    public Player otherPlayer(Player player) {

        if (player == this.player1) {
            return this.player2;
        }

        else if (player == this.player2) {
            return this.player1;
        }

        else {
            return null;
        }
    }

    /**
     * Checks if a move with the given parameters would be legal based on the origin
     * and destination coordinates. This method should specifically check for the
     * following 3 conditions: 1) The movement is in the bounds of this game's
     * board. 2) The correct piece is being moved based on the current player's
     * turn. 3) The destination is valid. A player CANNOT move on top of their own
     * piece.
     *
     * @param rowO integer representing the row origin
     * @param colO integer representing the column origin
     * @param rowD integer representing the row destination
     * @param colD integer representing the column destination
     * @return true if this is a legal move, false otherwise
     */
    public boolean isLegalMove(int rowO, int colO, int rowD, int colD) {

        if (inBoundsBoard(rowO, colO, this.board) && inBoundsBoard(rowD, colD, this.board)) {
            if ((this.board.getToken(rowO, colO) == this.whoseTurn.getPlayer()) ||
                    (this.board.getToken(rowO, colO) == this.whoseTurn.studentSymbol())) {
                return ((this.board.getToken(rowD, colD) != this.whoseTurn.getPlayer()) &&
                        (this.board.getToken(rowD, colD) != this.whoseTurn.studentSymbol()));

            }

        }

        return false;

    }

    public boolean inBoundsBoard(int row, int col, OnitamaBoard board) {

        return ((0 <= row && row < board.getDimension()) &&
                0 <= col && col < board.getDimension());

    }

    /**
     * Attempts to make a move for player1 or player2 (depending on whose turn it
     * is) from position rowO, colO to position rowD, colD. Returns true if the move
     * was successfully made.
     *
     * @param rowO      integer representing the row origin
     * @param colO      integer representing the column origin
     * @param rowD      integer representing the row destination
     * @param colD      integer representing the column destination
     * @param styleName string representing the name of the movement style
     * @return true if the move was successfully made, false otherwise
     */
    public boolean move(int rowO, int colO, int rowD, int colD, String styleName) {
        // Hint: Use existing methods (from main.Onitama and main.OnitamaBoard) as
        // helpers here
        // This method should also modify whoseTurn it is to be the next player

        if (isLegalMove(rowO, colO, rowD, colD)) {
            for (Style sty: this.board.getStyles()) {
                if ((sty.getName().equals(styleName)) && (sty.getOwner() == this.whoseTurn.getPlayer())) {
                    int[] lst = differenceDimension(rowO, colO, rowD, colD);
                    int[] lst2 = {lst[0] * -1, lst[1] * -1};
                    for (Move move: sty.getMoves()) {
                        if ((move.isPosition(lst)) && (this.whoseTurn == this.player2)) {
                            completeMove(rowO, colO, rowD, colD, sty);
                            return true;
                        }
                        else if (move.isPosition(lst2) && this.whoseTurn == player1) {
                            completeMove(rowO, colO, rowD, colD, sty);
                            return true;
                        }
                    }
                }
            }

        }

        return false;
    }

    /**
     * Takes the token at rowO and colO and moves it to rowD colD depending
     * on the style
     *
     * @param rowO      integer representing the row origin
     * @param colO      integer representing the column origin
     * @param rowD      integer representing the row destination
     * @param colD      integer representing the column destination
     * @param style string representing the name of the movement style
     * @return void return
     */
    public void completeMove(int rowO, int colO, int rowD, int colD, Style style) {
        char token = this.board.getToken(rowO, colO);
        this.board.setToken(rowD, colD, token);
        this.board.setToken(rowO, colO, OnitamaBoard.EMPTY);
        this.board.exchangeStyle(style);
        this.whoseTurn = this.otherPlayer(this.whoseTurn);

    }

    /**
     * returns the row and column of the difference of rowO and rowD
     * and the second element being the difference of colO and colD.
     *
     * @param rowO      integer representing the row origin
     * @param colO      integer representing the column origin
     * @param rowD      integer representing the row destination
     * @param colD      integer representing the column destination
     * @return two element tuple representing the row and column
     */
    public int[] differenceDimension(int rowO, int colO, int rowD, int colD) {

        int[] lst = new int[2];
        lst[0] = rowD - rowO;
        lst[1] = colD - colO;

        return lst;

    }

    /**
     * Returns the winner of the game if the game is over, or the board token for
     * EMPTY if the game is not yet finished. As per main.Onitama's rules, the
     * winner of the game is the player whose Grandmaster reaches the middle column
     * on the opposite row from the start position, OR the player who captures the
     * other player's Grandmaster.
     *
     * @return the character of the winning player's Grandmaster (G1 or G2) or the
     *         token for EMPTY if the game is not finished.
     */
    public char getWinner() {

        // Hint: Use existing methods as helpers here
        // Use main.OnitamaBoard.G1, main.OnitamaBoard.G2, etc. to access the board
        // tokens

        int middle = this.board.getDimension() / 2;

        if (this.board.getToken(0, middle) == this.player2.getPlayer()) {
            return OnitamaBoard.G2;
        }

        else if (this.board.getToken(this.board.getDimension() - 1, middle) == this.player1.getPlayer()) {
            return OnitamaBoard.G1;
        }

        else {
            int count = 0;
            char[] masters = new char[2];
            for (char[] row: this.board.getBoard()) {
                for (char col: row) {
                    if (col == OnitamaBoard.G1 || col == OnitamaBoard.G2) {
                        masters[count] = col;
                        count++;
                    }
                }
            }
            if (count >= 2) {
                return ' ';
            }
            else if (count == 0) {
                return ' ';
            }
            else {

                return masters[0];
            }
        }
    }


    /**
     * Takes the token at rowO and colO and moves it to rowD colD depending
     * on the style
     *
     * @param chr the char associated with a specific player
     * @return the player who is associated with the specified char.
     */
    public char getCharacter(char chr) {

        if (this.player1.getPlayer() == chr) {
            return this.player1.getPlayer();
        }

        else {
            return this.player2.getPlayer();
        }
    }

    /*
     * DO NOT CHANGE ANYTHING BELOW!!! Changing things below will mess up the Auto
     * tests and result in a MAJOR LOSS OF MARKS!!!
     */

    /**
     * DO NOT MODIFY THIS!!! Returns string representation of this board.
     *
     * @return a string representation of this board
     */
    // NOTE: This method is already done for you. DO NOT MODIFY THIS!!
    public String getBoardString() {
        return this.board.toString() + "\n";
    }

    /**
     * DO NOT MODIFY THIS!!! Returns string representation of a player's available
     * styles.
     *
     * @param player the grandmaster of the player whose styles are printed, or
     *               EMPTY for the fifth style
     * @return a string representation of the style
     */
    // NOTE: This method is already done for you. DO NOT MODIFY THIS!!
    public String getStylesString(char player) {
        String s = "";
        if (player == OnitamaBoard.EMPTY) {
            s += "Fifth style: \n";
        } else {
            s += "Player " + player + " styles: \n";
        }
        for (int i = 0; i < 5; i++) {
            if (this.board.getStyles()[i].getOwner() == player) {
                s += this.board.getStyles()[i].toString() + "\n";
            }
        }
        return s;
    }

    /**
     * DO NOT MODIFY THIS!!! Get the different styles of movement in main.Onitama
     *
     * @return an array of all styles of movement.
     */
    public Style[] getStyles() {
        return this.board.getStyles();
    }

    /**
     * DO NOT MODIFY THIS!!! Gets a copy of this OnitamaBoard from
     * OnitamaBoard.getBoard()
     *
     * @return a copy of the current game board.
     */
    public char[][] getBoard() {
        return this.board.getBoard();
    }

    /**
     * DO NOT MODIFY THIS!!! Construct a new OnitamaBoard with the given size and
     * preset board.
     * 
     * @param size  the dimension of the OnitamaBoard
     * @param board the preset board state of the OnitamaBoard
     */
    public void setBoard(int size, char[][] board) {
        this.board = new OnitamaBoard(size, board);
    }

    /**
     * Main function which creates and runs a random main.Onitama game.
     */
    // DO NOT MODIFY THIS!!
    // Run this to test the current class. We speedrun a game of Onitama in 4 moves.
    // The output should match EXACTLY to onitamaMainOutput.txt
    public static void main(String[] args) {
        // Speed run a game of Onitama where player 'O' wins
        Onitama o = new Onitama();
        int[][] moves = { { 0, 2, 1, 2 }, { 4, 2, 3, 3 }, { 1, 2, 2, 4 }, { 3, 3, 2, 4 } };
        String[] styleNames = { "crab", "rooster", "dragon", "mantis" };
        int rowO, colO, rowD, colD;
        String styleName;

        for (int i = 0; i < moves.length; i++) {
            System.out.println(o.getBoardString() + o.getWhoseTurn().getPlayer() + " moves next");
            rowO = moves[i][0];
            colO = moves[i][1];
            rowD = moves[i][2];
            colD = moves[i][3];
            styleName = styleNames[i];
            o.move(rowO, colO, rowD, colD, styleName);
            // Print the move made, board, who's turn it is, and player styles
            System.out.println("makes move " + styleName + ": (" + rowO + ", " + colO + ", " + rowD + ", " + colD + ")");
            System.out.println(o.getBoardString() + o.getWhoseTurn().getPlayer() + " moves next");
            System.out.println(o.getStylesString(OnitamaBoard.EMPTY));
            System.out.println(o.getStylesString(OnitamaBoard.G1));
            System.out.println(o.getStylesString(OnitamaBoard.G2));
        }

        // Print final board state and who won
        System.out.println(o.getBoardString());
        System.out.println("==============================\nGame Finished: " + o.getWinner() + " won the game!");
    }
}