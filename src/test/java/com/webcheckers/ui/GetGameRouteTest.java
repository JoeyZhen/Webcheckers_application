package com.webcheckers.ui;

import com.webcheckers.model.Player;
import com.webcheckers.model.Game;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetGameRouteTest {
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;
    private GetGameRoute Route;

    private Request request;
    private Session session;
    private Response response;

    @BeforeEach
    public void setup() throws Exception{

        templateEngine = mock(TemplateEngine.class);
        gameCenter=mock(GameCenter.class);
        playerLobby=mock(PlayerLobby.class);
        // create a unique GameRoute for each test
        Route = new GetGameRoute(playerLobby, gameCenter, templateEngine);
        request=mock(Request.class);
        session=mock(Session.class);
        response=mock(Response.class);
        when(request.session()).thenReturn(session);
    }

    @Test
    void GetGameRouteTests() throws Exception{
        PlayerLobby playerLobby=mock(PlayerLobby.class);
        assertNotNull(Route);
        when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(playerLobby);
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        Route.handle(request, response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetGameRoute.CHALLENGE_NAME);
        //   * test view name
        testHelper.assertViewName(GetGameRoute.VIEW_MODE);
    }

    @Test
    public void faulty_session() {
        // Arrange the test scenario: There is an existing session with a PlayerServices object
        when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(null);

        // Invoke the test
        try {
            Route.handle(request, response);
            fail("Redirects invoke halt exceptions.");
        } catch (HaltException e) {
            // expected
        }

        // Analyze the results:
        //   * redirect to the Game view
        verify(response).redirect(WebServer.HOME_URL);
    }
}
