package mtp.robots.milkshake.test.analytics.DirectedGraph;

import mtp.robots.milkshake.analytics.DirectedGraph;
import mtp.robots.milkshake.analytics.RVertexData;

import org.junit.Test;
import static org.junit.Assert.*;

public class EdgeCount {

    @Test
    public void zeroOnGraphCreation() throws Exception {
        DirectedGraph<RVertexData, Double> g = new DirectedGraph<RVertexData, Double>();
        assertEquals(0, g.edgeCount());
    }

    @Test
    public void zeroWhenAllEdgesAreRemoved() throws Exception {
        DirectedGraph<RVertexData, Double> g = new DirectedGraph<RVertexData, Double>();

        g.addVertex(new RVertexData(42.1));
        g.addVertex(new RVertexData(42.2));
        g.addVertex(new RVertexData(45));
        g.addVertex(new RVertexData(43));

        g.addEdge(new RVertexData(42.1), new RVertexData(42.2), 1.0);
        g.addEdge(new RVertexData(42.2), new RVertexData(43), 1.0);
        g.addEdge(new RVertexData(42.2), new RVertexData(45), 1.0);

        g.removeEdges();

        assertEquals(0, g.edgeCount());
    }

    @Test
    public void oneWhenSinlgeEdgeAdded() throws Exception {
        DirectedGraph<RVertexData, Double> g = new DirectedGraph<RVertexData, Double>();

        RVertexData u = new RVertexData(43.1);
        RVertexData v = new RVertexData(43.1);

        g.addVertex(u);
        g.addVertex(v);
        g.addEdge(u, v, 1.0);

        assertEquals(1, g.edgeCount());
    }

    @Test
    public void oneLessWhenSingleEdgeRemoved() throws Exception {
        DirectedGraph<RVertexData, Double> g = new DirectedGraph<RVertexData, Double>();

        g.addVertex(new RVertexData(42.1));
        g.addVertex(new RVertexData(42.2));
        g.addVertex(new RVertexData(45));
        g.addVertex(new RVertexData(43));

        g.addEdge(new RVertexData(42.1), new RVertexData(42.2), 1.0);
        g.addEdge(new RVertexData(42.2), new RVertexData(43), 1.0);
        g.addEdge(new RVertexData(42.2), new RVertexData(45), 1.0);

        g.removeEdge(new RVertexData(42.1), new RVertexData(42.2));
        assertEquals(2, g.edgeCount());
    }
}
