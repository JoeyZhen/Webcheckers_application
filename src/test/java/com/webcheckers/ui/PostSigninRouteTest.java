package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostSigninRouteTest {

  private static final String NO_ALPHANUMERIC_CHARACTER = "_";
  private static final String CONTAINS_DOUBLE_QUOTE_CHARACTER = "name\"";
  private static final String VALID_NAME = "name";


  private PostSigninRoute CuT;
  private PlayerLobby playerLobby;
  private Request request;
  private Session session;
  private Response response;
  private TemplateEngine templateEngine;
  private Player player;

  @BeforeEach
  public void setup(){
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    templateEngine = mock(TemplateEngine.class);
    response = mock(Response.class);

    playerLobby = new PlayerLobby();

    player = mock(Player.class);

    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player);

    CuT = new PostSigninRoute(playerLobby, templateEngine);
  }

  /**
   * Test that the signin action handles bad signins: invalid username
   */
  @Test
  public void bad_signin_1(){
    // Arrange the test scenario: The user's submitted name does not contain an alphanumeric character
    when(request.queryParams(eq(PostSigninRoute.NAME_PARAM))).thenReturn(NO_ALPHANUMERIC_CHARACTER);

    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    CuT.handle(request, response);

    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();

    testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetSigninRoute.TITLE);
    testHelper.assertViewModelAttribute(GetHomeRoute.NEW_PLAYER_ATTR, null);
    testHelper.assertViewModelAttribute(
            PostSigninRoute.MESSAGE_ATTR,
            PostSigninRoute.makeInvalidNameMessage(NO_ALPHANUMERIC_CHARACTER));
    testHelper.assertViewName(GetSigninRoute.VIEW_NAME);
  }

  /**
   * Test that the signin action handles bad signins: quote in the username
   */
  @Test
  public void bad_signin_2(){
    when(request.queryParams(eq(PostSigninRoute.NAME_PARAM))).thenReturn(CONTAINS_DOUBLE_QUOTE_CHARACTER);

    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    CuT.handle(request, response);

    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();

    testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetSigninRoute.TITLE);
    testHelper.assertViewModelAttribute(GetHomeRoute.NEW_PLAYER_ATTR, null);
    testHelper.assertViewModelAttribute(
            PostSigninRoute.MESSAGE_ATTR,
            PostSigninRoute.makeBadQuotationNameMessage(CONTAINS_DOUBLE_QUOTE_CHARACTER));
    testHelper.assertViewName(GetSigninRoute.VIEW_NAME);
  }

  /**
   * Test that the signin action handles bad signins: username is already taken.
   */
  @Test
  public void bad_signin_3(){
    when(request.queryParams(eq(PostSigninRoute.NAME_PARAM))).thenReturn(VALID_NAME);

    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    CuT.handle(request, response);

    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();

    testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetSigninRoute.TITLE);
    testHelper.assertViewModelAttribute(GetHomeRoute.NEW_PLAYER_ATTR, null);

    when(playerLobby.playerNameAlreadyTaken(VALID_NAME)).thenReturn(true);

    testHelper.assertViewModelAttribute(
            PostSigninRoute.MESSAGE_ATTR,
            PostSigninRoute.makeBadNameMessage(VALID_NAME));
    testHelper.assertViewName(GetSigninRoute.VIEW_NAME);
  }
}
