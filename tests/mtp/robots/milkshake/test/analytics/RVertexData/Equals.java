package mtp.robots.milkshake.test.analytics.RVertexData;

import mtp.robots.milkshake.analytics.RVertexData;

import org.junit.Test;
import static org.junit.Assert.*;

public class Equals {
    @Test
    public void twoInstancesWithSameValueAreEqual() {
        assertTrue(new RVertexData(42.3).equals(new RVertexData(42.3)));
    }

    @Test
    public void twoInstancesThatRoundTheSameAreEqual() {
        assertTrue(new RVertexData(42.3).equals(new RVertexData(42.34)));
    }

    @Test
    public void twoOfTheSameInstanceAreEqual() {
        RVertexData rd = new RVertexData(42.3);
        assertTrue(rd.equals(rd));
    }

    @Test
    public void twoOfTheSameInstanceAreEqualWithOperator() {
        RVertexData rd = new RVertexData(42.3);
        assertTrue(rd == rd);
    }
}
