package mtp.robots.milkshake.analytics;

import java.util.*;

public class REdgeData {
    Map<Long, Integer> paths = new HashMap<Long, Integer>();

    public void add(Long hash) {
        if (!paths.containsKey(hash)) { paths.put(hash, 0); }
        paths.put(hash, paths.get(hash) + 1);
    }

    public Map<Long, Integer> getPaths() {
        return Collections.unmodifiableMap(paths);
    }
}
