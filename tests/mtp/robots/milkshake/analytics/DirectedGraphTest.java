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
        DirectedGraph<RVertexData, Double> f = new DirectedGraph<RVertexData, Double>();
        assertEquals(0, f.vertexCount());

        // added one vertex, count should reflect it.
        f.createAndAddVertex(new RVertexData(45.0));
        assertEquals(1, f.vertexCount());

        // removed one vertex, and in this case should go back to zero.
        f.removeVertex(new RVertexData(45.0));
        assertEquals(0, f.vertexCount());

        // similar values that round to the same nearest tenth (configurable) based on half_up rounding should not
        // only be in there once. half_up rounding rounds to the nearest neighbor unless equidistant
        // in which case it rounds up. So will up or down depending on which value is closer, or round up when exactly
        // in between.

        f.createAndAddVertex(new RVertexData(45.1));
        f.createAndAddVertex(new RVertexData(45.12));
        f.createAndAddVertex(new RVertexData(45.13));
        f.createAndAddVertex(new RVertexData(45.1356333563));
        f.createAndAddVertex(new RVertexData(45.133));
        f.createAndAddVertex(new RVertexData(45.13345));
        f.createAndAddVertex(new RVertexData(45.15));

        assertEquals(2, f.vertexCount());

        // likewise given a value near enough it should remove the corresponding vertex.
        f.removeVertex(new RVertexData(45.13424564567));
        assertEquals(1, f.vertexCount());
    }

    @Test
    public void testTotalEdgeCount() throws Exception {
        DirectedGraph<RVertexData, Double> f = new DirectedGraph<RVertexData, Double>();

        // should be one, only two vertexes exist
        f.createAndAddVertex(new RVertexData(45.345));
        f.createAndAddVertex(new RVertexData(45.566));
        f.addEdge(new RVertexData(45.345), new RVertexData(45.566), 1.0);

        assertEquals(1, f.totalEdgeCount());

        // should still only be one, similar values will round to one instance.
        f.createAndAddVertex(new RVertexData(45.345));
        f.createAndAddVertex(new RVertexData(45.335));
        f.createAndAddVertex(new RVertexData(45.325));
        f.createAndAddVertex(new RVertexData(45.3154));
        f.createAndAddVertex(new RVertexData(45.536));
        f.createAndAddVertex(new RVertexData(45.526));
        f.createAndAddVertex(new RVertexData(45.556));
        f.createAndAddVertex(new RVertexData(45.526));

        // Again values should ronud accordingly and this should still render one
        // edge.
        f.addEdge(new RVertexData(45.344), new RVertexData(45.534), 1.0);
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
