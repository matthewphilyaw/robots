package mtp.robots.milkshake.analytics.location.engines;

import mtp.robots.milkshake.analytics.location.*;
import mtp.robots.milkshake.analytics.location.Trail.Data;
import mtp.robots.milkshake.analytics.location.Trail.Path;
import mtp.robots.milkshake.util.RingBuffer;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

import java.util.*;

public class Trail implements Engine {
    RingBuffer<Data> trail = new RingBuffer<Data>(3);
    Map<Integer, Data> g = new HashMap<Integer, Data>();

    public void updateEngine(AdvancedRobot host, ScannedRobotEvent target) {
        // if the buffer is not at the declared size above, lets continue filling it.
        // otherwise the first few entries would be orphaned since we would never
        // have trail less than the declared size after it's filled.
        if (trail.getItems().size() < trail.getSize()) {
            trail.add(new Data(host, target, trail.copyCurrentBuffer()));
            return;
        }  

        // store the current trails hash so we can update the trail.
        Integer currentHash = trail.getHash();

        // If current hash doesn't exist in dictionary insert it
        if (!g.containsKey(currentHash)) {
            g.put(currentHash, trail.getHead());
        }

        // update trail
        trail.add(new Data(host, target, trail.copyCurrentBuffer()));

        // Insert updated trail if it doesn't exist.
        if (!g.containsKey(trail.getHash())) {
            g.put(trail.getHash(), trail.getHead());
        }

        // if we don't have the updated trail in the paths for the current trail
        // create an entry.
        g.get(currentHash).addPath(trail.getHash(), target);
    }

    public List<Prediction> getNextNPredictions(Integer n) {
        return null;
    }

    public List<Prediction> getPredictionsForNTicks(Integer ticks) {
        return null;
    }

    public Double getSuccessRate() {
        return null;
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        Boolean includeStartParen = false;
        sb.append("Hh Hv|Th Tv|Tb Td\n\n");
        for (Data data : g.values()) {
            sb.append("(");
            for (Data d : data.getTrail().getItems()) {
                sb.append(d.getTargetHeading() + " ");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(") ");
            sb.append(data.getTrail().getHash().toString());
            sb.append("\n");
            for (Path p : data.getPaths().values()) {
                sb.append("    -(");
                for (Data d : g.get(p.getTrailHash()).getTrail().getItems()){
                    sb.append(d.getTargetHeading() + " ");
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append(") ");
                sb.append(p.getTrailHash() + " ");
                sb.append(p.getVisitCount().toString());
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    public String prints() {
        StringBuilder sb = new StringBuilder();
        Boolean includeStartParen = false;
        sb.append("Hh Hv|Th Tv|Tb Td\n\n");
        for (Data data : g.values()) {
            includeStartParen = true;
            for (Data d : data.getTrail().getItems()) {
                if (includeStartParen) {
                    sb.append("(");
                    includeStartParen = false;
                }
                else
                    sb.append(" ");
                sb.append(d.getHostHeading() + ", ");
                sb.append(d.getHostVelocity() + "|");
                sb.append(d.getTargetHeading() + ", ");
                sb.append(d.getTargetVelocity() + "|");
                sb.append(d.getTargetBearing() + ", ");
                sb.append(d.getTargetDistance());
                sb.append("\n");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(") ");
            sb.append(data.getTrail().getHash().toString());
            sb.append("\n");
            for (Path p : data.getPaths().values()) {
                includeStartParen = true;
                for (Data d : g.get(p.getTrailHash()).getTrail().getItems()){
                    if (includeStartParen) {
                        sb.append("    -(");
                        includeStartParen = false;
                    }
                    else
                        sb.append("      ");
                    sb.append(d.getHostHeading() + ", ");
                    sb.append(d.getHostVelocity() + "|");
                    sb.append(d.getTargetHeading() + ", ");
                    sb.append(d.getTargetVelocity() + "|");
                    sb.append(d.getTargetBearing() + ", ");
                    sb.append(d.getTargetDistance());
                    sb.append("\n");
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append(") ");
                sb.append(p.getTrailHash() + " ");
                sb.append(p.getVisitCount().toString());
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
