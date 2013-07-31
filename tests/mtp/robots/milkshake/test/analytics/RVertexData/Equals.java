package mtp.robots.milkshake.test.analytics.RVertexData;

import mtp.robots.milkshake.analytics.RVertexData;

import org.junit.Test;
import static org.junit.Assert.*;

public class Equals {
    @Test
    public void twoInstancesWithSameValueAreEqualExplicit() {
        assertTrue(new RVertexData(42.3).equals(new RVertexData(42.3)));
    }

    @Test
    public void twoInstancesWithSimilarValueAreEqualExplicit() {
        assertTrue(new RVertexData(42.3).equals(new RVertexData(42.34)));
    }
}
