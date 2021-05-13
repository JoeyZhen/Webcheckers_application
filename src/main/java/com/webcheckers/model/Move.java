package com.webcheckers.model;

/**
 * The model of a Move.  Uses the Position class for location
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class Move {

    private Position start;
    private Position end;
    private boolean wasKinged;

    /**
     * Constructor for a move based off of Positions
     * @param start the starting position of a move
     * @param end the ending position of a move
     */
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
        this.wasKinged = false;
    }

    /**
     * Constructor for a move based off of index
     * @param startIndex the starting index of a move
     * @param endIndex the ending index of a move
     */
    public Move(int startIndex, int endIndex){
        this.start = new Position(startIndex);
        this.end = new Position(endIndex);
        this.wasKinged = false;
    }


    /**
     * Gets the starting position of a move
     * @return the starting position of the move
     */
    public Position getStart()
    {
        return this.start;
    }

    /**
     * Gets the ending position of a move
     * @return the ending position of the move
     */
    public Position getEnd()
    {
        return this.end;
    }

    /**
     * Reverses a position as if looking at the other side of the board
     * @return the reversed move
     */
    public Move reverse()
    {
        return new Move(31 - this.getStart().toArrayIndex(), 31 - this.getEnd().toArrayIndex());
    }

    /**
     * Tells if a move resulted in a single moving to the end of a board
     * @return if this move resulted in a king
     */
    public boolean wasKinged()
    {
        return this.wasKinged;
    }

    /**
     * Sets if a king was created in this move
     * @param wasKinged if this move resulted in a king
     */
    public void setWasKinged(boolean wasKinged)
    {
        this.wasKinged = wasKinged;
    }
}
