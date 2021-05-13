package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.text.DefaultEditorKit;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

        final Position Start = new Position(1);
        final Position End = new Position(2);
        final Move CuT1 = new Move(Start, End);


        //No need for constructor tests here

    @Test
    void getStart() {
        assertEquals(Start, CuT1.getStart(),"Error in Move.getStart()");
    }

    @Test
    void getEnd() {
        assertEquals(End, CuT1.getEnd(), "Error in Move.getEnd()");
    }
}