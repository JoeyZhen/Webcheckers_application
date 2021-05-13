package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetSignInRouteTest {

    private Request request;
    private Session session;
    private Response response;
    private GetSigninRoute sign;
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;

    @Test
    public void setUp() throws Exception {
        request = mock(Request.class);
        response = mock(Response.class);
        sign = new GetSigninRoute(playerLobby,templateEngine);
        templateEngine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
    }

    @Test
    public void handle() throws Exception {

        session = request.session();
        final Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
        when(player != null);
        {
            when(player.getName() != null);
            response.redirect(WebServer.HOME_URL);
            assertTrue(response instanceof Map);
            assertNotNull(response);
        }

        final Map<String, Object> vm = new HashMap<>();
        assertEquals(vm.get(GetSigninRoute.TITLE),GetSigninRoute.TITLE);
    }
}