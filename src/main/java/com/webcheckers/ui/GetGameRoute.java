package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI controller to GET the game page
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
@SuppressWarnings("Duplicates")
public class GetGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    static final String CHALLENGE_NAME = "name";
    public static final String VIEW_MODE = "viewMode";
    public static final String CURRENT_PLAYER = "currentPlayer";
    public static final String RED_PLAYER = "redPlayer";
    public static final String WHITE_PLAYER = "whitePlayer";
    public static final String ACTIVE_COLOR = "activeColor";
    public static final String BOARD = "board";
    private static final String MESSAGE = "message";
    static final String IN_GAME_ERROR = "inGameError";



    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final GameCenter gameCenter;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetGameRoute(final PlayerLobby playerLobby, final GameCenter gameCenter, final TemplateEngine templateEngine){
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.playerLobby = playerLobby;
        this.gameCenter = gameCenter;
        this.templateEngine = templateEngine;
        //
        LOG.config("GetGameRoute is initialized.");
    }

    /**
     * Render the WebCheckers game page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the game page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetGameRoute is invoked.");

        final Session session = request.session();
        final Player player;

        Map<String, Object> vm = new HashMap<>();
        vm.put(GetHomeRoute.TITLE_ATTR, "Welcome!");

        // If the http session doesn't have a Player object assigned to it, then redirect to the home page.
        if (session.attribute(GetHomeRoute.PLAYER_KEY) == null){
          response.redirect(WebServer.HOME_URL);
          halt();
          return null;
        } else {
          player = session.attribute(GetHomeRoute.PLAYER_KEY);
        }

        // If the user hasn't signed in yet, then redirect to the home page.
        if (player.getName() == null){
          response.redirect(WebServer.HOME_URL);
          halt();
          return null;
        }

        if (session.attribute(GetHomeRoute.MESSAGE_ATTR) != null){
            vm.put(GetHomeRoute.MESSAGE_ATTR, session.attribute(GetHomeRoute.MESSAGE_ATTR));
            session.attribute(GetHomeRoute.MESSAGE_ATTR, null);
        }

        if (player.getGameID() == 0){
            String nameToChallenge = request.queryParams(CHALLENGE_NAME);
            // If a user just tries to go to /game without having an ongoing game, redirect to the home page.
            if (nameToChallenge == null){
                response.redirect(WebServer.HOME_URL);
                halt();
                return null;
            } else {
                Player playerToChallenge = playerLobby.getSessionByName(nameToChallenge).attribute(GetHomeRoute.PLAYER_KEY);
                if (playerToChallenge.getGameID() == 0){
                    int gameIdToAssign = gameCenter.newGame(player, playerToChallenge);
                    player.setGameID(gameIdToAssign);
                    player.setRatingOfOpponent(playerToChallenge.getMatchMakeScore());
                    playerToChallenge.setGameID(gameIdToAssign);
                    playerToChallenge.setRatingOfOpponent(player.getMatchMakeScore());

                    // Comment out when not debugging.
                    player.logGameStuff();

                } else {
                    LOG.fine(String.format("Player \"%s\" is already in a game, redirecting to home.", nameToChallenge));
                    response.redirect(WebServer.HOME_URL + "?name=" + nameToChallenge);
                    halt();
                    return null;
                }
            }
        }
        vm.put(IN_GAME_ERROR, false);
        Game game = gameCenter.getGame(player.getGameID());

        vm.put(CURRENT_PLAYER, player);
        vm.put(VIEW_MODE, game.getViewMode());
        vm.put(RED_PLAYER, game.getRedPlayer());
        vm.put(WHITE_PLAYER, game.getWhitePlayer());

        if (game.getRedPlayer().getName() == player.getName()){
            vm.put(BOARD, game.getBoard());
        } else {
            vm.put(BOARD, game.getBoard().flipBoard());
        }

        vm.put(ACTIVE_COLOR, game.getActiveColor());

        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }

}