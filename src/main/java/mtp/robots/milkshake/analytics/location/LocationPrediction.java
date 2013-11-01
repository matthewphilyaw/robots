package mtp.robots.milkshake.analytics.location;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

import java.util.*;

public interface LocationPrediction {
    List<ScannedRobotEvent> getScannedRobotEvents();
    Integer getAvgTicksFromRoot();
    Integer getVisitCount();
}
