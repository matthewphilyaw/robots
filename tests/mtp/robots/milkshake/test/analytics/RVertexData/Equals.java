package mtp.robots.milkshake.test.analytics.RVertexData;

import mtp.robots.milkshake.analytics.RVertexData;

import org.junit.Before;
import org.junit.Test;

import java.math.RoundingMode;
import java.util.Random;

import static org.junit.Assert.*;

public class Equals {
    int scale = 1;
    RoundingMode roundingMode = RoundingMode.HALF_UP;

    @Test
    public void twoInstancesWithSameValueAreEqual() {
        assertTrue(new RVertexData(42.3, scale, roundingMode).equals(new RVertexData(42.3, scale, roundingMode)));
    }

    @Test
    public void twoInstancesThatRoundTheSameAreEqual() {
        assertTrue(new RVertexData(42.3, scale, roundingMode).equals(new RVertexData(42.34, scale, roundingMode)));
    }

    @Test
    public void twoOfTheSameInstanceAreEqual() {
        RVertexData rd = new RVertexData(42.3, scale, roundingMode);
        assertTrue(rd.equals(rd));
    }

    @Test
    public void twoOfTheSameInstanceAreEqualWithOperator() {
        RVertexData rd = new RVertexData(42.3, scale, roundingMode);
        assertTrue(rd == rd);
    }

    @Test
    public void severalRandomInstancesThatRoundTheSameAreEqual() throws Exception {
        RVertexData v = new RVertexData(1.2, scale, roundingMode);
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            int num = r.nextInt(12500 - 12000) + 12000;
            assertTrue(v.equals(new RVertexData(num / 10000.0, scale, roundingMode)));
        }
    }

    @Test
    public void severalRandomInstancesThatDoNotRoundTheSameAreNotEqual() throws Exception {
        RVertexData v = new RVertexData(1.3, scale, roundingMode);
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            int num = r.nextInt(12500 - 12000) + 12000;
            assertFalse(v.equals(new RVertexData(num / 10000.0, scale, roundingMode)));
        }
    }
}
