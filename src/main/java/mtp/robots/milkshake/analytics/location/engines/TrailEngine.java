package mtp.robots.milkshake.analytics.location.engines;

import mtp.robots.milkshake.analytics.location.*;
import mtp.robots.milkshake.util.RingBuffer;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

import java.math.RoundingMode;
import java.util.*;

public class TrailEngine implements LocationEngine {
    RingBuffer<Position> trail = new RingBuffer<Position>(3);
    Map<Long, TrailData> g = new HashMap<Long, TrailData>();

    public void updateEngine(AdvancedRobot host, ScannedRobotEvent target) {
        if (trail.getItems().size() < trail.getSize()) return; // until we fill the buffer discard first few.

        Long currentHash = trail.getFnvHash();

        if (!g.containsKey(currentHash)) {
            g.put(currentHash, new TrailData(currentHash, target.getTime(), trail.getItems().get(0)));
        }

        trail.add(new Position(target.getHeading(), -1, RoundingMode.HALF_UP));

        // if the current trail is in the map, then on this update we need to update the trail
        // then add that trail to the paths for this trail.
        // Note we don't need to add this trail, because next update will do it.
        if (!g.get(currentHash).getPaths().containsKey(trail.getFnvHash())) {
            TrailPath tp = new TrailPath(trail.getFnvHash());
            tp.addScannedRobotEvent(target, target.getTime() - g.get(currentHash).getLastUpdateTick());
            g.get(currentHash).getPaths().put(trail.getFnvHash(), tp);
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
        return null;
    }
}
