package mtp.robots.milkshake.analytics.location;

import java.math.*;
import java.util.*;

public class LocationEdge {
    Map<Long, Integer> paths = new HashMap<Long, Integer>();

    public void add(Long hash) {
        if (!paths.containsKey(hash)) { paths.put(hash, 0); }
        paths.put(hash, paths.get(hash) + 1);
    }

    public Map<Long, Integer> getPaths() {
        return Collections.unmodifiableMap(paths);
    }
}
