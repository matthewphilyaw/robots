package mtp.robots.milkshake.test.analytics.DirectedGraph;

import mtp.robots.milkshake.analytics.DirectedGraph;

import java.util.*;

public class Common {
    public static void addNVertices(DirectedGraph<Integer, Double> g, int n) {
        for (int i = 0; i < n; i++) {
            g.addVertex(i);
        }
    }

    public static void addNDuplicateVertices(DirectedGraph<Integer, Double> g, int n) {
        for (int i = 0; i < n; i++) {
            g.addVertex(0);
        }
    }

    public static DirectedGraph<Integer, Double> createFullyConnected(int numberVetices) throws Exception {
        DirectedGraph<Integer, Double> g = new DirectedGraph<Integer, Double>();
        Common.addNVertices(g, numberVetices);

        Map<Integer, Map<Integer, Double>> vertices = g.getVertices();

        for (Integer i : vertices.keySet()) {
            for (Integer k : vertices.keySet()) {
                if (k == 1) continue;
                g.addEdge(i, k, 1.0);
            }
        }
        return g;
    }
}
