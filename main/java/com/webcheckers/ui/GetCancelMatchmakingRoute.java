package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static spark.Spark.halt;

public class GetCancelMatchmakingRoute implements Route {

  private static final Logger LOG = Logger.getLogger(GetCancelMatchmakingRoute.class.getName());

  private final PlayerLobby playerLobby;


  public GetCancelMatchmakingRoute(final PlayerLobby playerLobby){
    this.playerLobby = playerLobby;

    LOG.config("GetCancelMatchmakingRoute is initialized.");
  }

  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetCancelMatchmakingRoute is invoked.");

    final Session session = request.session();
    final Player player;

    if (session.attribute(GetHomeRoute.PLAYER_KEY) == null){
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;
    }

    player = session.attribute(GetHomeRoute.PLAYER_KEY);

    if (player.getGameID() != 0){
      response.redirect(WebServer.GAME_URL);
      halt();
      return null;
    }

    if (player.getName() == null){
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;
    }

    playerLobby.removePlayerFromQueue(player);
    LOG.fine(String.format("%s has left the matchmaking queue.", player.getName()));
    response.redirect(WebServer.MATCHMAKING_URL);
    return "";

  }
}
