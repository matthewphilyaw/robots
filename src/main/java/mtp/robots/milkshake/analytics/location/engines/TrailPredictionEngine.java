package mtp.robots.milkshake.analytics.location.engines;

import mtp.robots.milkshake.analytics.location.*;
import mtp.robots.milkshake.analytics.DirectedGraph;
import mtp.robots.milkshake.util.RingBuffer;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

public class TrailPredictionEngine implements LocationEngine  {
    DirectedGraph<LocationVertex, LocationEdge> g = new DirectedGraph<LocationVertex, LocationEdge>();
    RingBuffer<LocationVertex> trail = new RingBuffer<LocationVertex>(3);
    int maxEdgeVisit = 0;

    public void updateEngine(AdvancedRobot host, ScannedRobotEvent target) {
        LocationVertex vert = new LocationVertex(target.getHeading(), -1, RoundingMode.HALF_UP);
        g.addVertex(vert);
        if (trail.getItems().size() > 0 && !vert.equals(trail.getItems().get(0))) {
            LocationVertex lastVertData = trail.getItems().get(0);
            try {
                if (!g.getEdges(lastVertData).containsKey(vert)) g.addEdge(lastVertData, vert, new LocationEdge());
                g.getEdges(lastVertData).get(vert).add(trail.getFnvHash());
                if (g.getEdges(lastVertData).get(vert).getPaths().get(trail.getFnvHash()) > maxEdgeVisit)
                    maxEdgeVisit = g.getEdges(lastVertData).get(vert).getPaths().get(trail.getFnvHash());

            } catch (Exception ex) { System.out.println(ex.getMessage()); }
        }
        trail.add(vert);
    }

    public List<LocationPrediction> getNextNPredictions(Integer n, AdvancedRobot host, ScannedRobotEvent target) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Double getSuccessRate() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String print() {
        Integer visitThreshold = maxEdgeVisit - (maxEdgeVisit / 3);
        StringBuilder sb = new StringBuilder();
        sb.append("digraph G {\n");
        sb.append("    graph [layout=neato bgcolor=black fontcolor=white fontsize=40 overlap=prism sep=1 rankdir=LR label=");
        sb.append(visitThreshold);
        sb.append("]\n");
        sb.append("    node [color=white, fontcolor=white]\n");
        sb.append("    edge [color=white splines=ortho fontcolor=white]\n");

        for (Map.Entry<LocationVertex, Map<LocationVertex, LocationEdge>> vert : g.getVertices().entrySet()) {
            for (Map.Entry<LocationVertex, LocationEdge> ed : vert.getValue().entrySet()) {
                int sum = 0;
                for (Integer i : ed.getValue().getPaths().values()) {
                    sum += i;
                }
                if (sum < visitThreshold) continue;
                sb.append("    ");
                sb.append(vert.getKey().toString());
                sb.append(" -> ");
                sb.append(ed.getKey().toString());
                sb.append(" [label=");
                sb.append(sum);
                sb.append("]\n");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
