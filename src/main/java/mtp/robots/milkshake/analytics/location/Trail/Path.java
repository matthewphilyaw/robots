package mtp.robots.milkshake.analytics.location.Trail;

import mtp.robots.milkshake.analytics.location.Prediction;
import robocode.ScannedRobotEvent;

import java.util.*;

public class Path implements Prediction {
    Integer trailHash;
    Long visitCount = 0L;
    Long totalTicksForPath = 0L;
    List<ScannedRobotEvent> scannedRobotEvents = new ArrayList<ScannedRobotEvent>();

    public Path(Integer trailHash) {
        this.trailHash = trailHash;
    }

    public Integer getTrailHash() {
        return this.trailHash;
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
