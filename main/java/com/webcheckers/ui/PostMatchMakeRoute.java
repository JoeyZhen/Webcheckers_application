package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.logging.Logger;

import static spark.Spark.halt;

public class PostMatchMakeRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostMatchMakeRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    static final String ELO_PARAM = "maxDiff";
    static final String GAME_URL = "/game?name=";

    public PostMatchMakeRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine){
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
        //
        LOG.config("PostMatchMakeRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        LOG.fine("PostMatchMakeRoute is invoked.");

        final Session session = request.session();
        final Player player;
        final int maxEloDifferential;

        if (session.attribute(GetHomeRoute.PLAYER_KEY) == null){
            response.redirect(WebServer.HOME_URL);
            halt();
            return "";
        } else {
            player = session.attribute(GetHomeRoute.PLAYER_KEY);
        }

        if (player.getName() == null){
            response.redirect(WebServer.HOME_URL);
            halt();
            return "";
        }

        if (player.getGameID() != 0){
            response.redirect(WebServer.GAME_URL);
            halt();
            return "";
        }

        String maxEloDifferentialString = request.queryParams(ELO_PARAM);
        if (maxEloDifferentialString == null){
            response.redirect(WebServer.HOME_URL);
            halt();
            return "";
        }

        maxEloDifferential = Integer.parseInt(maxEloDifferentialString) * 25;

        player.setAllowedEloDifference(maxEloDifferential);

        LOG.fine(String.format("%s is now matchmaking with an Elo differential of %d!", player.getName(), player.getAllowedEloDifference()));

        if (!playerLobby.isPlayerSearching(player)){
            playerLobby.addPlayerToQueue(player);
        }

        String playerToChallenge = playerLobby.attemptToMatchmake(player);

        if (playerToChallenge == null){
          response.redirect(WebServer.MATCHMAKING_URL);
          return "Hey we haven't found you a match yet but I have to return something if I'm not redirecting you so here you go. Merry Chrysler :)";
        } else {
          String redirectURL = String.format("%s%s", GAME_URL, playerToChallenge);
          playerLobby.removePlayerFromQueue(player);
          playerLobby.removePlayerFromQueue(playerLobby.getSessionByName(playerToChallenge).attribute(GetHomeRoute.PLAYER_KEY));
          response.redirect(redirectURL);
          halt();
          return "";
        }

    }
}
