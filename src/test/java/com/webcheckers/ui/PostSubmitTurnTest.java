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

class PostSubmitTurnRouteTest {
    // The component-under-test(CuT)
    private PostSubmitTurnRoute CuT;
    private GameCenter gameCenter;
    private PlayerLobby playerLobby;
    private Game game;

    // mock objects
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private Gson gson;

    /**
     * Setup new mock and friendly object for each test
     */
/*    @BeforeEach
    public void setup() {
        // set up mock objects
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);

        // set up friendly objects
        Player player = new Player(playerLobby);
        Player opponent = new Player(playerLobby);
        playerLobby = new PlayerLobby();
        Player player1 = playerLobby.newPlayer();
        Player player2 = playerLobby.newPlayer();
        game = new Game(player1,player2,game.getGameNum());
        gameCenter = new GameCenter();

        // component-under-test
        CuT = new PostSubmitTurnRoute(gameCenter);
    }

    /**
     * Test that PostSubmitTurnTest will return Message object with
     * correct information when there are making moves
     */
/*    @Test
    public void submit_turn_test() throws Exception {

        Player player = playerLobby.newPlayer();
        when(session.attribute(PostSigninRoute.NAME_PARAM)).thenReturn(player);
        // component-under-test
        Move move = new Move(new Position(2, 3), new Position(3, 2));
        move.getStart();
        gameCenter.getGame(player.getGameID()).getMoveList();
        CuT = new PostSubmitTurnRoute(gameCenter);
        final Response response = mock(Response.class);
        gson = new Gson();
        // Invoke the test
        Message message = gson.fromJson((String) CuT.handle(request, response), Message.class);

        // Analyze the results:
        final String type = message.getType();
        assertNotNull(type);
        assertTrue(type instanceof String);
        // text is a non-null String
        final String text = message.getText();
        assertNotNull(text);
        assertTrue(text instanceof String);
        // model contains all necessary View-Model data
        assertEquals(Message.INFO, type);
        assertEquals("Submitted turn", text);
    }

}*/