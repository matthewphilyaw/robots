package mtp.robots.milkshake.analytics.location;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

import java.util.List;

public interface Engine {
    void updateEngine(AdvancedRobot host, ScannedRobotEvent target);
    List<Prediction> getNextNPredictions(Integer n);
    PredictionGroup getPredictionsForNTicks(Integer ticks);
    Double getSuccessRate();
    String print();
}
