package com.webcheckers.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    Piece piece = new Piece(Type.SINGLE, Color.RED);

    @Test
    void getType() {
        assertEquals(Type.SINGLE, piece.getType());
    }

    @Test
    void getColor() {
        assertEquals(Color.RED, piece.getColor());
    }

    @Test
    void promote() {
        piece.promote();
        assertEquals(Type.KING, piece.getType());
    }
}