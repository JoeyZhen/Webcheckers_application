package com.webcheckers.model;

import java.util.ArrayList;

/**
 * Encapsulation of moves and captures
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class MoveList {

    private ArrayList<Move> moveList;
    private ArrayList<Piece> captureList;
    private boolean isMoveOver;

    /**
     * Constructor for MoveList
     * Initializes necessary storage
     */
    public MoveList()
    {
        this.moveList = new ArrayList<>();
        this.captureList = new ArrayList<>();
    }

    /**
     * Adds a move to the encapsulation
     * @param move the move to add
     * @param capture the piece that was captured if there was one, null otherwise.
     */
    public void addMove(Move move, Piece capture)
    {
        this.moveList.add(move);
        this.captureList.add(capture);
    }

    /**
     * Removes the most recent move from the list
     * @return the move that was removed
     */
    public Move popMove() {
        Move temp = this.moveList.get(this.moveList.size() - 1);
        this.moveList.remove(this.moveList.size() - 1);
        return temp;
    }

    /**
     * Removes the most recent capture from the list
     * @return the piece that was captured during the most recent move
     */
    public Piece popCapture(){
        Piece temp = this.captureList.get(this.captureList.size()-1);
        this.captureList.remove((this.captureList.size()-1));
        return temp;
    }

    /**
     * Resets the move list to its empty state
     */
    public void reset(){
        this.captureList.clear();
        this.moveList.clear();
        this.isMoveOver = false;
    }

    /**
     * Checks if there are any moves to pop
     * @return True if the move list is empty, false otherwise
     */
    public boolean moveListEmpty() {
        return this.moveList.size() == 0;
    }

    /**
     * Checks if there are any captures to pop
     * @return True if the capture list is empty, false otherwise
     */
    public boolean captureListEmpty(){
        return this.captureList.size() == 0;
    }

    /**
     * Checks that there are no moves and there are no captures
     * @return true if there are both no captures and no moves
     */
    public boolean isEmpty(){
        return this.captureList.size() == 0 && this.moveList.size() == 0;
    }

    /**
     * Remove the most recent move and capture from the list
     * @return the move that was popped.
     */
    public Move popToMove() {
        if(this.moveList.size() != 0) {
            Move temp = this.moveList.get(0);
            this.moveList.remove(0);
            this.captureList.remove(0);
            return temp;
        }
        return null;
    }

    /**
     * peek the most recent move
     * @return the most recent move
     */
    public Move peekMove(){
        return this.moveList.get(this.moveList.size()-1);
    }

    /**
     * peek the most recent capture
     * @return the most recent capture
     */
    public Piece peekCapture(){
        return this.captureList.get(this.captureList.size()-1);
    }

    public boolean isMoveOver(){
        return this.isMoveOver;
    }

    public void setIsMoveOver(boolean isMoveOver){
        this.isMoveOver = isMoveOver;
    }
}