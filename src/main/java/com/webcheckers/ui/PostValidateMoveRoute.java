package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.*;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI controller to POST a validate move request
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class PostValidateMoveRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());

    private final GameCenter gameCenter;


    public PostValidateMoveRoute(final GameCenter gameCenter) {
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");

        this.gameCenter = gameCenter;
        LOG.config("PostValidateMoveRoute Initialized");
    }

    @Override
    public Object handle(Request request, Response response) {
        Gson GSON = new Gson();
        Move move = GSON.fromJson(request.body(), Move.class);
        final Session session = request.session();
        final Game game = gameCenter.getGame(((Player)session.attribute(GetHomeRoute.PLAYER_KEY)).getGameID());
        MoveList moveList = game.getMoveList();

        if(game.getActiveColor() == Color.RED) {
            return GSON.toJson(game.validateMove(move, moveList, game.getBoard()));
        }else{
            Board temp = game.getBoard().flipBoard();
            Message message = game.validateMove(move, moveList, temp);
            game.setBoard(temp.flipBoard());
            return GSON.toJson(message);
        }
    }
}



