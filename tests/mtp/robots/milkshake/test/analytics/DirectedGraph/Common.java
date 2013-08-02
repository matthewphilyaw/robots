package mtp.robots.milkshake.test.analytics.DirectedGraph;

import mtp.robots.milkshake.analytics.DirectedGraph;

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

    public static <TVertexData, TEdgeData> DirectedGraph<TVertexData, TEdgeData> createFullyConnected(int numberVetices) {
        return null;
    }
}
