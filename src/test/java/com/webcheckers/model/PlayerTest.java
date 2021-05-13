package com.webcheckers.model;

import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class PlayerTest {

    Player playerTest = new Player(new PlayerLobby());


    @Test
    void setName() {
    playerTest.setName("Ademide");
    assertEquals("Ademide", playerTest.getName());
    }

    @Test
    void getName() {
        playerTest.setName("Adam");
        assertEquals("Adam", playerTest.getName());
    }

    @Test
    void endGame() {
        playerTest.endGame();
        assertEquals(0, playerTest.getGameID());
    }

    @Test
    void setGameID() {
        playerTest.setGameID(10);
        assertEquals(10, playerTest.getGameID());
    }

    @Test
    void getGameID() {
        playerTest.setGameID(5);
        assertEquals(5, playerTest.getGameID());
    }
}