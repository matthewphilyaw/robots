package mtp.robots.milkshake.test.analytics.DirectedGraph;

import junit.extensions.TestSetup;
import mtp.robots.milkshake.analytics.DirectedGraph;
import mtp.robots.milkshake.analytics.RVertexData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;

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

    private void writeOut(DirectedGraph<Integer, Double> g, String name) throws Exception {
        File f = new File(name);
        FileWriter fw = new FileWriter(f.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(g.toString());
        bw.flush();
        bw.close();
    }

    @Test
    public void dumpFullyConnected() throws Exception { writeOut(Common.createFullyConnected(10), "fully-connected.dat"); }

    @Test
    public void dumpCircular() throws Exception { writeOut(Common.createCircularlyConnected(10, false), "circular.dat"); }

    @Test
    public void dumpCircularFullyConnected() throws Exception { writeOut(Common.createCircularlyConnected(10, true), "circularfully.dat"); }
}
