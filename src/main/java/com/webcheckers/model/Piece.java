package com.webcheckers.model;

/**
 * Enum for piece types
 */
enum Type
{
    SINGLE,
    KING
}

/**
 * The model of the checkers pieces
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class Piece {

    private Type type;
    private Color color;

    /**
     * Piece Constructor
     * @param type Type of piece to be created
     * @param color Color of piece to be created
     */
    public Piece(Type type, Color color)
    {
        this.type = type;
        this.color = color;
    }

    /**
     * Getter function for Piece Type
     * @return the type of piece
     */
    public Type getType()
    {
        return this.type;

    }

    /**
     * Getter function for piece color
     * @return the color of the piece
     */
    public Color getColor()
    {
        return this.color;
    }

    /**
     * Promotes a piece to a king
     */
    public void promote()
    {
        this.type = Type.KING;
    }

    /**
     * Demotes a piece to a pawn.
     */
    public void demote(){
        this.type = Type.SINGLE;
    }

}
