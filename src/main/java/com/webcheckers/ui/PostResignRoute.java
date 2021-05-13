package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI controller to POST a resign route
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
@SuppressWarnings("Duplicates")
public class PostResignRoute implements Route {

  private static final Logger LOG = Logger.getLogger(PostResignRoute.class.getName());

  private final PlayerLobby playerLobby;
  private final GameCenter gameCenter;

  public PostResignRoute(final PlayerLobby playerLobby, final GameCenter gameCenter){
    this.playerLobby = playerLobby;
    this.gameCenter = gameCenter;

    LOG.config("PostResignRoute is initialized.");

  }

  @Override
  public Object handle(Request request, Response response) throws Exception {

    LOG.fine("PostResignRoute is invoked.");

    final Session session = request.session();
    Gson GSON = new Gson();
    final Player player;
    final int gameID;


    if (session.attribute(GetHomeRoute.PLAYER_KEY) == null){
      LOG.fine("PlayerKey is null, redirecting to home.");
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;
    } else {
      player = session.attribute(GetHomeRoute.PLAYER_KEY);
    }

    if (player.getName() == null){
      LOG.fine("Player name is null, redirecting to home.");
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;
    }

    if (player.getGameID() == 0){
      LOG.fine("Player has no ongoing game, redirecting to home.");
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;
    }


    gameID = player.getGameID();

    Game game = gameCenter.getGame(gameID);

    String messageText = String.format("%s has resigned, you win!", player.getName());

    String opponent;

    if(gameCenter.getGame(gameID).getRedPlayer() != player &&  game.getActiveColor() == Color.RED ) {
      LOG.fine("Must be the players turn");
      halt();
      return null;
    }
    if (gameCenter.getGame(gameID).getRedPlayer() == player){
      opponent = gameCenter.getGame(gameID).getWhitePlayer().getName();
    }
    else {
      opponent = gameCenter.getGame(gameID).getRedPlayer().getName();
    }

    Message playerResignedMessage = new Message(Message.INFO, messageText);

    Session opponentSession = playerLobby.getSessionByName(opponent);

    opponentSession.attribute(GetHomeRoute.MESSAGE_ATTR, playerResignedMessage);

    player.setGameID(0);
    player.lost();
    Player opponentPlayer = opponentSession.attribute(GetHomeRoute.PLAYER_KEY);

    opponentPlayer.setGameID(0);
    opponentPlayer.win();


    return GSON.toJson(playerResignedMessage);
  }
}
