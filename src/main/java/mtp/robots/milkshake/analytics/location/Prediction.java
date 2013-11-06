package mtp.robots.milkshake.analytics.location;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

import java.util.*;

public interface Prediction {
    Long getAvgTicksFromLastPrediction();
    Long getVisitCount();
    Double getHostHeading();
    Double getHostVelocity();
    Double getTargetHeading();
    Double getTargetVelocity();
    Double getTargetBearing();
    Double getTargetDistance();
    Double getHostDistanceFromLastHostPrediction(); // distance from last host prediction's position
    Double getHostBearingFromLastHostPredictoin(); // bearing from last host prediction's position
}
