package mtp.robots.milkshake.test.analytics.DirectedGraph;

import mtp.robots.milkshake.analytics.DirectedGraph;
import mtp.robots.milkshake.analytics.RVertexData;

import org.junit.Test;
import static org.junit.Assert.*;

public class VertexCount {
    @Test
    public void zeroOnGraphCreation() throws Exception {
        DirectedGraph<RVertexData, Double> g = new DirectedGraph<RVertexData, Double>();
        assertEquals(0, g.vertexCount());
    }

    @Test
    public void zeroWhenAllVerticesAreRemoved() throws Exception {
        DirectedGraph<RVertexData, Double> g = new DirectedGraph<RVertexData, Double>();
        g.addVertex(new RVertexData(25));
        g.addVertex(new RVertexData(30));
        g.removeVertices();
        assertEquals(0, g.vertexCount());
    }

    @Test
    public void oneWhenSingleVertexAdded() throws Exception {
        DirectedGraph<RVertexData, Double> g = new DirectedGraph<RVertexData, Double>();
        g.addVertex(new RVertexData(25));
        assertEquals(1, g.vertexCount());
    }

    @Test
    public void oneLessWhenSingleVertexRemoved() throws Exception {
        DirectedGraph<RVertexData, Double> g = new DirectedGraph<RVertexData, Double>();
        g.addVertex(new RVertexData(25));
        g.addVertex(new RVertexData(30));
        g.removeVertex(new RVertexData(30));
        assertEquals(1, g.vertexCount());
    }
}
