package com.webcheckers.ui;


import com.webcheckers.model.*;
import com.webcheckers.appl.GameCenter;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import static spark.Spark.halt;

/**
 * The UI controller to GET the Replay home page
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class GetReplayRoute implements Route {

    static final String PLAYER_NAME = "playername";
    static final String LIST_OF_GAMES = "listofgames";
    static final String TITLE = "Replay";


    private static final Logger LOG = Logger.getLogger(GetReplayRoute.class.getName());
    private final GameCenter gameCenter;
    private final TemplateEngine templateEngine;



    public GetReplayRoute(GameCenter gameCenter, final TemplateEngine templateEngine) {
        this.gameCenter = gameCenter;
        this.templateEngine = templateEngine;
        LOG.config("GetReplayRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        final Session session = request.session();
        Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
        vm.put(GetHomeRoute.TITLE_ATTR, TITLE);
        vm.put(LIST_OF_GAMES, gameCenter.finishedGames());
        vm.put(PLAYER_NAME, player.getName());
        return templateEngine.render(new ModelAndView(vm, "replay.ftl"));
    }
}