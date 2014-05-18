package mtp.robots.milkshake.bot;

import mtp.robots.milkshake.analytics.location.*;
import mtp.robots.milkshake.util.Point;

public class DrawPrediction {
    final PredictionGroup pg;
    final Point self;

    public DrawPrediction(PredictionGroup p, Point s) {
        this.pg = p;
        this.self = s;
    }

    public PredictionGroup getPredictionGroup() {
        return this.pg;
    }

    public Point getSelfPoint() {
        return this.self;
    }
}
