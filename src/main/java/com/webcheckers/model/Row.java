package com.webcheckers.model;

import java.util.Arrays;
import java.util.Iterator;

/**
 * The model of the rows of the checkerboard
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
public class Row implements Iterable<Space>{

    private int index;
    private Space spaces[];

    /**
     * Constructor for a row
     * @param pieces array of pieces on all playable squares in the row
     * @param index index of the row to be created
     */
    public Row(Piece[] pieces, int index) {
        this.index = index;
        this.spaces = new Space[8];
        for(int i = 0; i < 8; i++) {
            if (index % 2 == 0) {
                if (i % 2 == 0) {
                    spaces[i] = new Space(i, false, null);
                } else {
                    spaces[i] = new Space(i, true, pieces[i/2]);
                }
            } else {
                if (i % 2 == 0) {
                    spaces[i] = new Space(i, true, pieces[i/2]);
                } else {
                    spaces[i] = new Space(i, false, null);
                }
            }
        }
    }

    /**
     * getter method for index
     * @return the vertical index of the row
     */
    public int getIndex()
    {
        return this.index;
    }


    /**
     * Iterator for the row
     * @return iterator of the spaces in the row
     */
    @Override
    public Iterator<Space> iterator() {
        return Arrays.asList(this.spaces).iterator();
    }
}
