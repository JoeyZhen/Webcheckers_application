package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ReplayTest{

    int size;
    ArrayList<Move> moves;

    @BeforeEach
    public void setup(){
        moves = new ArrayList<Move>();
        size = 0;
    }

    @Test
    public void testGetPlayer1(){
        Replay replay = new Replay();
        replay.setPlayers("Joey", "Lyan");
        assertTrue(replay.getPlayer1().equals("Joey"));
    }

    @Test
    public void testGetPlayer2(){
        Replay replay = new Replay();
        replay.setPlayers("Joey", "Lyan");
        assertTrue(replay.getPlayer2().equals("Lyan"));
    }


    @Test
    public void testGetNumTurns(){
        Replay replay = new Replay();
        assertEquals(0, replay.getNumTurns());
    }

    @Test
    public void testGetMoves(){
        Replay replay = new Replay();
    }


    @Test
    public void testEquals1(){
        Replay r1 = new Replay();
        Replay r2 = new Replay();
        r1.setPlayers("Test", "Test");
        r2.setPlayers("Test", "T");
        assertFalse(r1.equals(r2));
    }

    @Test
    public void testEquals2(){
        Replay replay1 = new Replay();
        Replay replay2 = new Replay();
        replay1.setPlayers("Test", "Test");
        replay2.setPlayers("Test", "Test");
    }
}
