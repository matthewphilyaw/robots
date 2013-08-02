package mtp.robots.milkshake.test.analytics.DirectedGraph;

import junit.extensions.TestSetup;
import mtp.robots.milkshake.analytics.DirectedGraph;
import mtp.robots.milkshake.analytics.RVertexData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class General {
    DirectedGraph<Integer, Double> g;
    int count = 100;

    @Before
    public void setUp() throws Exception {
        g = Common.createFullyConnected(count);
    }

    @After
    public void tearDown() throws Exception {
        g = null;
    }

    @Test
    public void verifyEdgeCount() throws Exception {
        assertEquals(count * (count - 1), g.getTotalEdgeCount());
    }

    @Test
    public void verifyVertexCount() throws Exception {
        assertEquals(count, g.getVertices().size());
    }
}
