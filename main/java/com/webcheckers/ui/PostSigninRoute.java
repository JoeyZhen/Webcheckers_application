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
 * The UI controller to POST a sign in request
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class PostSigninRoute implements Route {

  private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());

  static final String NAME_PARAM = "myText";
  static final String MESSAGE_ATTR = "message";



  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;

  /**
   * Shows the wrong message when there are two same name appeared
   * @param name Player's name
   * @return Error message
   */
  static String makeBadNameMessage(final String name){
    return String.format("The name %s is already in use by another player.", name);
  }

  /**
   * Shows the name is invalid or not
   * @param name Player's name
   * @return Error message
   */
  static String makeInvalidNameMessage(final String name){
    return String.format("The name \"%s\" is invalid. Names must contain at least one alphanumeric character.", name);
  }

  static String makeBadQuotationNameMessage(final String name){
    return String.format("The name \"%s\" is invalid. Names cannot contain a quotation mark.", name);
  }


  /**
   * Create the Post sign-in route object
   * @param playerLobby The interface page for player
   * @param templateEngine The template engine
   */
  public PostSigninRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine){
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;
  }

  /**
   * The function handle the request from players and
   * the response from the server.
   * @param request The player's request
   * @param response The server's response
   * @return the response from server
   * @throws Exception
   */
  @Override
  public Object handle(Request request, Response response) {

    final Map<String, Object> vm = new HashMap<>();
    vm.put(GetHomeRoute.TITLE_ATTR, GetSigninRoute.TITLE);


    final Session session = request.session();
    final Player player = session.attribute(GetHomeRoute.PLAYER_KEY);

    if (player != null){

      final String name = request.queryParams(NAME_PARAM);

      ModelAndView mv;

      // Checks to see if the input contains at least one letter or number.
      boolean hasAlphanumericCharacter = false;

      for (char c : name.toCharArray()){
        if (Character.isLetterOrDigit(c)){
          hasAlphanumericCharacter = true;
          break;
        }
      }

      if (!hasAlphanumericCharacter){
        mv = error(vm, makeInvalidNameMessage(name));
        return templateEngine.render(mv);
      }

      if(name.contains("\"")){
        mv = error(vm, makeBadQuotationNameMessage(name));
        return templateEngine.render(mv);
      }

      if (playerLobby.playerNameAlreadyTaken(name)){
        mv = error(vm, makeBadNameMessage(name));
        return templateEngine.render(mv);
      }

      player.setName(name);
      playerLobby.newPlayerLogin(session, name);
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;

    } else {
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;
    }
  }

  /**
   *
   * @param vm The map which represents the error in model and view
   * @param message The message
   * @return new interface page for sign-in
   */
  private ModelAndView error(final Map<String, Object> vm, final String message){
    vm.put(MESSAGE_ATTR, message);
    return new ModelAndView(vm, "signin.ftl");
  }

}
