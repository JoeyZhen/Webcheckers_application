package com.webcheckers.model;


import java.util.Arrays;
import java.util.Iterator;

/**
 * The model of the checkers board
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class Board implements Iterable<Row>{
    // Oriented For White
    private Piece boardState[];

    private String VALID_SIMPLE_MOVE = "Valid Simple Move";
    private String INVALID_SIMPLE_MOVE = "Invalid Simple Move";
    private String INVALID_SIMPLE_JUMP = "Invalid Jump";
    private String VALID_SIMPLE_JUMP = "Valid Simple Jump";
    private String INDEX_OUT_OF_BOUNDS = "Index Out of Bounds";

    /**
     * Board Constructor for new Board
     */
    public Board()
    {
        this.boardState = new Piece[32];
        this.setInitialState();
    }

    /**
     * Board Constructor for a game in progress
     * @param boardState Array of Pieces that shows current Board State
     */
    public Board(Piece boardState[]){
        this.boardState = boardState;
    }

    /**
     * Returns a new Board that is a 180 degree rotation of the last
     * @return Rotated Board
     */
    public Board flipBoard()
    {
        Piece temp[] = new Piece[32];
        for(int i = 0; i < 32; i++){
            temp[31 - i] = this.boardState[i];
        }
        return new Board(temp);
    }

    /**
     * Sets the board state to that of a new board
     */
    private void setInitialState()
    {
        for(int i = 0; i < 12; i++)
        {
            this.boardState[i] = new Piece(Type.SINGLE, Color.WHITE);
        }

        for(int i = 20; i < 32; i++)
        {
            this.boardState[i] = new Piece(Type.SINGLE, Color.RED);
        }
    }

    /**
     * Iterator for the BoardÚÚ
     * @return Iterator of Rows for Board
     */
    public Iterator<Row> iterator()
    {
        Row temp[] = new Row[8];
        for(int i = 0; i < 8; i++){
            temp[i] = new Row(Arrays.copyOfRange(this.boardState,i*4, (i+1) *4), i);
        }
        return Arrays.asList(temp).iterator();
    }

    /**
     * Validates a Move
     * @param move Move to validate
     * @return if the move is acceptable
     */
    public Message validateMove(Move move, Color color)
    {
        int endPosition = move.getEnd().toArrayIndex();
        int startPosition = move.getStart().toArrayIndex();

        // check that start and end are valid positions
        if(startPosition < 0 || startPosition > 31 || endPosition < 0 || endPosition > 31){
            return new Message(Message.ERROR, INDEX_OUT_OF_BOUNDS);
        }

        // check that there isn't a piece in the end position
        if(this.boardState[endPosition]!= null ){
            return new Message(Message.ERROR, "End position is occupied");
        }

        if(this.boardState[startPosition] == null)
        {
            return new Message(Message.ERROR, "Move not allowed");
        }

        // make sure you're moving your own color
        if(this.boardState[startPosition].getColor() != color)
        {
            return new Message(Message.ERROR, "Piece does not belong to player");
        }

        // if there is a jump available, you must do a jump
        if(isJumpAvailable(color))
        {
            return validateSimpleJump(startPosition,endPosition,color);
        }

        // if there is no jump, validate a simple move
        return validateSimpleMove(startPosition, endPosition);
    }

    /**
     * Validates a simpleMove
     * @param startPosition the startPosition of a Move
     * @param endPosition the endPosition of a Move
     * @return Message of the results
     */
    private Message validateSimpleMove(int startPosition, int endPosition)
    {
        // check that start and end are valid positions
        if(startPosition < 0 || startPosition > 31 || endPosition < 0 || endPosition > 31){
            return new Message(Message.ERROR, INDEX_OUT_OF_BOUNDS);
        }

        // Check if only moving one row
        if(indexToRow(endPosition)!= indexToRow(startPosition)-1){
            if(this.boardState[startPosition].getType() != Type.KING || indexToRow(endPosition) != indexToRow(startPosition)+1)
            {
                return new Message(Message.ERROR, INVALID_SIMPLE_JUMP);
            }
        }

        // Check to make sure movement is allowed
        if(toTopLeft(startPosition) == endPosition || toTopRight(startPosition) == endPosition){
            return new Message(Message.INFO, VALID_SIMPLE_MOVE);
        }

            // if king check move backwards
        if(this.boardState[startPosition].getType() == Type.KING){
            if(toBottomLeft(startPosition) == endPosition || toBottomRight(startPosition) == endPosition){
                return new Message(Message.INFO, VALID_SIMPLE_MOVE);
            }
        }
        return new Message(Message.ERROR, INVALID_SIMPLE_MOVE);
    }

    /**
     * Validates if a move is a jump
     * @param startPosition start position of move
     * @param endPosition end position of move
     * @param color color of player making move
     * @return true if move is a valid jump, false otherwise.
     */
    private Message validateSimpleJump(int startPosition, int endPosition, Color color)
    {
        if(startPosition < 0 || startPosition > 31 || endPosition < 0 || endPosition > 31){
            return new Message(Message.ERROR, INDEX_OUT_OF_BOUNDS);
        }
        // Check if only moving one row
        if(indexToRow(endPosition)!= indexToRow(startPosition)-2){
            if(this.boardState[startPosition].getType() != Type.KING || indexToRow(endPosition) != indexToRow(startPosition)+2)
            {
                return new Message(Message.ERROR, INVALID_SIMPLE_JUMP);
            }
        }

        if(this.boardState[endPosition] != null){
            return new Message(Message.ERROR,INVALID_SIMPLE_JUMP);
        }

        if(toJumpTopLeft(startPosition) == endPosition){
            if(this.boardState[toTopLeft(startPosition)] != null && this.boardState[toTopLeft(startPosition)].getColor() != color){
                return new Message(Message.INFO, VALID_SIMPLE_JUMP);
            }
            else{
                return new Message(Message.ERROR, INVALID_SIMPLE_JUMP);
            }
        }
        if(toJumpTopRight(startPosition) == endPosition) {
            if(this.boardState[toTopRight(startPosition)] != null && this.boardState[toTopRight(startPosition)].getColor() != color) {
                return new Message(Message.INFO, VALID_SIMPLE_JUMP);
            }
            else {
                return new Message(Message.ERROR, INVALID_SIMPLE_JUMP);
            }
        }

        // if king check move backwards
        if(this.boardState[startPosition].getType() == Type.KING){
            if(toJumpBottomLeft(startPosition) == endPosition) {
                if(this.boardState[toBottomLeft(startPosition)] != null && this.boardState[toBottomLeft(startPosition)].getColor() != color){
                    return new Message(Message.INFO, VALID_SIMPLE_JUMP);
                }
                else{
                    return new Message(Message.ERROR, INVALID_SIMPLE_JUMP);
                }
            }

            if(toJumpBottomRight(startPosition) == endPosition){
                if(this.boardState[toBottomRight(startPosition)] != null && this.boardState[toBottomRight(startPosition)].getColor() != color){
                    return new Message(Message.INFO, VALID_SIMPLE_JUMP);
                }
                else{
                    return new Message(Message.ERROR, INVALID_SIMPLE_JUMP);
                }
            }
        }
        return new Message(Message.ERROR, INVALID_SIMPLE_JUMP);
    }

    /**
     * checks if a jump is available for a given color
     * @param color color to check the move for
     * @return if a jump is available
     */
    private boolean isJumpAvailable(Color color)
    {
        for(int position = 0; position < 32; position++)
        {
            if(isJumpAvailable(color, position))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * checks if a jump is available for a given index for a given player
     * @param color color of the player
     * @param position index of the position of the piece
     * @return if a jump is available
     */
    public boolean isJumpAvailable(Color color, int position){
        if(this.boardState[position] != null && this.boardState[position].getColor() == color)
        {
            if(validateSimpleJump(position, toJumpTopLeft(position), color).getType().equals(Message.INFO)){
                return true;
            }
            if(validateSimpleJump(position, toJumpTopRight(position), color).getType().equals(Message.INFO)){
                return true;
            }
            if(this.boardState[position].getType() == Type.KING){
                if(validateSimpleJump(position, toJumpBottomLeft(position), color).getType().equals(Message.INFO)){
                    return true;
                }
                if(validateSimpleJump(position, toJumpBottomRight(position), color).getType().equals(Message.INFO)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the game is over
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver(Color color)
    {
        if(this.isPlayerOutOfPieces()){
            return true;
        }
        if(this.isJumpAvailable(color)){
            return false;
        }
        if(this.isSimpleMoveAvailable(color)){
            return false;
        }

        return true;
    }

    /**
     * Checks if a player is out of pieces
     * @return true if a player has no pieces, false otherwise
     */
    private boolean isPlayerOutOfPieces()
    {
        int redCount = 0;
        int whiteCount = 0;
        for(int i = 0; i< 32; i++)
        {
            if(this.boardState[i] != null)
            {
                if(this.boardState[i].getColor() == Color.RED){
                    redCount++;
                }else if(this.boardState[i].getColor() == Color.WHITE){
                    whiteCount++;
                }
            }
        }

        return redCount == 0 || whiteCount == 0;
    }

    /**
     * Checks if a simple move is available
     * @param color color of player to check
     * @return true if a move is available, false otherwise.
     */
    private boolean isSimpleMoveAvailable(Color color) {
        for(int position = 0; position < 32; position++) {
            if(this.boardState[position].getColor() == color)
            {
                if(validateSimpleMove(position, toTopLeft(position)).getType().equals(Message.INFO)){
                    return true;
                }
                if(validateSimpleMove(position, toTopRight(position)).getType().equals(Message.INFO)){
                    return true;
                }
                if(this.boardState[position].getType() == Type.KING){
                    if(validateSimpleMove(position, toBottomLeft(position)).getType().equals(Message.INFO)){
                        return true;
                    }
                    if(validateSimpleMove(position, toBottomRight(position)).getType().equals(Message.INFO)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Enacts a simple jump move
     * @param startPosition start position of the jump
     * @param endPosition end position of the jump
     */
    private void makeSimpleJump(int startPosition, int endPosition)
    {
        this.boardState[endPosition] = this.boardState[startPosition];
        this.boardState[startPosition] = null;

        if(toJumpTopLeft(startPosition) == endPosition){
            this.boardState[toTopLeft(startPosition)] = null;
        }
        else if(toJumpTopRight(startPosition) == endPosition){
            this.boardState[toTopRight(startPosition)] = null;
        }
        else if(toJumpBottomLeft(startPosition) == endPosition){
            this.boardState[toBottomLeft(startPosition)] = null;
        }
        else if(toJumpBottomRight(startPosition) == endPosition){
            this.boardState[toBottomRight(startPosition)] = null;
        }
    }

    /**
     * Finds the piece captured by a jump
     * @param move the move to check
     * @return the Piece captured
     */
    public Piece simpleJumpCapture(Move move)
    {
        int startPosition = move.getStart().toArrayIndex();
        int endPosition = move.getEnd().toArrayIndex();

        if(toJumpTopLeft(startPosition) == endPosition){
            return this.boardState[toTopLeft(startPosition)];
        }
        else if(toJumpTopRight(startPosition) == endPosition){
            return this.boardState[toTopRight(startPosition)];
        }
        else if(toJumpBottomLeft(startPosition) == endPosition){
            return this.boardState[toBottomLeft(startPosition)];
        }
        else if(toJumpBottomRight(startPosition) == endPosition){
            return this.boardState[toBottomRight(startPosition)];
        }

        return null;
    }

    /**
     * Enacts a move to the board
     * @param move move to enact
     * @param color color that is making the move
     * @return Piece that was captured
     */
    public Message makeMove(Move move, Color color)
    {
        int startPosition = move.getStart().toArrayIndex();
        int endPosition = move.getEnd().toArrayIndex();
        if(isJumpAvailable(color)){
            if(validateSimpleJump(startPosition, endPosition, color).getType().equals(Message.INFO)){
               makeSimpleJump(startPosition, endPosition);
                if(indexToRow(endPosition) == 0){
                    this.boardState[endPosition].promote();
                }
               return new Message(Message.INFO, VALID_SIMPLE_JUMP);
            }
            return null;
        }
        if(validateSimpleMove(startPosition,endPosition).getType().equals(Message.INFO)){
            this.boardState[endPosition] = this.boardState[startPosition];
            this.boardState[startPosition] = null;
            if(indexToRow(endPosition) == 0){
                this.boardState[endPosition].promote();
            }
            return new Message(Message.INFO, VALID_SIMPLE_MOVE);
        }
        return null;
    }

    /**
     * backs up a given move
     * @param move move to undo
     * @param color the color of the player making the move
     * @param piece the piece that was captured if any
     */
    public void backUpMove(Move move, Color color, Piece piece)
    {
        int startPosition = move.getStart().toArrayIndex();
        int endPosition = move.getEnd().toArrayIndex();
        this.boardState[startPosition] = this.boardState[endPosition];
        if(move.wasKinged()){
            this.boardState[startPosition].demote();
        }
        this.boardState[endPosition] = null;

        if(toJumpTopLeft(startPosition) == endPosition){
            this.boardState[toTopLeft(startPosition)] = piece;
        }
        else if(toJumpTopRight(startPosition) == endPosition){
            this.boardState[toTopRight(startPosition)] = piece;
        }
        else if(toJumpBottomLeft(startPosition) == endPosition){
            this.boardState[toBottomLeft(startPosition)] = piece;
        }
        else if(toJumpBottomRight(startPosition) == endPosition){
            this.boardState[toBottomRight(startPosition)] = piece;
        }
    }


    /**
     * Returns if a move results in a king
     * @param move the move to check
     * @return if the moving piece turned to king
     */
    public boolean resultsInKing(Move move)
    {
        if(this.boardState[move.getStart().toArrayIndex()].getType() == Type.SINGLE) {
            return move.getEnd().getRow() == 0;
        }
        return false;
    }

    /**
     * get the position index to the top left of this one
     * @param position base position
     * @return position index of the position to the top left
     */
    private int toTopLeft(int position)
    {
        if(indexToRow(position) % 2 == 1)
        {
            return position - 5;
        }
        return position - 4;
    }

    /**
     * get the position index to the top right of this one
     * @param position base position
     * @return position index of the position to the top right
     */
    private int toTopRight(int position)
    {
        if(indexToRow(position) % 2 == 1)
        {
            return position - 4;
        }
        return position - 3;
    }

    /**
     * get the position index to the bottom left of this one
     * @param position base position
     * @return position index of the position to the bottom left
     */
    private int toBottomLeft(int position)
    {
        if(indexToRow(position) % 2 == 1)
        {
            return position + 3;
        }
        return position + 4;
    }

    /**
     * get the position index to the bottom right of this one
     * @param position base position
     * @return position index of the position to the bottom right
     */
    private int toBottomRight(int position)
    {
        if(indexToRow(position) % 2 == 1)
        {
            return position + 4;
        }
        return position + 5;
    }

    /**
     * get the position index of a jump to the top left
     * @param position base position
     * @return position index of the position a jump distance to the top left
     */
    private int toJumpTopLeft(int position)
    {
        return position - 9;
    }

    /**
     * get the position index of a jump to the top right
     * @param position base position
     * @return position index of the position a jump distance to the top right
     */
    private int toJumpTopRight(int position)
    {
        return position - 7;
    }

    /**
     * get the position index of a jump to the bottom right
     * @param position base position
     * @return position index of the position a jump distance to the bottom right
     */
    private int toJumpBottomRight(int position)
    {
        return position + 9;
    }

    /**
     * get the position index of a jump to the bottom left
     * @param position base position
     * @return position index of the position a jump distance to the bottom left
     */
    private int toJumpBottomLeft(int position)
    {
        return position + 7;
    }

    /**
     * Gets the row of a given index
     * @param position the index to convert
     * @return the row of the given index
     */
    private int indexToRow(int position)
    {
        return position / 4;
    }

    public Board copy()
    {
        Piece temp[] = new Piece[32];
        for(int i = 0; i < 32; i++){
            temp[i] = boardState[i];
        }
        return new Board(temp);
    }
}
