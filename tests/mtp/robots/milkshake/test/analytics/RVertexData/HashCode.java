package mtp.robots.milkshake.test.analytics.RVertexData;

import mtp.robots.milkshake.analytics.RVertexData;

import org.junit.Test;
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
    public void severalInstancesThatRoundTheSameMatch() throws Exception {

    }
}
