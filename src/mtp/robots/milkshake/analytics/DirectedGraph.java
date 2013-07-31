package mtp.robots.milkshake.analytics;

import java.util.*;

public class DirectedGraph<TVertexData, TEdgeData> {
    HashMap<TVertexData, HashMap<TVertexData, TEdgeData>> vertices =
           new HashMap<TVertexData, HashMap<TVertexData, TEdgeData>>();

    public int vertexCount() { return this.vertices.size(); }

    public int totalEdgeCount() {
        int count = 0;

        for (HashMap<TVertexData, TEdgeData> d : this.vertices.values()) {
           count += d.size();
        }
        return count;
    }

    public void addVertex(TVertexData u) {
        vertices.put(u, new HashMap<TVertexData, TEdgeData>());
    }

    public void removeVertex(TVertexData u) throws Exception {
        if (!vertices.containsKey(u)) throw new Exception("u is not in graph");
        vertices.remove(u);

        for (HashMap<TVertexData, TEdgeData> e : this.vertices.values()) {
            e.remove(u);
        }
    }

    public void addEdge(TVertexData u, TVertexData v, TEdgeData data) throws Exception {
        if (!vertices.containsKey(u)) throw new Exception("u is not in graph");
        if (!vertices.containsKey(v)) throw new Exception("v is not in graph");
        vertices.get(u).put(v, data);
    }

    public void removeEdge(TVertexData u, TVertexData v) throws Exception {
        if (!vertices.containsKey(u)) throw new Exception("u is not in graph");
        vertices.get(u).remove(v);
    }

    public Set<Map.Entry<TVertexData, TEdgeData>> getEdges(TVertexData u) throws Exception {
        if (!vertices.containsKey(u)) throw new Exception("u is not in graph");
        return vertices.get(u).entrySet();
    }
}
