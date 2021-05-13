package com.webcheckers.model;

/**
 * Class for any messages from the game to be contained in and returned to the server
 * to be printed out in the game/home window
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */

public class Message {

    /**
     * Classifier for the two types of messages
     */
    public static final String INFO = "info";
    public static  final String ERROR = "error";

    private String text;
    private String type;

    /**
     * Initializer for Message lass
     * @param type Type (what kind of message)
     * @param text String of the message
     */
    public Message(String type,String text)
    {
        this.text = text;
        this.type = type;
    }

    /**
     * Getter of the text of a message
     * @return String of text
     */
    public String getText()
    {
        return this.text;
    }

    /**
     * Getter of the Type of the message
     * @return this Message's Type
     */
    public String getType()
    {
        return this.type;
    }
}
