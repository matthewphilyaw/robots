package mtp.robots.milkshake.analytics.location.Trail;

import mtp.robots.milkshake.util.RingBuffer;

import java.util.*;

public class Data {
    RingBuffer<Position> trail;
    Long lastUpdateTick;
    Position position;
    Map<Integer, Path> paths = new HashMap<Integer, Path>();

    public Data(RingBuffer<Position> trail, Long lastUpdateTick) {
        this.trail = trail;
        this.lastUpdateTick = lastUpdateTick;
    }

    public RingBuffer<Position> getTrail() {
        return this.trail;
    }

    public Map<Integer, Path> getPaths() {
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
