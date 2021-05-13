package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.ui.GetHomeRoute;
import spark.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class PlayerLobby {

  private static final Logger LOG = Logger.getLogger(PlayerLobby.class.getName());

  // A HashMap of Http sessions and Strings, which represent currently logged in players and their associated names.
  private static HashMap<Session, String> playerNames = new HashMap<>();

  // The reverse of the playerNames hashmap.
  private static HashMap<String, Session> playerSessions = new HashMap<>();


  private static ArrayList<Player> playersMatchmaking = new ArrayList<>();

  public Player newPlayer() {
    LOG.fine("New player instance created.");
    return new Player(this);
  }

  /**
   * Determine if a username is already taken by an active user.
   *
   * @param name the username in question
   * @return false if name is available, true if taken.
   */
  public boolean playerNameAlreadyTaken(final String name) {
    return playerNames.containsValue(name);
  }

  /**
   * Add a session and corresponding name to the playerNames hashmap when a new player logs in.
   *
   * @param session The new http session which to link to the player name
   * @param name    The name to link to the http session
   */
  public void newPlayerLogin(final Session session, final String name) {
    synchronized (this) {
      LOG.fine(name + " signed in");
      playerNames.put(session, name);
      playerSessions.put(name, session);
    }
  }

  public String getNumberOfActivePlayers() {
    synchronized (this) {
      Set keySet = playerNames.keySet();
      int activePlayers = keySet.size();
      String toReturn = Integer.toString(activePlayers);
      return toReturn;
    }
  }

  public Session getSessionByName(String name) {
    return playerSessions.get(name);
  }

  /**
   * Remove the player session and name from the players hashmap upon logging out.
   *
   * @param name The name of the player being logged out.
   */
  public void playerLogout(final String name) {
    synchronized (this) {
      LOG.fine(name + " signed out");
      Session session = playerSessions.remove(name);
      Player toRemove = session.attribute("playerServices");
      removePlayerFromQueue(toRemove);
      playerNames.remove(session);
    }
  }

  /**
   * Returns list of Active players excluding the current player
   *
   * @param playerExcluded session of current player
   * @return list of players
   */
  public ArrayList<String> getListOfActivePlayers(String playerExcluded) {
    ArrayList<String> listOfActivePlayers = new ArrayList<>(playerNames.values());
    listOfActivePlayers.remove(playerExcluded);

    for (Player playerMatchmaking : playersMatchmaking){
      listOfActivePlayers.remove(playerMatchmaking.getName());
    }

    return listOfActivePlayers;
  }

  public void addPlayerToQueue(final Player player) {
    playersMatchmaking.add(player);
  }

  public void removePlayerFromQueue(final Player player) {
    playersMatchmaking.remove(player);
  }

  public boolean isPlayerSearching(final Player player) {
    return playersMatchmaking.contains(player);
  }

  public boolean doesPlayerFitRange(int playerElo, int allowedEloDifference, int eloToTest) {
    int eloCeiling = playerElo + allowedEloDifference;
    int eloFloor = playerElo - eloCeiling;
    if (eloFloor < 0) {
      eloFloor = 0;
    }

    return (eloToTest >= eloFloor) && (eloToTest <= eloCeiling);

  }

  public String attemptToMatchmake(Player playerSearching) {
    int playerElo = playerSearching.getMatchMakeScore();
    int allowedEloDifference = playerSearching.getAllowedEloDifference();

    for (Player possibleMatch : playersMatchmaking) {

      // A little check to make sure that we aren't accidentally leaving players in the queue somehow.
      // Safety first!
      if (possibleMatch.getGameID() != 0){
        removePlayerFromQueue(possibleMatch);
      }

      if (!possibleMatch.getName().equals(playerSearching.getName())) {
        if (doesPlayerFitRange(playerElo, allowedEloDifference, possibleMatch.getMatchMakeScore()) &&
                doesPlayerFitRange(possibleMatch.getMatchMakeScore(), possibleMatch.getAllowedEloDifference(), playerElo)) {
          return possibleMatch.getName();
        }
      }
    }

    return null;

  }

}
