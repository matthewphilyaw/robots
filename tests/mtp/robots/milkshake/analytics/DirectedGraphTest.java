package mtp.robots.milkshake.analytics;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DirectedGraphTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testVertexCount() throws Exception {
        // new graph, should be zero
        DirectedGraph<RVertexData, Double> g = new DirectedGraph<RVertexData, Double>();
        assertEquals(0, g.vertexCount());

        // added one vertex, count should reflect it.
        g.addVertex(new RVertexData(45.0));
        assertEquals(1, g.vertexCount());

        // removed one vertex, and in this case should go back to zero.
        g.removeVertex(new RVertexData(45.0));
        assertEquals(0, g.vertexCount());

        // similar values that round to the same nearest tenth (configurable) based on half_up rounding should not
        // only be in there once. half_up rounding rounds to the nearest neighbor unless equidistant
        // in which case it rounds up. So will up or down depending on which value is closer, or round up when exactly
        // in between.

        g.addVertex(new RVertexData(45.1));
        g.addVertex(new RVertexData(45.12));
        g.addVertex(new RVertexData(45.13));
        g.addVertex(new RVertexData(45.1356333563));
        g.addVertex(new RVertexData(45.133));
        g.addVertex(new RVertexData(45.13345));
        g.addVertex(new RVertexData(45.15));

        assertEquals(2, g.vertexCount());

        // likewise given a value near enough it should remove the corresponding vertex.
        g.removeVertex(new RVertexData(45.13424564567));
        assertEquals(1, g.vertexCount());
    }

    @Test
    public void testTotalEdgeCount() throws Exception {
        DirectedGraph<RVertexData, Double> g = new DirectedGraph<RVertexData, Double>();

        // should be one, only two vertexes exist
        g.addVertex(new RVertexData(45.345));
        g.addVertex(new RVertexData(45.566));
        g.addEdge(new RVertexData(45.345), new RVertexData(45.566), 1.0);

        assertEquals(1, g.totalEdgeCount());

        // should still only be one, similar values will round to one instance.
        g.addVertex(new RVertexData(45.345));
        g.addVertex(new RVertexData(45.335));
        g.addVertex(new RVertexData(45.325));
        g.addVertex(new RVertexData(45.3154));
        g.addVertex(new RVertexData(45.536));
        g.addVertex(new RVertexData(45.526));
        g.addVertex(new RVertexData(45.556));
        g.addVertex(new RVertexData(45.526));

        // Again values should ronud accordingly and this should still render one
        // edge.
        g.addEdge(new RVertexData(45.344), new RVertexData(45.534), 1.0);
        assertEquals(1, g.totalEdgeCount());
    }

    @Test
    public void testAddVertex() throws Exception {

    }

    @Test
    public void testRemoveVertex() throws Exception {

    }

    @Test
    public void testAddEdge() throws Exception {

    }

    @Test
    public void testRemoveEdge() throws Exception {

    }

    @Test
    public void testGetVertex() throws Exception {

    }

    @Test
    public void testCreateAndAddVertex() throws Exception {

    }
}
