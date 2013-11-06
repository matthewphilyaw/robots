package mtp.robots.milkshake.analytics.location.Trail;

import mtp.robots.milkshake.analytics.location.Prediction;
import robocode.ScannedRobotEvent;

import java.util.*;

public class Path {
    Integer trailHash;
    Long visitCount = 0L;
    Long totalTicksForPath = 0L;

    public Path(Integer trailHash) {
        this.trailHash = trailHash;
    }

    public Integer getTrailHash() {
        return this.trailHash;
    }

    public Long getAvgTicksFromLastPrediction() {
        return this.totalTicksForPath / this.visitCount;
    } 

    public Long getVisitCount() {
        return this.visitCount;
    }

    public void updatePath(Long ticksFromLastTrail) {
        this.totalTicksForPath += ticksFromLastTrail;
        this.visitCount++;
    }
}
