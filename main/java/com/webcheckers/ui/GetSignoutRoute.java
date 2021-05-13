package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI controller to GET the signout route
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class GetSignoutRoute implements Route {

  private static final Logger LOG = Logger.getLogger(GetSignoutRoute.class.getName());

  private final PlayerLobby playerLobby;

  public GetSignoutRoute(final PlayerLobby playerLobby) {
    this.playerLobby = playerLobby;
    //
    LOG.config("GetSignoutRoute is initialized.");
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    Session session = request.session();
    Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
    if (player != null){
      if (player.getName() != null){
        playerLobby.playerLogout(request.queryParams("name"));
        session.attribute(GetHomeRoute.PLAYER_KEY, null);
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
      }
    }
    return null;
  }
}
