package com.webcheckers.model;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.ui.GetSigninRoute;

import java.util.logging.Logger;

/**
 * The model of the checkers player
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class Player {

  private static final Logger LOG = Logger.getLogger(Player.class.getName());


  private final PlayerLobby playerLobby;

  // The player's username that they have selected upon signin.
  private String name;
  // The ID of the game that is currently in progress. If there is no game in progress, should be set to 0.
  private int gameID;
  // Turn number only used for Replays
  private int turn;
  // Total number of wins
  private int totalWins;
  //Total number of games
  private int totalGames;
  // The rating of the current opponent if player is in a game.
  private int ratingOfOpponent;
  // Total score that has been accumulated over all games.
  private int totalEloRating;
  // Range up or down in elo that would be okay to match this player with.
  private int allowedEloDifference;


  /**
   * Constructor for Player
   * @param playerLobby the PlayerLobby of the server of the player
   */
  public Player(PlayerLobby playerLobby){
    this.playerLobby = playerLobby;
    this.totalWins = 0;
    this.totalGames = 0;
    this.ratingOfOpponent = -1;
    this.totalEloRating = 1000;
    this.allowedEloDifference = 100; // This will almost always be changed, it is just 100 as a baseline.
  }

  /**
   * Sets the player's name to a give string
   * @param name new name for player
   */
  public synchronized void setName(String name){
    this.name = name;
  }


  /**
   * Getter for the player's name
   * @return the player's name
   */
  public String getName(){
    return name;
  }

  /**
   * End the current game for the player
   */
  public void endGame(){
    this.gameID = 0;
  }

  /**
   * Assign the player to a new Game
   * @param newGameID GameID of the Game to assign the player to
   */
  public void setGameID(final int newGameID){
    this.gameID = newGameID;
  }

  /**
   * Getter for the player's current GameID
   * @return player's current GameID
   */
  public int getGameID() {return gameID;}


  public void setRatingOfOpponent(int ratingOfOpponent){
    this.ratingOfOpponent = ratingOfOpponent;
  }

  /**
   *Increments the number of games won
   */
  public void win() {
    this.totalWins++;
    this.totalGames++;

    if (this.ratingOfOpponent == -1){
      return;
    }

    this.totalEloRating = (int)(this.totalEloRating + 30 * (1-this.getWinProbability()));
    this.ratingOfOpponent = -1;

  }

  /**
   *Increments the number of games played
   */
  public void lost(){

    totalGames++;

    if (this.ratingOfOpponent == -1){
      return;
    }

    this.totalEloRating = (int)(this.totalEloRating + 30 * (0-this.getWinProbability()));
    this.ratingOfOpponent = -1;

  }

  public void logStuff(){
    LOG.fine(String.format("%s has %d wins, and has played %d games total.",
            this.getName(),
            this.totalWins,
            this.totalGames));
    LOG.fine(String.format("They have a total elo score of %d.\n",
            this.getMatchMakeScore()));
  }

  public void logGameStuff(){
    LOG.fine(String.format("%s is playing against a player of rating %d.",
            this.name,
            this.ratingOfOpponent));
    LOG.fine(String.format("This results in rating difference of %f, giving them a probability of winning of %f%%.",
            (double)this.getMatchMakeScore() - this.ratingOfOpponent,
            this.getWinProbability() * 100));
  }

  public void setAllowedEloDifference(int allowedEloDifference){
    this.allowedEloDifference = allowedEloDifference;
  }

  public int getAllowedEloDifference(){
    return allowedEloDifference;
  }

  public int getAllowedEloFloor(){
    int toReturn = getMatchMakeScore() - allowedEloDifference;
    if (toReturn >= 0){
      return toReturn;
    } else {
      return 0;
    }
  }

  public int getAllowedEloCeiling(){
    return getMatchMakeScore() + allowedEloDifference;
  }


  public int getTotalGames(){
    return totalGames;
  }

  public int getTotalWins(){
    return totalWins;
  }

  public int getTotalLosses(){
    return totalGames - totalWins;
  }

  public String getWinsString(){
    if (this.totalWins == 1){
      return "1 win";
    } else {
      return String.format("%d wins", this.totalWins);
    }
  }

  private double getWinProbability(){
    double myRating = this.getMatchMakeScore();
    double opponentRating = this.ratingOfOpponent;

    double eloDifference = myRating - opponentRating;

    double winProbablity = 1 / (1 + Math.pow(10, (-eloDifference / 400)));

    return winProbablity;

  }

  public String getLossesString(){
    if (this.getTotalLosses() == 1){
      return "1 loss";
    } else {
      return String.format("%d losses", this.getTotalLosses());
    }
  }

  public int getMatchMakeScore(){
    return totalEloRating;
  }

  /**
   * Gets the players turn, only used for replay
   * @param turn the player's turn
   */
  public void setTurn(int turn){
    this.turn = turn;
  }

  /**
   * Sets the players turn, only used for replay
   * @return the player's turn
   */
  public int getTurn(){
    return this.turn;
  }
}
