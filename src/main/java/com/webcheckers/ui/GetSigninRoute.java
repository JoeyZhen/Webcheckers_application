package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI controller to get the signin page
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class GetSigninRoute implements Route {

  private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());

  static final String TITLE = "Sign In";
  static final String VIEW_NAME = "signin.ftl";

  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;

  /**
   * Create the Spark Route (UI controller) for the
   * {@code GET /} HTTP request.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetSigninRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
    // validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;
    //
    LOG.config("GetSigninRoute is initialized.");
  }

  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetSigninRoute is invoked.");

    final Session session = request.session();
    final Player player = session.attribute(GetHomeRoute.PLAYER_KEY);

    if (player != null){
      if (player.getName() != null){
        LOG.fine(String.format("Player \"%s\" is already logged in, redirecting.", player.getName()));
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
      }
    }


    //
    Map<String, Object> vm = new HashMap<>();
    vm.put(GetHomeRoute.TITLE_ATTR, TITLE);
    return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
  }
}
