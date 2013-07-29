package mtp.robots.milkshake.analytics;


import java.util.concurrent.*;
import java.util.*;

public class Vertex<TNodeData, TEdgeData> {
    private ConcurrentHashMap<Double, TEdgeData> adj = new ConcurrentHashMap<Double, TEdgeData>();
    TNodeData data;

    public Vertex(TNodeData data) {
       this.data = data;
    }

    public TEdgeData getEdgeData(double edge) {
        return adj.get(edge);
    }

    public void setEdgeData(double edge, TEdgeData data) {
        adj.put(edge, data);
    }

    public void addEdge(double edge, TEdgeData data) {
        adj.put(edge, data);
    }

    public void removeEdge(double edge) {
        adj.remove(edge);
    }

    public int getEdgeCount() {
        return adj.size();
    }

    public Enumeration<Double> getEdges() {
        return adj.keys();
    }
}
