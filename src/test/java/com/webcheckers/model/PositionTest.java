package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
public class PositionTest {
final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Test
    public void ctor_oneArg()
    {
        System.setErr(new PrintStream(out));
        new Position(-1);
        assertEquals(Position.indexOutOfBoundsLow,out.toString(), "Position allowed negative index");
        out.reset();
        new Position(33);
        assertEquals(Position.indexOutOfBoundsHigh, out.toString(), "Position allowed index above 31");
        final Position CuT1 = new Position(30);
        assertEquals(7, CuT1.getRow(), "Index Constructor broken for odd rows");
        assertEquals(4, CuT1.getCell(), "Index Constructor broken for cell in odd rows");
        final Position CuT2 = new Position(8);
        assertEquals(2, CuT2.getRow(),"Index constructor broken for even rows");
        assertEquals(1, CuT2.getCell(),"Index constructor broken for cell in even row");
    }

    @Test
    public void ctor_twoArg()
    {
        System.setErr(new PrintStream(out));
        new Position(-1,1);
        assertEquals(Position.rowOutOfBoundsLow, out.toString(), "2Arg Const allows negative row.");
        out.reset();
        new Position(1,-1);
        assertEquals(Position.cellOutOfBoundsLow, out.toString(), "2Arg Const allows negative cell");
        out.reset();
        new Position(8,1);
        assertEquals(Position.rowOutOfBoundsHigh, out.toString(), "2Arg Const allows row > 8");
        out.reset();
        new Position(1, 8);
        assertEquals(Position.cellOutOfBoundsHigh, out.toString(),"2Arg Const allows cell > 8");
    }
    @Test
    void toArrayIndex() {
        final Position CuT1 = new Position(30);
        assertEquals(30, CuT1.toArrayIndex(), "ToArrayIndex error on odd rows");
        final Position CuT2 = new Position(8);
        assertEquals(8, CuT2.toArrayIndex(), "ToArrayIndex error on even rows");

    }
}
