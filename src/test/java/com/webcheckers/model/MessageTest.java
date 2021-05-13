package com.webcheckers.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    final String test = "a9dj4k395j";
    final Message mess = new Message(Message.ERROR, test);
    @Test
    void getText() {
        assertEquals(test, mess.getText(), "Error in getText");
    }

    @Test
    void getType() {
        assertEquals(Message.ERROR, mess.getType(), "Error in getType");
    }
}