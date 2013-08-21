package mtp.robots.milkshake.test.analytics.RVertexData;

import mtp.robots.milkshake.analytics.location.LocationVertex;

import org.junit.Test;

import java.math.RoundingMode;
import java.util.Random;

import static org.junit.Assert.*;

public class Equals {
    int scale = 1;
    RoundingMode roundingMode = RoundingMode.HALF_UP;

    @Test
    public void twoInstancesWithSameValueAreEqual() {
        assertTrue(new LocationVertex(42.3, scale, roundingMode).equals(new LocationVertex(42.3, scale, roundingMode)));
    }

    @Test
    public void twoInstancesThatRoundTheSameAreEqual() {
        assertTrue(new LocationVertex(42.3, scale, roundingMode).equals(new LocationVertex(42.34, scale, roundingMode)));
    }

    @Test
    public void twoOfTheSameInstanceAreEqual() {
        LocationVertex rd = new LocationVertex(42.3, scale, roundingMode);
        assertTrue(rd.equals(rd));
    }

    @Test
    public void twoOfTheSameInstanceAreEqualWithOperator() {
        LocationVertex rd = new LocationVertex(42.3, scale, roundingMode);
        assertTrue(rd == rd);
    }

    @Test
    public void severalRandomInstancesThatRoundTheSameAreEqual() throws Exception {
        LocationVertex v = new LocationVertex(1.2, scale, roundingMode);
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            int num = r.nextInt(12500 - 12000) + 12000;
            assertTrue(v.equals(new LocationVertex(num / 10000.0, scale, roundingMode)));
        }
    }

    @Test
    public void severalRandomInstancesThatDoNotRoundTheSameAreNotEqual() throws Exception {
        LocationVertex v = new LocationVertex(1.3, scale, roundingMode);
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            int num = r.nextInt(12500 - 12000) + 12000;
            assertFalse(v.equals(new LocationVertex(num / 10000.0, scale, roundingMode)));
        }
    }
}
