package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.*;
import spark.*;

import java.util.Objects;

/**
 * The UI controller to POST the next turn request in a replay
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class PostReplayNextRoute implements Route {
    private final GameCenter gameCenter;

    public PostReplayNextRoute(final GameCenter gameCenter) {

        this.gameCenter = gameCenter;
    }

    @Override
    public String handle(Request request, Response response) {
        Gson gson = new Gson();
        final Session session = request.session();
        Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
        int gameID = player.getGameID();
        int turn = player.getTurn();
        Game game = gameCenter.getGame(gameID);
        if(game.getTurnNum() > turn) {
            player.setTurn(turn+1);
            return gson.toJson(new Message(Message.INFO, "true"));
        }
        else {
            return gson.toJson(new Message(Message.ERROR, "false"));
        }
    }
}