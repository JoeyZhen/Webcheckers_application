package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

//import sun.rmi.runtime.Log;
/**
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */

public class GameCenter {

    private static final Logger LOG = Logger.getLogger(GameCenter.class.getName());

    private HashMap<Integer, Game> gameList;
    private int totalGames = 0;
    private Player playerOne;
    private Player playerTwo;
    private Replay savereplay;

    /**
     * Holds site wide statistics
     */
    public GameCenter(){
        this.gameList = new HashMap<>();
        this.totalGames = 0;
    }

    public Game getGame(final int gameID){
        return gameList.get(gameID);
    }

    /**
     * Create a new game, give it a gameID, and assign the game to the gameID in the HashMap.
     * @return the gameID.
     */
    public int newGame(final Player playerOne, final Player playerTwo){
        LOG.fine("New Game instance Created");
        this.totalGames++;
        Game game = new Game(playerOne, playerTwo, totalGames);
        gameList.put(totalGames, game);
        int toReturn = totalGames;
        return toReturn;
    }

    /**
     * Store game stats when a game is finished
     */
    public void gameFinished(){
        synchronized (this){


        }
    }

    public Replay getsaveReplay(){
        return this.savereplay;
    }

    public ArrayList<String> finishedGames(){
        ArrayList<String> gamesFinished = new ArrayList<>();
        for (int gameNumber : gameList.keySet()) {
           Game game = gameList.get(gameNumber);
           if(game.getRedPlayer().getGameID() == 0){
               gamesFinished.add("Game" + gameNumber);
           }
        }
        return gamesFinished;
    }

}
