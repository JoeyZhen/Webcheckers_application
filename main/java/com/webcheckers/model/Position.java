package com.webcheckers.model;

/**
 * The model of a position on a board.  Used for moves
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class Position {

    private int row;
    private int cell;

    /**
     * Error Messages for improper arguments
     */
    protected static final String rowOutOfBoundsHigh = "Position Row is above 7\n";
    protected static final String rowOutOfBoundsLow = "Position Row is below 0\n";
    protected static final String cellOutOfBoundsHigh = "Position Cell is above 7\n";
    protected static final String cellOutOfBoundsLow = "Position Cell is below 0\n";
    protected static final String indexOutOfBoundsHigh = "Position Index is above 32\n";
    protected static final String indexOutOfBoundsLow = "Position Index is below 0\n";

    /**
     * Constructor for a Position
     * @param row the row of the position (y-index)
     * @param cell the cell of the position (x-index)
     */
    public Position(int row, int cell){
        try {
            if(row > 7)
                throw new Exception(rowOutOfBoundsHigh);
            if(row < 0)
                throw new Exception(rowOutOfBoundsLow);
            if(cell > 7)
                throw new Exception(cellOutOfBoundsHigh);
            if(cell < 0)
                throw new Exception(cellOutOfBoundsLow);
            this.row = row;
            this.cell = cell;
        }catch(Exception e){
            System.err.print(e.getMessage());
            this.row = 0;
            this.cell = 0;
        }
    }
    /**
     * Constructor for Position using array index
     * @param arrayIndex the index of a 32 length array
     */
    public Position(int arrayIndex){
        // Check index bounds
        try {

            if (arrayIndex > 31) {
                arrayIndex = 0;
                throw new Exception(indexOutOfBoundsHigh);
            }

            if (arrayIndex < 0) {
                arrayIndex = 32;
                throw new Exception(indexOutOfBoundsLow);
            }
        }catch(Exception e)
        {
            System.err.print(e.getMessage());
        }

        this.row = arrayIndex/4;

        if(row % 2 == 0){
            this.cell = (arrayIndex % 4) * 2 + 1;
        }
        else{
            this.cell = (arrayIndex % 4) * 2;
        }
    }
    /**
     * Getter method for row (y-index)
     * @return the row of the position
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Getter method for cell (x-index)
     * @return the cell of the position (x-index)
     */
    public int getCell(){
        return this.cell;
    }

    /**
     * convert a position to an array index in a 32 length array
     * @return the index of the position.
     */
    public int toArrayIndex(){
        return this.row * 4 + this.cell/2;
    }
}
