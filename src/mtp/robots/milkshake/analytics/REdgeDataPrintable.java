package mtp.robots.milkshake.analytics;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class REdgeDataPrintable {
    Map<Long, Object[]> paths = new HashMap<Long, Object[]>();

    public void add(Long hash, String name) {
        Object [] ed = {name, 0};
        if (!paths.containsKey(hash)) { paths.put(hash, ed); }
        Object[] mod = paths.get(hash);
        mod[1] = (Integer)mod[1] + 1;
        paths.put(hash,mod);
    }

    public Map<Long, Object[]> getPaths() {
        return Collections.unmodifiableMap(paths);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Object[] s : paths.values()) {
            sb.append(s[0]);
            sb.append(":");
            sb.append(s[1]);
            sb.append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }
}

