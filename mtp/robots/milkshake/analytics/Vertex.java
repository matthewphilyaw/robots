package mtp.robots.milkshake.analytics;


import java.util.concurrent.*;
import java.util.*;

public class Vertex<TVertexData, TEdgeData> {
    final ConcurrentHashMap<TVertexData, TEdgeData> adj = new ConcurrentHashMap<TVertexData, TEdgeData>();
    final TVertexData data;

    public Vertex(TVertexData data) {
       this.data = data;
    }

    public TEdgeData getEdgeData(TVertexData key) {
        return adj.get(key);
    }

    public void setEdgeData(TVertexData key, TEdgeData data) {
        adj.replace(key, data);
    }

    public void addEdge(TVertexData key, TEdgeData data) {
        adj.putIfAbsent(key, data);
    }

    public void removeEdge(TVertexData key) {
        adj.remove(key);
    }

    public int getEdgeCount() {
        return adj.size();
    }

    public Enumeration<TVertexData> getEdges() {
        return adj.keys();
    }
}
