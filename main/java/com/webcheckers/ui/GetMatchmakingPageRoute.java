package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static spark.Spark.halt;

public class GetMatchmakingPageRoute implements Route {

  private static final Logger LOG = Logger.getLogger(GetMatchmakingPageRoute.class.getName());

  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;

  static final String INITIAL_ELO_FLOOR = "initialEloFloor";
  static final String INITIAL_ELO_CEILING = "initialEloCeiling";
  static final String INITIAL_SLIDER_POSITION = "sliderPosition";
  static final String IS_PLAYER_IN_QUEUE = "inQueue";
  static final String ELO_FLOOR = "eloFloor";
  static final String ELO_CEILING = "eloCeiling";


  public GetMatchmakingPageRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine){
    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;

    LOG.config("GetMatchmakingPageRoute is initialized.");
  }



  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetMatchmakingPageRoute is invoked.");

    final Session session = request.session();
    final Player player;

    Map<String, Object> vm = new HashMap<>();
    vm.put(GetHomeRoute.TITLE_ATTR, "Matchmaking");

    if (session.attribute(GetHomeRoute.PLAYER_KEY) == null){
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;
    }

    player = session.attribute(GetHomeRoute.PLAYER_KEY);

    if (player.getGameID() != 0){
      response.redirect(WebServer.GAME_URL);
      halt();
      return null;
    }

    if (player.getName() == null){
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;
    }

    vm.put(GetHomeRoute.PLAYER_NAME, player.getName());
    vm.put(GetHomeRoute.ELO_RATING_ATTR, player.getMatchMakeScore());

    if (!playerLobby.isPlayerSearching(player)){

      vm.put(IS_PLAYER_IN_QUEUE, false);
      vm.put(INITIAL_SLIDER_POSITION, 1);
      vm.put(INITIAL_ELO_FLOOR, player.getMatchMakeScore() - 25);
      vm.put(INITIAL_ELO_CEILING, player.getMatchMakeScore() + 25);

    } else {
      vm.put(IS_PLAYER_IN_QUEUE, true);
      vm.put(ELO_FLOOR, player.getAllowedEloFloor());
      vm.put(ELO_CEILING, player.getAllowedEloCeiling());
    }







    return templateEngine.render(new ModelAndView(vm, "matchmaking.ftl"));


  }
}
