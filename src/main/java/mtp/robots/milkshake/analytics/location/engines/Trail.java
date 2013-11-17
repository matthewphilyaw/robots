package mtp.robots.milkshake.analytics.location.engines;

import mtp.robots.milkshake.analytics.location.*;
import mtp.robots.milkshake.analytics.location.Trail.Data;
import mtp.robots.milkshake.analytics.location.Trail.Path;
import mtp.robots.milkshake.util.RingBuffer;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

import java.util.*;

public class Trail implements Engine {
    RingBuffer<Data> trail = new RingBuffer<Data>(2);
    Map<Integer, Data> g = new HashMap<Integer, Data>();

    public void updateEngine(AdvancedRobot host, ScannedRobotEvent target) {
        // if the buffer is not at the declared size above, lets continue filling it.
        // otherwise the first few entries would be orphaned since we would never
        // have trail less than the declared size after it's filled.
        if (trail.getItems().size() < trail.getSize()) {
            Data d = new Data(host, target, trail.copyCurrentBuffer());
            d.updateData(host, target);
            trail.add(d);
            return;
        }  

        // store the current trails hash so we can update the trail.
        Integer currentHash = trail.getHash();

        // If current hash doesn't exist in dictionary insert it
        if (!g.containsKey(currentHash)) {
            g.put(currentHash, trail.getHead());
        }

        // update trail
        Data d = new Data(host, target, trail.copyCurrentBuffer());
        d.updateData(host, target);
        trail.add(d);

        // Insert updated trail if it doesn't exist.
        if (!g.containsKey(trail.getHash())) {
            g.put(trail.getHash(), trail.getHead());
        }

        // need to update the data every scan as well.
        g.get(trail.getHash()).updateData(host, target);

        g.get(currentHash).addPath(trail.getHash(), host, target);
    }

    public List<Prediction> getNextNPredictions(Integer n) {
        return null;
    }

    public PredictionGroup getPredictionsForNTicks(Integer ticks) {
        final ScannedRobotEvent root = trail.getHead().getTargetHistory().get(trail.getHead().getTargetHistory().size() - 1);
        PredictionGroup pg = new PredictionGroup() {
            private List<Prediction> list = new ArrayList<Prediction>();
            @Override
            public ScannedRobotEvent getRootEvent() {
                return root;
            }

            @Override
            public List<Prediction> getPredictions() {
                return list;
            }
        };

        Long ltick = Long.valueOf(ticks);
        Integer lastHash = trail.getHash();
        while (ltick > 0) {
            Path path = null;
            if (!g.containsKey(lastHash)) {
                ltick = 0L;
                continue;
            }
            if (g.get(lastHash).getPaths().size() == 0) {
                ltick = 0L;
                continue;
            }

            for (Path p : g.get(lastHash).getPaths().values()) {
                if (path == null) {
                    path = p;
                }
                else if (path.getVisitCount() < p.getVisitCount() &&
                         path.getAvgTicksFromLastPrediction() > 0 &&
                         path.getVisitCount() > 1) {
                    path = p;
                }
            }

            if (path == null) {
                ltick = 0L;
                continue;
            }

            if (path.getAvgTicksFromLastPrediction() <= 0) {
                ltick = 0L;
                continue;
            }

            pg.getPredictions().add(this.mapPathToPrediction(path));
            ltick -= path.getAvgTicksFromLastPrediction();
            // follow the.
            lastHash = path.getTrailHash();
        }
        return pg;
    }

    public Double getSuccessRate() {
        return null;
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        for (Data data : g.values()) {
            sb.append(data.getTrail().getItems().get(0).getTargetHeading() + "\n");
            for (Path p : data.getPaths().values()) {
                sb.append("    ");
                sb.append(g.get(p.getTrailHash()).getTrail().getItems().get(0).getTargetHeading() + " - ");
                sb.append(p.getAvgTicksFromLastPrediction() + " ");
                sb.append(p.getAvgBearingFromLastPrediction() + " ");
                sb.append(p.getAvgDistanceFromLastPrediction() + " ");
                sb.append(p.getVisitCount().toString());
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private Prediction mapPathToPrediction(final Path p) {
        return new Prediction() {
            @Override
            public Long getAvgTicksFromLastPrediction() {
                return p.getAvgTicksFromLastPrediction().longValue();
            }

            @Override
            public Long getVisitCount() {
                return p.getVisitCount();
            }

            @Override
            public Double getTargetHeading() {
                return g.get(p.getTrailHash()).getTargetHeading();
            }

            @Override
            public Double getTargetVelocity() {
                return g.get(p.getTrailHash()).getTargetVelocity();
            }

            @Override
            public Double getTargetBearingFromLastPrediction() {
                return p.getAvgBearingFromLastPrediction();
            }

            @Override
            public Double getTargetDistanceFromLastPrediction() {
                return p.getAvgBearingFromLastPrediction();
            }
        };
    }
}
