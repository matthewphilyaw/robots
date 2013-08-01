package mtp.robots.milkshake.test.analytics.RVertexData;

import mtp.robots.milkshake.analytics.RVertexData;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.*;

public class HashCode {
    @Test
    public void twoInstancesWithSameValueMatch() throws Exception {
        assertTrue(new RVertexData(43.1).hashCode() == new RVertexData(43.1).hashCode());
    }

    @Test
    public void twoInstancesWithDifferentValuesDoNotMatch() throws Exception {
        assertFalse(new RVertexData(43.1).hashCode() == new RVertexData(43.2).hashCode());
    }

    @Test
    public void twoInstancesThatRoundTheSameMatch() throws Exception {
        assertTrue(new RVertexData(43.1).hashCode() == new RVertexData(43.12).hashCode());
    }

    @Test
    public void severalRandomInstancesThatRoundTheSameMatch() throws Exception {
        RVertexData v = new RVertexData(1.2);
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            int num = r.nextInt(12500 - 12000) + 12000;
            assertTrue(v.hashCode() == new RVertexData(num / 10000.0).hashCode());
        }
    }

    @Test
    public void severalRandomInstancesThatDoNotRoundTheSameMatch() throws Exception {
        RVertexData v = new RVertexData(1.3);
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            int num = r.nextInt(12500 - 12000) + 12000;
            assertFalse(v.hashCode() == new RVertexData(num / 10000.0).hashCode());
        }
    }
}
