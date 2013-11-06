package mtp.robots.milkshake.analytics.location.Trail;

import mtp.robots.milkshake.util.RingBuffer;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Data {
    final int scale = -1;
    final int hashCode;

    final RoundingMode roundingMode = RoundingMode.HALF_UP;
    final List<ScannedRobotEvent> targetHistory = new ArrayList<ScannedRobotEvent>();
    final RingBuffer<Data> trail;

    final Double hostHeading;
    final Double hostVelocity;
    final Double targetHeading;
    final Double targetVelocity;
    final Double targetDistance;
    final Double targetBearing;

    Long lastUpdateTick;
    Map<Integer, Path> paths = new HashMap<Integer, Path>();

    public Data(AdvancedRobot host, ScannedRobotEvent target, RingBuffer<Data> trail) {
        this.trail = trail;
        this.lastUpdateTick = target.getTime();

        this.hostHeading = Data.fuzzyWuzzy(host.getHeading(), scale, roundingMode);
        this.hostVelocity = Data.fuzzyWuzzy(host.getVelocity(), 0, roundingMode);
        this.targetHeading = Data.fuzzyWuzzy(target.getHeading(), scale, roundingMode);
        this.targetVelocity = Data.fuzzyWuzzy(target.getVelocity(), 0, roundingMode);
        this.targetDistance = Data.fuzzyWuzzy(target.getDistance(), scale, roundingMode);
        this.targetBearing = Data.fuzzyWuzzy(target.getBearing(), scale, roundingMode);

        this.targetHistory.add(target);

        StringBuilder sb = new StringBuilder();
        sb.append(this.hostHeading);
        //sb.append(this.hostVelocity);
        sb.append(this.targetBearing);
        sb.append(this.targetHeading);
        //sb.append(this.targetVelocity);
        sb.append(this.targetDistance);

        this.hashCode = sb.toString().hashCode();
    }

    public RingBuffer<Data> getTrail() {
        return this.trail;
    }

    public Map<Integer, Path> getPaths() {
        return Collections.unmodifiableMap(this.paths);
    }

    public void addPath(Integer trailHash, ScannedRobotEvent target) {
        if (!paths.containsKey(trailHash)) {
            paths.put(trailHash, new Path(trailHash));
        }
        paths.get(trailHash).updatePath(target.getTime() - this.lastUpdateTick);
    }

    public void updateData(ScannedRobotEvent target) {
        this.lastUpdateTick = target.getTime();
        this.targetHistory.add(target);
    }

    public Long getLastUpdateTick() {
        return lastUpdateTick;
    }

    public Double getHostHeading() {
        return this.hostHeading;
    }

    public Double getHostVelocity() {
        return this.hostVelocity;
    }

    public Double getTargetHeading() {
        return this.targetHeading;
    }

    public Double getTargetVelocity() {
        return this.targetVelocity;
    }

    public Double getTargetBearing() {
        return this.targetBearing;
    }

    public Double getTargetDistance() {
        return this.targetDistance;
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
}
