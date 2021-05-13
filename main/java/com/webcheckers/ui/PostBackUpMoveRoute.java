package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import spark.*;

import java.util.logging.Logger;

/**
 * The UI controller to POST a back up move request
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class PostBackUpMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostBackUpMoveRoute.class.getName());

    private final GameCenter gameCenter;

    /**
     *Create the Spark Route (UI controller) for the
     * {@code POST /BackupMove} HTTP request
     * @param gameCenter gameCenter
     */
    public PostBackUpMoveRoute(final GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }


    /**
     *Render a Backup request
     *
     * @param request the HTTP request
     * @param response the HTTP request
     * @return the rendered back up
     * @throws Exception if null is returned
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson gson = new Gson();
        Session session = request.session();
        Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
        final Game game = gameCenter.getGame(player.getGameID());
        MoveList moveList = game.getMoveList();
        if(moveList.isEmpty())
        {
            return gson.toJson(new Message(Message.ERROR, "MoveList is Empty"));
        }
        if(game.getActiveColor() == Color.RED){
            game.backUpMove(moveList,game.getBoard());
        }else{
            Board temp = game.getBoard().flipBoard();
            game.backUpMove(moveList, temp);
            game.setBoard(temp.flipBoard());
        }

        return gson.toJson(new Message(Message.INFO, "Move backed up successfully"));
    }
}
