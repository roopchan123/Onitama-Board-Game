package main;

// TODO: Add Javadoc comments for all its methods. (Task 6.1)

/**
 * A class to represent a potential turn (piece selection and movement).
 */

public class Turn {
    private int rowO, colO, rowD, colD;
    private String styleName;
    private char player;


    /**
     * Constructs a move (piece placement) that knows its row, col
     * movement from origin to destination on the grid
     *
     * @param row_o	integer representing the origin row of the piece to move
     * @param col_o	integer representing the origin column of the piece to move
     * @param row_d	integer representing the destination row of the piece to move
     * @param col_d	integer representing the destination column of the piece to move
     * @param styleName string representing the style being used to move
     * @param player the character representing the player making this Turn
     */
    public Turn(int row_o, int col_o, int row_d, int col_d, String styleName, char player) {

        this.rowO = row_o;
        this.colO = col_o;
        this.rowD = row_d;
        this.colD = col_d;
        this.styleName = styleName;
        this.player = player;

    }

    // TODO: Complete the getters (Task 3.2)


    /**
     * Returns the original row of this Turn.
     *
     * @return this turns original row.
     */
    public int getRowO() {

        return this.rowO;
    }

    /**
     * Returns the original column of this Turn.
     *
     * @return this turns original column
     */
    public int getColO() {

        return this.colO;
    }

    /**
     * Returns the destination row of this Turn.
     *
     * @return this turns destination row.
     */
    public int getRowD() {

        return this.rowD;
    }


    /**
     * Returns the destination column of this Turn.
     *
     * @return this turns destination column.
     */
    public int getColD() {

        return this.colD;
    }

    /**
     * Returns the style of this turn.
     *
     * @return this turns style.
     */
    public String getStyle() {

        return this.styleName;
    }

    @Override

    public String toString() {

        return this.player +": " + "("+this.rowO + "," + this.colO + "," + this.styleName +") " + "-> "
                + "(" + this.rowD + "," + this.colD + ")";
    }


    // TODO: Complete the method to represent this Turn as a string (Task 3.3)
    public static void main(String[] args){
        // Create Turns
        Turn t1 = new Turn(0, 0, 1, 2, "dragon", 'X');
        Turn t2 = new Turn(3, 2, 2, 2, "crab", 'O');
        Turn t3 = new Turn(2, 2, 3, 1, "rooster", 'X');

        // Print Turns
        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t3);
    }
}
