package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import static spark.Spark.halt;

/**
 * The UI controller to GET the home page
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;

  public static final String TITLE_ATTR = "title";
  static final String NEW_PLAYER_ATTR = "newPlayer";
  static final String PLAYER_KEY = "playerServices";
  static final String PLAYER_NAME = "playerName";
  static final String NUMBER_OF_PLAYERS = "activePlayers";
  static final String LIST_OF_PLAYERS = "listofplayers";
  static final String IN_GAME_ERROR = "inGameError";
  static final String NAME_TO_CHALLENGE = "nameToChallenge";
  static final String MESSAGE_ATTR = "message";
  static final String ELO_RATING_ATTR = "eloRating";
  static final String TOTAL_GAMES_ATTR = "totalGames";
  static final String WINS_ATTR = "winsString";
  static final String LOSSES_ATTR = "lossesString";
  static final String IS_SEARCHING_ATTR = "isSearching";
  /**
   * Create the Spark Route (UI controller) for the
   * {@code GET /} HTTP request.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
    // validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;
    //
    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetHomeRoute is invoked.");

    final Session session = request.session();

    Map<String, Object> vm = new HashMap<>();
    vm.put(TITLE_ATTR, "Welcome!");
    // If there is no player object associated with the current session, create one.
    if (session.attribute(PLAYER_KEY) == null){
      vm.put(IN_GAME_ERROR, false);
      final Player newPlayer = playerLobby.newPlayer();
      session.attribute(PLAYER_KEY, newPlayer);
      session.attribute(IS_SEARCHING_ATTR, false);
      vm.put(NEW_PLAYER_ATTR, true);
      vm.put(NUMBER_OF_PLAYERS, playerLobby.getNumberOfActivePlayers());
    } else {
      Player player = session.attribute(PLAYER_KEY);

      if (player.getGameID() != 0){
        response.redirect(WebServer.GAME_URL);
        halt();
        return null;
      }

      if (playerLobby.isPlayerSearching(player)){
        playerLobby.removePlayerFromQueue(player);

        LOG.fine(String.format("%s has left the matchmaking queue.", player.getName()));

      }

      if (session.attribute(MESSAGE_ATTR) != null){
        vm.put(MESSAGE_ATTR, session.attribute(MESSAGE_ATTR));
        session.attribute(MESSAGE_ATTR, null);
      }

      // If the session has a player object, but has not logged in, display the login button.
      if (player.getName() == null){
        vm.put(IN_GAME_ERROR, false);
        vm.put(NEW_PLAYER_ATTR, true);
        vm.put(NUMBER_OF_PLAYERS, playerLobby.getNumberOfActivePlayers());
      }
      else {
        if(request.queryParams("name") != null){
          vm.put(IN_GAME_ERROR, true);
          vm.put(NEW_PLAYER_ATTR, false);
          vm.put(PLAYER_NAME, player.getName());
          vm.put(NAME_TO_CHALLENGE, request.queryParams("name"));
          vm.put(LIST_OF_PLAYERS, playerLobby.getListOfActivePlayers(player.getName()));
        }
        else {
          vm.put(IN_GAME_ERROR, false);
          vm.put(NEW_PLAYER_ATTR, false);
          vm.put(PLAYER_NAME, player.getName());
          vm.put(LIST_OF_PLAYERS, playerLobby.getListOfActivePlayers(player.getName()));
        }

        vm.put(ELO_RATING_ATTR, player.getMatchMakeScore());
        vm.put(TOTAL_GAMES_ATTR, player.getTotalGames());
        vm.put(WINS_ATTR, player.getWinsString());
        vm.put(LOSSES_ATTR, player.getLossesString());
        //player.logStuff();
        }
    }

    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }

}