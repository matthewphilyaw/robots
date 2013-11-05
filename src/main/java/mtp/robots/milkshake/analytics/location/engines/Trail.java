package mtp.robots.milkshake.analytics.location.engines;

import mtp.robots.milkshake.analytics.location.*;
import mtp.robots.milkshake.analytics.location.Trail.*;
import mtp.robots.milkshake.util.RingBuffer;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

import java.math.*;
import java.util.*;

public class Trail implements Engine {
    RingBuffer<Position> trail = new RingBuffer<Position>(4);
    Map<Integer, Data> g = new HashMap<Integer, Data>();

    public void updateEngine(AdvancedRobot host, ScannedRobotEvent target) {
        // if the buffer is not at the declared size above, lets continue filling it.
        // otherwise the first few entries would be orphaned since we would never
        // have trail less than the declared size after it's filled.
        if (trail.getItems().size() < trail.getSize()) {
            trail.add(new Position(target, -1, RoundingMode.HALF_UP));
            return;
        }  

        // store the current trails hash so we can update the trail.
        Integer currentHash = trail.getHash();

        // If current hash doesn't exist in dictionary insert it
        if (!g.containsKey(currentHash)) {
            g.put(currentHash, new Data(trail.copyCurrentBuffer(), trail.getHead().getScannedRobotEvent().getTime()));
        }

        // update trail
        trail.add(new Position(target, -1, RoundingMode.HALF_UP));

        // Insert updated trail if it doesn't exist.
        if (!g.containsKey(trail.getHash())) {
            g.put(trail.getHash(), new Data(trail.copyCurrentBuffer(), trail.getHead().getScannedRobotEvent().getTime()));
        }

        // if we don't have the updated trail in the paths for the current trail
        // create an entry.
        if (!g.get(currentHash).getPaths().containsKey(trail.getHash())) {
            Path tp = new Path(trail.getHash());
            tp.addScannedRobotEvent(target, target.getTime() - g.get(currentHash).getLastUpdateTick());
            g.get(currentHash).getPaths().put(trail.getHash(), tp);
            return;
        }

        // We have an entry for the updated trail in the paths for the current trail
        // lets update the stats.
        g.get(currentHash)
         .getPaths()
         .get(trail.getHash())
         .addScannedRobotEvent(target, target.getTime() - g.get(currentHash).getLastUpdateTick());
    }

    public List<Prediction> getNextNPredictions(Integer n, AdvancedRobot host, ScannedRobotEvent target) {
        return null;
    }

    public List<Prediction> getPredictionsForNTicks(Integer ticks, AdvancedRobot host, ScannedRobotEvent target) {
        return null;
    }

    public Double getSuccessRate() {
        return null;
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        
        for (Data data : g.values()) {
            sb.append("(");
            for (Position ps : data.getTrail().getItems()) {
                sb.append(Double.valueOf(ps.getScaledValue()).toString() + " ");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(") ");
            sb.append(data.getTrail().getHash().toString());
            sb.append("\n");
            for (Path p : data.getPaths().values()) {
                sb.append("     (");
                for (Position pi : g.get(p.getTrailHash()).getTrail().getItems()) {
                    sb.append(Double.valueOf(pi.getScaledValue()).toString() + " ");
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
