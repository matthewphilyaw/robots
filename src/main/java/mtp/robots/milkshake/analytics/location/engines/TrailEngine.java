package mtp.robots.milkshake.analytics.location.engines;

import mtp.robots.milkshake.analytics.location.*;
import mtp.robots.milkshake.util.RingBuffer;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

import java.math.*;
import java.util.*;

public class TrailEngine implements LocationEngine {
    RingBuffer<Position> trail = new RingBuffer<Position>(3);
    Map<Long, TrailData> g = new HashMap<Long, TrailData>();

    public void updateEngine(AdvancedRobot host, ScannedRobotEvent target) {
        if (trail.getItems().size() < trail.getSize()) {
            trail.add(new Position(target, 1, RoundingMode.HALF_UP));
            return; // need to fill buffer first!
        }  

        Long currentHash = trail.getFnvHash();

        if (!g.containsKey(currentHash)) {
            g.put(currentHash, new TrailData(trail.copyCurrentBuffer(), trail.getHead().getScannedRobotEvent().getTime()));
        }

    

        trail.add(new Position(target, 1, RoundingMode.HALF_UP));

        if (trail.getFnvHash() == currentHash) {
            for (int i = 0; i < trail.getSize(); i++)
            {
                if (!g.get(currentHash).getTrail().getItems().get(i).equals(trail.getItems().get(i))) {
                    System.out.println("same hash different items!");
                }
            }
        }

        if (!g.containsKey(trail.getFnvHash())) {
            g.put(trail.getFnvHash(), new TrailData(trail.copyCurrentBuffer(), trail.getHead().getScannedRobotEvent().getTime()));
        }

        // if the current trail is in the map, then on this update we need to update the trail
        // then add that trail to the paths for this trail.
        // Note we don't need to add this trail, because next update will do it.
        if (!g.get(currentHash).getPaths().containsKey(trail.getFnvHash())) {
            TrailPath tp = new TrailPath(trail.copyCurrentBuffer());
            tp.addScannedRobotEvent(target, target.getTime() - g.get(currentHash).getLastUpdateTick());
            g.get(currentHash).getPaths().put(trail.getFnvHash(), tp);
        }
        else {

            g.get(currentHash)
             .getPaths()
             .get(trail.getFnvHash())
             .addScannedRobotEvent(target, target.getTime() - g.get(currentHash).getLastUpdateTick());
        }
    }

    public List<LocationPrediction> getNextNPredictions(Integer n, AdvancedRobot host, ScannedRobotEvent target) { 
        return null;
    }

    public List<LocationPrediction> getPredictionsForNTicks(Integer ticks, AdvancedRobot host, ScannedRobotEvent target) {
        return null;
    }

    public Double getSuccessRate() {
        return null;
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        
        for (TrailData data : g.values()) {
            sb.append("(");
            for (Position ps : data.getTrail().getItems()) {
                sb.append(Double.valueOf(ps.getScaledValue()).toString() + " ");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            sb.append(data.getTrail().getFnvHash().toString());
            sb.append("\n");
            for (TrailPath p : data.getPaths().values()) {
                sb.append("     (");
                for (Position pi : p.getTrail().getItems()) {
                    sb.append(Double.valueOf(pi.getScaledValue()).toString() + " ");
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append(") ");
                sb.append(p.getTrail().getFnvHash().toString() + " ");
                sb.append(p.getVisitCount().toString());
                sb.append("\n");
            }
        }

        return sb.toString();
    }

//  public String print() {
//      StringBuilder sb = new StringBuilder();
//      sb.append("digraph G {\n");
//      sb.append("    graph [layout=neato bgcolor=black fontcolor=white fontsize=40 overlap=prism sep=1 rankdir=LR]\n");
//      sb.append("    node [color=white, fontcolor=white]\n");
//      sb.append("    edge [color=white splines=ortho fontcolor=white]\n");

//      for (TrailData data : g.values()) {
//              sb.append("    ");
//              sb.append(data.getTrail().getFnvHash().toString());
//              sb.append(" [label=\"");
//              for (Position p : data.getTrail().getItems()) {
//                  sb.append(Double.valueOf(p.getScaledValue()).toString() + " ");
//              }
//              sb.append("\"]\n");
//      }
//      
//      for (TrailData data : g.values()) {
//          for (TrailPath p : data.getPaths().values()) {
//              sb.append("    ");
//              sb.append(data.getTrail().getFnvHash().toString());
//              sb.append(" -> ");
//              sb.append(p.getTrailHash().toString());
//              sb.append(" [label=");
//              sb.append(p.getVisitCount().toString());
//              sb.append("]\n");
//          }
//      }

//      sb.append("}");
//      return sb.toString();
//  }
}
