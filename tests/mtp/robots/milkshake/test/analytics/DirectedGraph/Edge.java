package mtp.robots.milkshake.test.analytics.DirectedGraph;

import mtp.robots.milkshake.analytics.DirectedGraph;

import org.junit.Test;
import static org.junit.Assert.*;

public class Edge {
    @Test
    public void zeroOnGraphCreation() throws Exception {
        DirectedGraph<Integer, Double> g = new DirectedGraph<Integer, Double>();

        assertEquals(0, g.getTotalEdgeCount());
    }

    @Test
    public void zeroWhenAllEdgesAreRemoved() throws Exception {
        DirectedGraph<Integer, Double> g = new DirectedGraph<Integer, Double>();

        Common.addNVertices(g, 4);
        g.addEdge(0,1, 1.0);
        g.addEdge(1,2, 1.0);
        g.addEdge(2,3, 1.0);

        g.removeEdges();
        assertEquals(0, g.getTotalEdgeCount());
    }

    @Test
    public void oneWhenSingleEdgeAdded() throws Exception {
        DirectedGraph<Integer, Double> g = new DirectedGraph<Integer, Double>();

        Common.addNVertices(g, 2);
        g.addEdge(0, 1, 1.0);

        assertEquals(1, g.getTotalEdgeCount());
    }

    @Test
    public void oneLessWhenSingleEdgeRemoved() throws Exception {
        DirectedGraph<Integer, Double> g = new DirectedGraph<Integer, Double>();

        Common.addNVertices(g, 2);
        g.addEdge(0, 1, 1.0);
        g.removeEdge(0, 1);
        assertEquals(0, g.getTotalEdgeCount());
    }
}
