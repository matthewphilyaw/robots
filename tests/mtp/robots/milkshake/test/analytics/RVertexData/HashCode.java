package mtp.robots.milkshake.test.analytics.RVertexData;

import mtp.robots.milkshake.analytics.RVertexData;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import static org.junit.Assert.*;

public class HashCode {
    int scale = 1;
    RoundingMode roundingMode = RoundingMode.HALF_UP;

    @Test
    public void twoInstancesWithSameValueMatch() throws Exception {
        assertTrue(new RVertexData(43.1, scale, roundingMode).hashCode() == new RVertexData(43.1, scale, roundingMode).hashCode());
    }

    @Test
    public void twoInstancesWithDifferentValuesDoNotMatch() throws Exception {
        assertFalse(new RVertexData(43.1, scale, roundingMode).hashCode() == new RVertexData(43.2, scale, roundingMode).hashCode());
    }

    @Test
    public void twoInstancesThatRoundTheSameMatch() throws Exception {
        assertTrue(new RVertexData(43.1, scale, roundingMode).hashCode() == new RVertexData(43.12, scale, roundingMode).hashCode());
    }

    @Test
    public void severalRandomInstancesThatRoundTheSameMatch() throws Exception {
        RVertexData v = new RVertexData(1.2, scale, roundingMode);
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            int num = r.nextInt(12500 - 12000) + 12000;
            assertTrue(v.hashCode() == new RVertexData(num / 10000.0, scale, roundingMode).hashCode());
        }
    }

    @Test
    public void severalRandomInstancesThatDoNotRoundTheSameMatch() throws Exception {
        RVertexData v = new RVertexData(1.3, scale, roundingMode);
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            int num = r.nextInt(12500 - 12000) + 12000;
            assertFalse(v.hashCode() == new RVertexData(num / 10000.0, scale, roundingMode).hashCode());
        }
    }
}
