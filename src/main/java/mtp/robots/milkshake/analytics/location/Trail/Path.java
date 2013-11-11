package mtp.robots.milkshake.analytics.location.Trail;

import mtp.robots.milkshake.analytics.location.Prediction;
import robocode.ScannedRobotEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Path {
    Integer trailHash;
    Long visitCount = 0L;
    Long totalTicksForPath = 0L;
    Double totalDistance = 0.0;
    Double totalBearing = 0.0;

    public Path(Integer trailHash) {
        this.trailHash = trailHash;
    }

    public Integer getTrailHash() {
        return this.trailHash;
    }

    public Long getAvgTicksFromLastPrediction() {
        return BigDecimal.valueOf(Double.valueOf(this.totalTicksForPath) / this.visitCount)
                         .setScale(0, RoundingMode.UP)
                         .longValue();
    } 

    public Long getVisitCount() {
        return this.visitCount;
    }

    public Double getAvgBearingFromLastPrediction() {
        return this.totalBearing / this.visitCount;
    }

    public Double getAvgDistanceFromLastPrediction() {
        return this.totalDistance / this.visitCount;
    }

    public void updatePath(Long ticksFromLastTrail, Double lastBearing, Double lastDistance) {
        this.totalTicksForPath += ticksFromLastTrail;
        this.totalBearing += lastBearing;
        this.totalDistance += lastDistance;
        this.visitCount++;
    }
}
