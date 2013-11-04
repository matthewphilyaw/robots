package mtp.robots.milkshake.analytics.location;

import mtp.robots.milkshake.util.*;

import robocode.ScannedRobotEvent;

import java.math.*;
import java.util.*;

public class TrailPath implements LocationPrediction {
    final RingBuffer<Position> trail;
    Long visitCount = 0L;
    Long totalTicksForPath = 0L;
    List<ScannedRobotEvent> scannedRobotEvents = new ArrayList<ScannedRobotEvent>();

    public TrailPath(RingBuffer<Position> trail) {
        this.trail = trail;
    }

    public RingBuffer<Position> getTrail() {
        return this.trail;
    }

    public List<ScannedRobotEvent> getScannedRobotEvents() {
        return Collections.unmodifiableList(scannedRobotEvents);
    }

    public Long getAvgTicksFromRoot() {
        return totalTicksForPath / visitCount; 
    } 

    public Long getVisitCount() {
        return visitCount;
    }

    public void addScannedRobotEvent(ScannedRobotEvent e, Long ticksFromRoot) {
        scannedRobotEvents.add(e);
        totalTicksForPath += ticksFromRoot;
        visitCount++;
    }
}
