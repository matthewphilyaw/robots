package mtp.robots.milkshake.test.analytics.DirectedGraph;

import mtp.robots.milkshake.analytics.DirectedGraph;
import mtp.robots.milkshake.analytics.RVertexData;

import org.junit.Test;
import static org.junit.Assert.*;

public class Vertex {
    @Test
    public void zeroOnGraphCreation() throws Exception {
        DirectedGraph<RVertexData, Double> g = new DirectedGraph<RVertexData, Double>();
        assertEquals(0, g.getVertices().size());
    }

    @Test
    public void zeroWhenAllVerticesAreRemoved() throws Exception {
        DirectedGraph<Integer, Double> g = new DirectedGraph<Integer, Double>();
        Common.addNVertices(g, 10);
        g.removeVertices();
        assertEquals(0, g.getVertices().size());
    }

    @Test
    public void oneWhenSingleVertexAdded() throws Exception {
        DirectedGraph<Integer, Double> g = new DirectedGraph<Integer, Double>();
        g.addVertex(0);
        assertEquals(1, g.getVertices().size());
    }

    @Test
    public void oneLessWhenSingleVertexRemoved() throws Exception {
        DirectedGraph<Integer, Double> g = new DirectedGraph<Integer, Double>();
        int n = 40;
        Common.addNVertices(g, n);
        g.removeVertex(0);
        assertEquals(n-1, g.getVertices().size());
    }

    @Test
    public void verifyVertexIsOnlyAddedOnce() throws Exception {
        DirectedGraph<Integer, Double> g = new DirectedGraph<Integer, Double>();
        Common.addNDuplicateVertices(g, 10);
        assertEquals(1, g.getVertices().size());
    }

    @Test
    public void verifyVertexWasAdded() throws Exception {
        DirectedGraph<Integer, Float> g = new DirectedGraph<Integer, Float>();
        g.addVertex(1);
        assertTrue(g.getVertices().containsKey(1));
    }
}
