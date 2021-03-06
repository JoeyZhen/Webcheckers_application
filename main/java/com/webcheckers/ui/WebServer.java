package com.webcheckers.ui;

import static spark.Spark.*;

import java.util.Objects;
import java.util.logging.Logger;

import com.google.gson.Gson;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import spark.TemplateEngine;

import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.*;


/**
 * The server that initializes the set of HTTP request handlers.
 * This defines the <em>web application interface</em> for this
 * WebCheckers application.
 *
 * <p>
 * There are multiple ways in which you can have the client issue a
 * request and the application generate responses to requests. If your team is
 * not careful when designing your approach, you can quickly create a mess
 * where no one can remember how a particular request is issued or the response
 * gets generated. Aim for consistency in your approach for similar
 * activities or requests.
 * </p>
 *
 * <p>Design choices for how the client makes a request include:
 * <ul>
 *     <li>Request URL</li>
 *     <li>HTTP verb for request (GET, POST, PUT, DELETE and so on)</li>
 *     <li><em>Optional:</em> Inclusion of request parameters</li>
 * </ul>
 * </p>
 *
 * <p>Design choices for generating a response to a request include:
 * <ul>
 *     <li>View templates with conditional elements</li>
 *     <li>Use different view templates based on results of executing the client request</li>
 *     <li>Redirecting to a different application URL</li>
 * </ul>
 * </p>
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class WebServer {
  private static final Logger LOG = Logger.getLogger(WebServer.class.getName());

  //
  // Constants
  //

  /**
   * The URL pattern to request the Home page.
   */
  public static final String HOME_URL = "/";
  public static final String SIGNIN_URL = "/signin";
  public static final String GAME_URL = "/game";
  public static final String SIGN_OUT = "/out";
  public static final String BACK_UP = "/backupMove";
  public static final String RESIGN_URL =  "/resignGame";
  public static final String VALIDATE_URL = "/validateMove";
  public static final String SUBMIT_TURN = "/submitTurn";
  public static final String CHECK_TURN = "/checkTurn";
  public static final String REPLAY_MENU = "/replay";
  public static final String REPLAY_URL = "/replay/game";
  public static final String END_REPLAY_URL ="/replay/stopWatching";
  public static final String NEXT_TURN_URL = "/replay/nextTurn";
  public static final String PREVIOUS_TURN_URL = "/replay/previousTurn";
  public static final String MATCHMAKING_URL = "/matchmake";
  public static final String CANCEL_URL = "/cancelMatchmaking";


  //
  // Attributes
  //

  private final PlayerLobby playerLobby;
  private final GameCenter gameCenter;
  private final TemplateEngine templateEngine;
  private final Gson gson;

  //
  // Constructor
  //

  /**
   * The constructor for the Web Server.
   *
   * @param templateEngine
   *    The default {@link TemplateEngine} to render page-level HTML views.
   * @param gson
   *    The Google JSON parser object used to render Ajax responses.
   *
   * @throws NullPointerException
   *    If any of the parameters are {@code null}.
   */
  public WebServer(final PlayerLobby playerLobby, final GameCenter gameCenter, final TemplateEngine templateEngine, final Gson gson) {
    // validation
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    Objects.requireNonNull(gson, "gson must not be null");
    //
    this.playerLobby = playerLobby;
    this.gameCenter = gameCenter;
    this.templateEngine = templateEngine;
    this.gson = gson;
  }

  //
  // Public methods
  //

  /**
   * Initialize all of the HTTP routes that make up this web application.
   *
   * <p>
   * Initialization of the web server includes defining the location for static
   * files, and defining all routes for processing client requests. The method
   * returns after the web server finishes its initialization.
   * </p>
   */
  public void initialize() {

    // Configuration to serve static files
    staticFileLocation("/public");

    //// Setting any route (or filter) in Spark triggers initialization of the
    //// embedded Jetty web server.

    //// A route is set for a request verb by specifying the path for the
    //// request, and the function callback (request, response) -> {} to
    //// process the request. The order that the routes are defined is
    //// important. The first route (request-path combination) that matches
    //// is the one which is invoked. Additional documentation is at
    //// http://sparkjava.com/documentation.html and in Spark tutorials.

    //// Each route (processing function) will check if the request is valid
    //// from the client that made the request. If it is valid, the route
    //// will extract the relevant data from the request and pass it to the
    //// application object delegated with executing the request. When the
    //// delegate completes execution of the request, the route will create
    //// the parameter map that the response template needs. The data will
    //// either be in the value the delegate returns to the route after
    //// executing the request, or the route will query other application
    //// objects for the data needed.

    //// FreeMarker defines the HTML response using templates. Additional
    //// documentation is at
    //// http://freemarker.org/docs/dgui_quickstart_template.html.
    //// The Spark FreeMarkerEngine lets you pass variable values to the
    //// template via a map. Additional information is in online
    //// tutorials such as
    //// http://benjamindparrish.azurewebsites.net/adding-freemarker-to-java-spark/.

    //// These route definitions are examples. You will define the routes
    //// that are appropriate for the HTTP client interface that you define.
    //// Create separate Route classes to handle each route; this keeps your
    //// code clean; using small classes.

    // Shows the Checkers game Home page.
    get(HOME_URL, new GetHomeRoute(playerLobby, templateEngine));

    // Shows the Checkers game Login Page.
    get(SIGNIN_URL, new GetSigninRoute(playerLobby, templateEngine));

    // Post a login request.
    post(SIGNIN_URL, new PostSigninRoute(playerLobby, templateEngine));

    // Shows the Checkers game Game page
    get(GAME_URL, new GetGameRoute(playerLobby, gameCenter, templateEngine));

    // Sign Out
    get(SIGN_OUT, new GetSignoutRoute(playerLobby));

    // Resign
    post(RESIGN_URL, new PostResignRoute(playerLobby, gameCenter));

    //Check Turn
    post(CHECK_TURN, new PostCheckTurnRoute(gameCenter, playerLobby));

    //Validate a player move
    post(VALIDATE_URL, new PostValidateMoveRoute(gameCenter));

    //Backup last validated move
    post(BACK_UP, new PostBackUpMoveRoute(gameCenter));

    //Submit a Turn
    post(SUBMIT_TURN, new PostSubmitTurnRoute(gameCenter));

    //
    post(MATCHMAKING_URL, new PostMatchMakeRoute(playerLobby, templateEngine));

    get(MATCHMAKING_URL, new GetMatchmakingPageRoute(playerLobby, templateEngine));

    get(CANCEL_URL, new GetCancelMatchmakingRoute(playerLobby));




    //Replay Menu
    get(REPLAY_MENU, new GetReplayRoute(gameCenter, templateEngine));

    //Replay a game
    get(REPLAY_URL, new GetReplayGameRoute(gameCenter, templateEngine));

    //Next turn of replay
    post(NEXT_TURN_URL, new PostReplayNextRoute(gameCenter));
//
    //Previous turn of replay
    post(PREVIOUS_TURN_URL, new PostReplayPreviousRoute());

    //End the replay
    get(END_REPLAY_URL, new GetReplayStopWatchingRoute());
    LOG.config("WebServer is initialized.");
  }

}