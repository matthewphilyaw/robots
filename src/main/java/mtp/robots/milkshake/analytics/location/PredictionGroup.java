package mtp.robots.milkshake.analytics.location;

import robocode.ScannedRobotEvent;

import java.util.List;

public interface PredictionGroup {
    ScannedRobotEvent getRootEvent();
    List<Prediction> getPredictions();
    Long getActualTicks();
}
