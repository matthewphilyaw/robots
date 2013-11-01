package mtp.robots.milkshake.analytics.location;

import robocode.ScannedRobotEvent;

import java.util.*;

public class TrailPath implements LocationPrediction {
    Integer trailHash;
    Integer visitCount;
    Integer totalTicksForPath;
    List<ScannedRobotEvent> scannedRobotEvents;

    public TrailPath(Integer trailHash) {
        trailHash = trailHash;
    }

    public List<ScannedRobotEvent> getScannedRobotEvents() {
        return Collections.unmodifiableList(scannedRobotEvents);
    }

    public Integer getAvgTicksFromRoot() {
        return totalTicksForPath / visitCount; 
    } 
    public Integer getVisitCount() {
        return visitCount;
    }

    public void addScannedRobotEvent(ScannedRobotEvent e, Integer ticksFromRoot) {
        scannedRobotEvents.add(e);
        totalTicksForPath += ticksFromRoot;
        visitCount++;
    }
}
