package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("Model-Tier")
public class SpaceTest {

    @Test
    public void spaceGetterTest() {

        final Space space = new Space(4, true, null);
        assertNull(space.getPiece());
        assertEquals(true, space.getCellIdx() == 4);
    }
    @Test
    public void isValidTest() {

        final Space s1 = new Space(10, false, null);
        final Space s2 = new Space(3, true, null);
        final Space s3 = new Space(5, true, null);

        assertEquals(false, s1.isValid());
        assertEquals(true, s2.isValid());
        assertEquals(true, s3.isValid());
    }
}