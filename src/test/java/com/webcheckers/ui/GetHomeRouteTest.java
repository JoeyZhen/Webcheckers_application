package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import spark.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;

public class GetHomeRouteTest {
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private GetHomeRoute homeRoute;

    private Request request;
    private Session session;
    private Response response;

    @BeforeEach
    public void setup() throws Exception{
        templateEngine = mock(TemplateEngine.class);
        homeRoute= new GetHomeRoute(playerLobby, templateEngine);

        request=mock(Request.class);
        session=mock(Session.class);
        response=mock(Response.class);
        when(request.session()).thenReturn(session);

    }

    @Test
    void GetHomeRouteTests() throws Exception{

    }
}
