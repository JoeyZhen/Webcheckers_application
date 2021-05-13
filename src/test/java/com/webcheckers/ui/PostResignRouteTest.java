package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostResignGameTest {

    private PostResignRoute CuT;
    //Mocks
    private PlayerLobby playerLobby;
    private Game game;
    private Request request;
    private Session session;
    private Response response;
    private GameCenter gameCenter;
    private Gson gson;
    private Player player;

    /**
     * Setup before each test
     */
    @BeforeEach
    public void setup() {

        gson = new Gson();
        player = new Player(playerLobby);

        //mock
        playerLobby = mock(PlayerLobby.class);
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        game = mock(Game.class);
        gameCenter = mock(GameCenter.class);
        when(request.session()).thenReturn(session);
        when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player);
        CuT = new PostResignRoute(playerLobby, gameCenter);
    }


    /**
     * Tests case where other user already resigned
     */
    @Test
    public void otherResigned() throws Exception {
        when(player.getName()).thenReturn(null);
        CuT.handle(request, response);
    }

    /**
     * Tests case where resign is successful
     */
    @Test
    public void resign_success() throws Exception {
        when(player.getGameID() == 0).thenReturn(null);
        assertNotNull(game);
        CuT.handle(request, response);
    }
}
