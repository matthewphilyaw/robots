package mtp.robots.milkshake.analytics.location;

import mtp.robots.milkshake.analytics.location.engines.TrailPredictionEngine;

public class LocationManager {
    LocationEngineType defaultEngine;

    public LocationManager(LocationEngineType defaultEngine) {
        this.defaultEngine = defaultEngine;
    }

    public LocationEngine getLocationEngine() {
        return this.getLocationEngine(defaultEngine);
    }

    public LocationEngine getLocationEngine(LocationEngineType engineType) {
        try {
            switch (engineType) {
                case TRAILPREDICTION:
                    return new TrailPredictionEngine();
                default:
                    throw new Exception("Could not locate engine type");
            }
        } catch (Exception e) {}

        return null;
    }
}
