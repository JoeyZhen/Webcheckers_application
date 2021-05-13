package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.GetReplayRoute.PLAYER_NAME;

public class PostReplayTest implements Route {
    private static final Logger LOG = Logger.getLogger(GetReplayRoute.class.getName());
    private final GameCenter gameCenter;
    private final TemplateEngine templateEngine;

    public PostReplayTest(final TemplateEngine templateEngine, final GameCenter gameCenter, final Gson gson) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.templateEngine = templateEngine;
        this.gameCenter = gameCenter;

    }

    @Override
    public String handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        final Session session = request.session();
        Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
        vm.put(PLAYER_NAME, player.getName());
        return templateEngine.render(new ModelAndView(vm, "replay.ftl"));
    }
}