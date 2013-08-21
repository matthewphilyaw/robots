package mtp.robots.milkshake.analytics.location;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

import java.util.List;

public interface LocationEngine {
    void updateEngine(AdvancedRobot host, ScannedRobotEvent target);
    List<LocationPrediction> getNextNPredictions(Integer n, AdvancedRobot host, ScannedRobotEvent target);
    Double getSuccessRate();
    String print();
}
