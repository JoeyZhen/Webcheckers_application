package com.webcheckers.model;

import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.webcheckers.model.ViewMode.PLAY;
import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Board;
class GameTest {
    Player red = new Player(new PlayerLobby());
    Player white = new Player(new PlayerLobby());
    Game gameTest = new Game(red, white, 1);


    @Test
    void isActive(){
        assertTrue(gameTest.isActive());
    }

    @Test
    void getViewMode(){
        assertEquals(PLAY, gameTest.getViewMode());
    }

    @Test
    void getRedPlayer(){
        assertEquals(red, gameTest.getRedPlayer());
    }

    @Test
    void getWhitePlayer(){
        assertEquals(white, gameTest.getWhitePlayer());
    }

    @Test
    void getActiveColor(){
        assertEquals(Color.RED, gameTest.getActiveColor());
    }

    @Test
    void getGameNum(){
        assertEquals(1, gameTest.getGameNum());
    }

    @Test
    void nextTurn(){
        gameTest.nextTurn();
        assertEquals(2, gameTest.getTurnNum());
    }

    @Test
    void sleep(){
        gameTest.sleep();
        assertEquals(ViewMode.SPECTATOR, gameTest.getViewMode());
        assertEquals(false, gameTest.isActive());
    }

}
