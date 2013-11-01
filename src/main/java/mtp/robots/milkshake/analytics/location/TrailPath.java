package mtp.robots.milkshake.analytics.location;

import robocode.ScannedRobotEvent;

import java.util.*;

public class TrailPath implements LocationPrediction {
    Long trailHash;
    Long visitCount;
    Long totalTicksForPath;
    List<ScannedRobotEvent> scannedRobotEvents;

    public TrailPath(Long trailHash) {
        this.trailHash = trailHash;
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
