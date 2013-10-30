package mtp.robots.milkshake.test.analytics.DirectedGraph;

import mtp.robots.milkshake.analytics.DirectedGraph;
import mtp.robots.milkshake.analytics.location.LocationVertex;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class VertexTestCase {
    @Test
    public void zeroOnGraphCreation() throws Exception {
        DirectedGraph<LocationVertex, Double> g = new DirectedGraph<LocationVertex, Double>();
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

    @Test
    public void verifyVertexWasRemovedFromGraphAndAllEdges() throws Exception {
        DirectedGraph<Integer, Double> g = Common.createFullyConnected(100);
        Integer u = 33;
        g.removeVertex(33);

        Map<Integer, Map<Integer, Double>> verts = g.getVertices();
        assertFalse(verts.containsKey(u));
        for (Map<Integer, Double> e : verts.values()) {
            assertFalse(e.containsKey(u));
        }
    }

    @Test
    public void whenAddingSameVertexEdgeDataStaysInTact() throws Exception {
        DirectedGraph<Integer, Double> g = Common.createFullyConnected(100);
        Integer u = 50;

        Map<Integer, Double> e = g.getEdges(u);
        g.addVertex(u);
        Map<Integer, Double> ea = g.getEdges(u);

        assertEquals(e, ea);
    }
}
