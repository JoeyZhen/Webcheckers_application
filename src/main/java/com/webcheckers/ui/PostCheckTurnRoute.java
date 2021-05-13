package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import spark.*;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Handles Post "/checkturn"
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class PostCheckTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());

    private final GameCenter gameCenter;
    private final PlayerLobby playerLobby;


    public PostCheckTurnRoute(final GameCenter gameCenter, final PlayerLobby playerLobby) {

        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        this.gameCenter = gameCenter;
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) {

        Gson GSON = new Gson();
        final Session session = request.session();
        Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
        final Game game = gameCenter.getGame(player.getGameID());
        Message message;

        if (game == null){
            message = new Message(Message.INFO, "true");
        }
        // Check if a player resigned
        else if(game.getWhitePlayer().getGameID() == 0 || game.getRedPlayer().getGameID() == 0){
            message = new Message(Message.INFO, "true");
        }
        // Check if it is red's turn
        else if (game.getRedPlayer() == player && game.getActiveColor() == Color.RED ){
            message = new Message(Message.INFO, "true");
        }
        // Check if it is white's turn
        else if (game.getWhitePlayer() == player && game.getActiveColor() == Color.WHITE){
            message = new Message(Message.INFO, "true");
        }
        else{
            message = new Message(Message.INFO, "false");
        }
        return GSON.toJson(message);
    }
}