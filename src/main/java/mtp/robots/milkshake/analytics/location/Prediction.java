package mtp.robots.milkshake.analytics.location;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

import java.util.*;

public interface Prediction {
    Long getAvgTicksFromLastPrediction();
    Long getVisitCount();
    Double getTargetHeading();
    Double getTargetVelocity();
    Double getTargetBearingFromLastPrediction();
    Double getTargetDistanceFromLastPrediction();
}
