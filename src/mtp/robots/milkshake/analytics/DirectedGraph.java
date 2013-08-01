package mtp.robots.milkshake.analytics;

import java.util.*;

public class DirectedGraph<TVertexData, TEdgeData> {
    Map<TVertexData, Map<TVertexData, TEdgeData>> vertices = new HashMap<TVertexData, Map<TVertexData, TEdgeData>>();

    public void addVertex(TVertexData u) {
        vertices.put(u, new HashMap<TVertexData, TEdgeData>());
    }

    public void removeVertex(TVertexData u) throws Exception {
        if (!vertices.containsKey(u)) throw new Exception("u is not in graph");
        vertices.remove(u);

        for (Map<TVertexData, TEdgeData> e : this.vertices.values()) {
            e.remove(u);
        }
    }

    public Map<TVertexData, Map<TVertexData, TEdgeData>> getVertices() {
        return Collections.unmodifiableMap(vertices);
    }

    public void removeVertices() {
        vertices.clear();
    }

    public int getTotalEdgeCount() {
        int count = 0;
        for (Map<TVertexData, TEdgeData> e : vertices.values())
        {
            count += e.size();
        }
        return count;
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

    public void removeEdgesFor(TVertexData u) throws Exception {
        if (!vertices.containsKey(u)) throw new Exception("u is not in graph");
        int numEdges = vertices.get(u).size();
        vertices.get(u).clear();
    }

    public void removeEdges() throws Exception {
        for (TVertexData key : this.vertices.keySet()) {
            removeEdgesFor(key);
        }
    }

    public Map<TVertexData, TEdgeData> getEdges(TVertexData u) throws Exception {
        if (!vertices.containsKey(u)) throw new Exception("u is not in graph");
        return Collections.unmodifiableMap(vertices.get(u));
    }
}
