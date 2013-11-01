package mtp.robots.milkshake.analytics.location;

import mtp.robots.milkshake.util.RingBuffer;

import java.util.*;

public class TrailData {
    Long trailHash;
    Long lastUpdateTick;
    Position position;
    Map<Long, TrailPath> paths = new HashMap<Long, TrailPath>();

    public TrailData(Long trailHash, Long lastUpdateTick, Position position) {
        this.trailHash = trailHash;
        this.lastUpdateTick = lastUpdateTick;
        this.position = position;
    }

    public Long getTrailHash() {
        return this.trailHash;
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
        return this.position;
    }
}
