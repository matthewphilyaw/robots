package mtp.robots.milkshake.analytics.location;

import mtp.robots.milkshake.util.RingBuffer;

import java.math.*;
import java.util.*;

public class TrailData {
    RingBuffer<Position> trail;
    Long lastUpdateTick;
    Position position;
    Map<Long, TrailPath> paths = new HashMap<Long, TrailPath>();

    public TrailData(RingBuffer<Position> trail, Long lastUpdateTick) {
        this.trail = trail;
        this.lastUpdateTick = lastUpdateTick;
    }

    public RingBuffer<Position> getTrail() {
        return this.trail;
    }

    public Map<Long, TrailPath> getPaths() {
        return this.paths;
    }

    public void setLastUpdateTick(Long lastUpdateTick) {
        this.lastUpdateTick = lastUpdateTick;
    }

    public Long getLastUpdateTick() {
        return lastUpdateTick;
    }

    public Position getPosition() {
        return this.trail.getHead();
    }
}
