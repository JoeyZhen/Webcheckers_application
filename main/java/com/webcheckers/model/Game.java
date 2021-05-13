package com.webcheckers.model;


import com.google.gson.Gson;
import com.webcheckers.ui.GetGameRoute;
import com.webcheckers.ui.GetHomeRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Enums for view types
 */
enum ViewMode
{
    PLAY,
    SPECTATOR,
    REPLAY
}

/**
 * Game class that contains 2 players and a board
 * Keeps track of view mode(how the user is displaying the game), which player's turn it is,
 * their color, and the turn number.
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class Game {
    private int gameNum;
    private boolean isActive;
    private int turnNum;
    private ViewMode viewMode;
    private Color activeColor;
    private Player redPlayer;
    private Player whitePlayer;
    private Board board;
    private Player winner;
    private Player loser;
    private MoveList moveList;
    private ArrayList<Board> gameRecord;

    public Game(final Player redPlayer, final Player whitePlayer, final int gameNum){
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.board = new Board();
        this.activeColor = Color.RED;
        this.viewMode = ViewMode.PLAY;
        this.gameNum = gameNum;
        this.turnNum = 1;
        this.isActive = true;
        this.moveList = new MoveList();
        this.gameRecord = new ArrayList<>();
        this.gameRecord.add(this.board.copy());
    }

    /**
     * isActive displays whether the game is being currently played or not(due to timeout/replay display)
     * @return boolean isActive
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Changes viewMode when Game ends, or when spectating ends.
     */
    public void sleep(){
        this.isActive = false;
        this.viewMode = ViewMode.SPECTATOR;
    }

    /**
     * Advance the turn of the game
     */
    public void nextTurn(){
        this.turnNum++;
        if(this.activeColor == Color.RED){
            this.activeColor = Color.WHITE;
        }else{
            this.activeColor = Color.RED;
        }
        gameRecord.add(this.board.copy());
        this.moveList.reset();
        if (this.board.isGameOver(this.activeColor)){
            if (activeColor == Color.RED){
                this.setLoser(this.getRedPlayer());
                this.setWinner(this.getWhitePlayer());
            } else {
                this.setLoser(this.getWhitePlayer());
                this.setWinner(this.getRedPlayer());
            }
        }
    }


    /**
     * Get the ViewMode for the current game
     * @return the ViewMode for the current game
     */
    public ViewMode getViewMode(){
        return viewMode;
    }

    /**
     * Getter method for the board
     * @return Board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Getter method for the red player
     * @return current red Player
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    /**
     * Getter method for the white player
     * @return current white Player
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * Returns the turn number of current move
     * @return int of current turn number
     */
    public int getTurnNum() {
        return turnNum;
    }

    /**
     * Getter for game number
     * @return int of current game number
     */
    public int getGameNum() {
        return gameNum;
    }

    /**
     * Getter for activeColor(what color the currentPlayer should be)
     * @return Color of current player
     */

    public Color getActiveColor(){
        return activeColor;
    }

    /**
     * Sets the given player as the winner of the game
     * @param player the player who won
     */
    public void setWinner(Player player) {

        if (this.redPlayer.equals(player)) {
            this.winner = this.redPlayer;
        } else {
            this.winner = this.whitePlayer;
        }

        player.win();

    }

    /**
     * Sets the winner of the game
     * @param player the player who lost the game
     */
    public void setLoser(Player player) {
        if (this.redPlayer.equals(player)) {
            this.loser = this.redPlayer;
        } else {
            this.loser = this.whitePlayer;
        }

        player.lost();

    }

    /**
     * Gets the winner of a game
     * @return the current winner of a game, null if the game is in progress
     */
    public Player getWinner() {
        return this.winner;
    }


    /**
     * Gets the loser of a game
     * @return the current winner of a game, null if the game is in progress
     */
    public Player getLoser() {
        return this.loser;
    }


    /**
     * Checks if the game has a winner
     * @return true if the game has a winner, false otherwise
     */
    public boolean hasWinner() {
        return !(this.winner == null);
    }

    /**
     * Quick toString method for error checking the current game's turn stats
     * @return String with most stats reprinted for testing use
     */
    @Override
    public String toString() {
        return redPlayer.getName() + " vs " + whitePlayer.getName() +
                "\n Game:" + getGameNum();
    }

    /**
     * Validates a given move
     * @param move move to validate
     * @return true if the move is valid, false otherwise
     */
    public Message validateMove(Move move, MoveList moveList, Board board) {
        if (move != null) {
            if (moveList.isEmpty()) {
                Message mess = board.validateMove(move, this.activeColor);
                if (mess.getType().equals(Message.INFO)) {
                    Piece capture = board.simpleJumpCapture(move);
                    if (board.resultsInKing(move)) {
                        move.setWasKinged(true);
                        moveList.setIsMoveOver(true);
                    }
                    moveList.addMove(move, capture);
                    board.makeMove(move, this.activeColor);
                    if (!board.isJumpAvailable(this.activeColor, move.getEnd().toArrayIndex()) || capture == null) {
                        moveList.setIsMoveOver(true);
                    }
                }
                return mess;
            } else if (moveList.peekCapture() == null) {
                return new Message(Message.ERROR, "Invalid Move");
            } else {
                if (moveList.peekMove().getEnd().toArrayIndex() != move.getStart().toArrayIndex()) {
                    return new Message(Message.ERROR, "Invalid Move");
                }
                Message mess = board.validateMove(move, this.activeColor);
                Piece piece = board.simpleJumpCapture(move);
                if (mess.getType().equals(Message.INFO) && piece != null) {
                    if (board.resultsInKing(move)) {
                        move.setWasKinged(true);
                        moveList.setIsMoveOver(true);
                    }
                    moveList.addMove(move, piece);
                    board.makeMove(move, this.activeColor);
                    if (!board.isJumpAvailable(this.activeColor, move.getEnd().toArrayIndex())) {
                        moveList.setIsMoveOver(true);
                    }
                    return mess;
                } else {
                    return new Message(Message.ERROR, "Invalid Move");
                }
            }

        } else {
            return new Message(Message.ERROR, "Unknown Error");
        }
    }

    /**
     * Enact a given move
     * @param moveList moveList to enact to enact
     * @return the piece captured by a move if applicable
     */
    public Message makeMove(MoveList moveList, Board board)
    {
        Message temp = new Message(Message.ERROR, "No Move Submitted");
        while(!moveList.isEmpty()){
            temp = board.makeMove(moveList.popToMove(), this.activeColor);
        }
        return temp;
    }

    /**
     * Backs up a given move
     * @param moveList moveList to revert move from
     */
    public void backUpMove(MoveList moveList, Board board)
    {
        moveList.setIsMoveOver(false);
        board.backUpMove(moveList.popMove(), this.activeColor, moveList.popCapture());
    }

    /**
     * Backs up all moves made this turn
     */
    public void backUpMove(){
        while(!this.moveList.isEmpty()){
            this.board.backUpMove(this.moveList.popMove(), this.activeColor, this.moveList.popCapture());
        }
    }

    /**
     * Getter Method for the moveList
     * @return the moveList for this game
     */
    public MoveList getMoveList()
    {
        return this.moveList;
    }

    /**
     * Sets the game board to a different board
     * @param board board to change the game board to
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Gets the Board for a specific turn
     * @param turnNum the turn to check
     * @return the MoveList for the turn
     */
    public Board getBoard(int turnNum){
        return this.gameRecord.get(turnNum-2);
    }

    /**
     * Gets the game length up to the current time
     * @return the number of turns that the game lasted
     */
    public int getGameLength(){
        return this.turnNum;
    }

    public Map<String,Object> createReplayMap(Player player){
        Map<String, Object> vm = new HashMap<>();
        vm.put(GetHomeRoute.TITLE_ATTR,"Replay");
        vm.put(GetGameRoute.VIEW_MODE, ViewMode.REPLAY);
        vm.put(GetGameRoute.RED_PLAYER, redPlayer);
        vm.put(GetGameRoute.CURRENT_PLAYER, player);
        vm.put(GetGameRoute.WHITE_PLAYER, whitePlayer);
        vm.put(GetGameRoute.ACTIVE_COLOR, activeColor);
        vm.put(GetGameRoute.BOARD, this.gameRecord.get(player.getTurn()));
        Map<String, Object> modeOptionsAsJSON = new HashMap<>();
        if(player.getTurn() > 0) {
            modeOptionsAsJSON.put("hasPrevious",true);
        }
        else{
            modeOptionsAsJSON.put("hasPrevious", false);
        }
        if(player.getTurn() + 1 < this.turnNum){
            modeOptionsAsJSON.put("hasNext", true);
        }
        else{
            modeOptionsAsJSON.put("hasNext", false);
        }
        Gson gson = new Gson();
        vm.put("modeOptionsAsJSON", gson.toJson(modeOptionsAsJSON));
        return vm;
    }
}
