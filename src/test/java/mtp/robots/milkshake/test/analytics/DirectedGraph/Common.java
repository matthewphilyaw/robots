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
                if (k == i) continue;
                g.addEdge(i, k, 1.0);
            }
        }
        return g;
    }

    public static DirectedGraph<Integer, Double> createCircularlyConnected(int numberVetices, boolean fullyConnected) throws Exception {
        DirectedGraph<Integer, Double> g = new DirectedGraph<Integer, Double>();
        Common.addNVertices(g, numberVetices);

        Map<Integer, Map<Integer, Double>> vertices = g.getVertices();
        for (int i = 1; i < vertices.size(); i++) {
            g.addEdge(i-1, i, 1.0);
            if (fullyConnected) {
                g.addEdge(i, i-1, 1.0);
            }
        }

        if (vertices.size() > 1) {
            g.addEdge(vertices.size() - 1, 0, 1.0);
            if (fullyConnected) g.addEdge(0, vertices.size() - 1, 1.0);
        }

        return g;
    }
}
