package mtp.robots.milkshake.test.analytics.RVertexData;

import mtp.robots.milkshake.analytics.RVertexData;

import org.junit.Test;

import java.util.Random;

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

    @Test
    public void severalRandomInstancesThatRoundTheSameAreEqual() throws Exception {
        RVertexData v = new RVertexData(1.2);
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            int num = r.nextInt(12500 - 12000) + 12000;
            assertTrue(v.equals(new RVertexData(num / 10000.0)));
        }
    }

    @Test
    public void severalRandomInstancesThatDoNotRoundTheSameAreNotEqual() throws Exception {
        RVertexData v = new RVertexData(1.3);
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            int num = r.nextInt(12500 - 12000) + 12000;
            assertFalse(v.equals(new RVertexData(num / 10000.0)));
        }
    }
}
