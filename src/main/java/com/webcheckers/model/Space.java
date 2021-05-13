package com.webcheckers.model;
/**
 * The model for the spaces on the board
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class Space {

    private int cellIdx;
    private boolean valid;
    private Piece piece;

    /**
     * Constructor for a Space
     * @param cellIdx horizontal location of the cell
     * @param valid if the space is a valid location for a piece to be placed on
     * @param piece the piece that is located on the space (null for none)
     */
    public Space(int cellIdx, boolean valid, Piece piece)
    {

        this.cellIdx = cellIdx;
        this.valid = valid;
        this.piece = piece;
    }

    /**
     * Getter Method for CellIdx
     * @return horizontal location of the cell
     */
    public int getCellIdx()
    {
        return this.cellIdx;

    }

    /**
     * checks if the space is a valid space to move to
     * @return true if the space is valid, false otherwise
     */
    public boolean isValid()
    {
        return this.piece == null && this.valid;

    }

    /**
     * Getter for the piece located on the space
     * @return the piece if there is one, null otherwise
     */
    public Piece getPiece() {
        return this.piece;
    }

}
