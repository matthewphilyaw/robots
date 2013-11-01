package mtp.robots.milkshake.analytics.location;

import mtp.robots.milkshake.util.RingBuffer;

import java.util.*;

public class TrailData {
    RingBuffer trailBuffer;
    Map<Integer, TrailPath> paths = new HashMap<Integer, TrailPath>();

    public TrailData(RingBuffer trailBuffer) {
        this.trailBuffer = trailBuffer;
    }

    public RingBuffer getTrailBuffer() {
        return trailBuffer;
    }

    public Map<Integer, TrailPath> getPaths() {
        return paths;
    }
}
