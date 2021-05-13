package com.webcheckers.ui;
/*
import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

class PostCheckTurnRouteTest {

    private PostSubmitTurnRoute CuT;
    private GameCenter gameCenter;
    private PlayerLobby playerLobby;
    private Game game;
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private Gson gson;

    @BeforeEach
    /**
     * Setup new mock and friendly object for test
     */
/*    public void setup() {

        // set up mock objects
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        playerLobby = new PlayerLobby();
        Player player = new Player(playerLobby);
        Player opponent = new Player(playerLobby);
        playerLobby.newPlayerLogin(session, "player");
        playerLobby.newPlayerLogin(session, "opponent");
        game = new Game(player, opponent, 0);
        CuT = new PostSubmitTurnRoute(gameCenter);
    }

    @Test
    /**
     * Test that PostSubmitTurn will return Message
     * with correct information when there are making moves
     */
/*    public void checkturntest() throws Exception {
        // Arrange the test scenario: The session holds player
        String player = PlayerLobby.class.getName();
        when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player);
        Move move = new Move(new Position(3, 4), new Position(4, 3));
        move.getStart();
        gameCenter.getGame(((Player) session.attribute(GetHomeRoute.PLAYER_KEY)).getGameID()).
                makeMove(game.getMoveList(),game.getBoard());
        CuT = new PostSubmitTurnRoute(gameCenter);
        final Response response = mock(Response.class);
        gson = new Gson();
        // Invoke the test
        Message message = gson.fromJson((String) CuT.handle(request, response), Message.class);

        // Analyze the results:
        final String type = message.getType();
        assertNotNull(type);
        assertTrue(type instanceof String);
        final String text = message.getText();
        assertNotNull(text);
        assertTrue(text instanceof String);
        assertEquals(Message.INFO, type);
        assertEquals("Checked Turn ", text);
    }
}*/