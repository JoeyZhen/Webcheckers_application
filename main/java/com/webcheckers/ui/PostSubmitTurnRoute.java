package com.webcheckers.ui;

        import com.google.gson.Gson;
        import com.webcheckers.appl.GameCenter;
        import com.webcheckers.model.*;
        import spark.*;

        import java.util.Objects;
        import java.util.logging.Logger;

/**
 * The UI controller needed to POST a move.
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class PostSubmitTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());

    private final Gson gson;
    private final GameCenter gameCenter;


    /**
     * Create the Route for the {@code POST /SubmitTurn } JSON request
     *
     * @param gameCenter Game center
     */
    public PostSubmitTurnRoute(final GameCenter gameCenter){
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        this.gameCenter = gameCenter;
        gson = new Gson();
    }


    /**
     *Handle submitting a turn in the webCheckers game.
     * @param request The HTTP request
     * @param response The HTTP response
     * @return the rendered rendered HTML view after a move
     * @throws Exception If null returned
     */
    @Override
    public Object handle(Request request, Response response) {
        Session session = request.session();
        Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
        Game game = this.gameCenter.getGame(player.getGameID());
        MoveList moveList = game.getMoveList();

        // You can only end your turn if your move is over
        if(moveList.isMoveOver()) {
            game.nextTurn();
            return gson.toJson(new Message(Message.INFO, "Move is Complete"));
        }else{
            return gson.toJson(new Message(Message.ERROR, "Move is not complete"));
        }
    }
}
