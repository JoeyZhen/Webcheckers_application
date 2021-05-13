package com.webcheckers.ui;


import com.webcheckers.model.*;
import com.webcheckers.appl.GameCenter;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import static spark.Spark.halt;

/**
 * The UI controller to GET the replay game page
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class GetReplayGameRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetReplayGameRoute.class.getName());
    private final GameCenter gameCenter;
    private final TemplateEngine templateEngine;


    public GetReplayGameRoute(GameCenter gameCenter, final TemplateEngine templateEngine) {
        this.gameCenter = gameCenter;
        this.templateEngine = templateEngine;
        LOG.config("GetReplayGameRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {
        final Session session = request.session();
        Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
        if(player.getGameID() == 0){
            player.setGameID(Integer.parseInt(request.queryParams("gameID")));
            player.setTurn(0);
        }
        final Game game = gameCenter.getGame(player.getGameID());
        Map<String, Object> vm = game.createReplayMap(player);
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));

    }
}