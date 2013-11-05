package mtp.robots.milkshake.analytics.location;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

import java.util.*;

public interface Prediction {
    List<ScannedRobotEvent> getScannedRobotEvents();
    Long getAvgTicksFromRoot();
    Long getVisitCount();
}
