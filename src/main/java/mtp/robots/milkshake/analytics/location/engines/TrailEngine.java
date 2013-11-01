package mtp.robots.milkshake.analytics.location.engines;

import mtp.robots.milkshake.analytics.location.*;
import mtp.robots.milkshake.util.RingBuffer;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

import java.util.*;

public class TrailEngine implements LocationEngine {
    Map<Integer, TrailData> g = new HashMap<Integer, TrailData>();

    public void updateEngine(AdvancedRobot host, ScannedRobotEvent target) {

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
