package mtp.robots.milkshake.analytics.location.Trail;

import mtp.robots.milkshake.util.Point;
import mtp.robots.milkshake.util.RingBuffer;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Data {
    final int scale = -1;
    final int hashCode;

    final RoundingMode roundingMode = RoundingMode.HALF_UP;
    final List<ScannedRobotEvent> targetHistory = new ArrayList<ScannedRobotEvent>();
    final Map<Integer, Path> paths = new HashMap<Integer, Path>();
    final RingBuffer<Data> trail;

    final Double targetHeading;
    final Double targetVelocity;

    Point targetPoint;
    Long lastUpdateTick;

    public Data(AdvancedRobot host, ScannedRobotEvent target, RingBuffer<Data> trail) {
        this.trail = trail;

        Double th = target.getHeadingRadians();
        Double tv = target.getVelocity();

        if (tv < 0) {
            tv *= -1;
            th = Utils.normalAbsoluteAngle(Math.PI + th);
        }

        this.targetHeading = Data.fuzzyWuzzy(th, scale, roundingMode);
        this.targetVelocity = Data.fuzzyWuzzy(tv, 0, roundingMode);

        final StringBuilder sb = new StringBuilder();
        sb.append(this.targetHeading);
        sb.append(this.targetVelocity);

        this.hashCode = sb.toString().hashCode();
    }

    public RingBuffer<Data> getTrail() {
        return this.trail;
    }

    public Map<Integer, Path> getPaths() {
        return Collections.unmodifiableMap(this.paths);
    }

    public void addPath(Integer trailHash, AdvancedRobot host, ScannedRobotEvent target) {
        if (!paths.containsKey(trailHash)) {
            paths.put(trailHash, new Path(trailHash));
        }

        final Point currentTargetPoint = Data.getTargetPoint(host, target);
        final Point offsetPoint = new Point(currentTargetPoint.getX() - this.targetPoint.getX(),
                                            currentTargetPoint.getY() - this.targetPoint.getY());

        final Double lastBearing = Utils.normalAbsoluteAngle((Math.PI / 2) - Math.atan2(offsetPoint.getY(), offsetPoint.getX()));
        final Double lastDistance = Math.sqrt(Math.pow(Math.abs(offsetPoint.getX()), 2) + Math.pow(Math.abs(offsetPoint.getY()), 2));
        paths.get(trailHash).updatePath(target.getTime() - this.lastUpdateTick, lastBearing, Math.abs(lastDistance));
    }

    public void updateData(AdvancedRobot host, ScannedRobotEvent target) {
        this.targetPoint = Data.getTargetPoint(host, target);
        this.lastUpdateTick = target.getTime();
        this.targetHistory.add(target);

    }

    public Long getLastUpdateTick() {
        return lastUpdateTick;
    }

    public Double getTargetHeading() {
        return this.targetHeading;
    }

    public Double getTargetVelocity() {
        return this.targetVelocity;
    }

    public List<ScannedRobotEvent> getTargetHistory() {
        return this.targetHistory;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Data)) return false;
        if (obj == this) return true;
        return obj.hashCode() == this.hashCode();
    }

    private static Double fuzzyWuzzy(Double value, int scale, RoundingMode roundingMode) {
        BigDecimal scaled = BigDecimal.valueOf(value).setScale(scale, roundingMode);
        return scaled.doubleValue();
    }

    private static Point getTargetPoint(AdvancedRobot host, ScannedRobotEvent target) {
        final double bearingAngleToGrid = host.getHeadingRadians() + target.getBearingRadians();
        return new Point(host.getX() + Math.sin(bearingAngleToGrid) * target.getDistance(),
                         host.getY() + Math.cos(bearingAngleToGrid) * target.getDistance());
    }
}
