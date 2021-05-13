package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetSignoutRouteTest {

    private GetSignoutRoute CuT;
    private Request request;
    private Session session;
    private Response response;
    private PlayerLobby playerLobby;
    private Player player;

    @BeforeEach
    public void setup() {

        //mocks
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        playerLobby = mock(PlayerLobby.class);
        player = new Player(playerLobby);

        when(request.session()).thenReturn(session);

        //setup test
        CuT = new GetSignoutRoute(playerLobby);
    }

    @Test
    public void signout() throws Exception {
        when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player);

        //invoke test
        CuT.handle(request, response);
    }

    @Test
    public void signedoutalready() throws Exception {
        when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(null);

        //invoke test
        CuT.handle(request, response);
    }
}