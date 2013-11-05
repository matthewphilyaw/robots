package mtp.robots.milkshake.analytics.location;

import mtp.robots.milkshake.analytics.location.engines.*;

public class Manager {
    EngineType defaultEngine;

    public Manager(EngineType defaultEngine) {
        this.defaultEngine = defaultEngine;
    }

    public Engine getLocationEngine() {
        return this.getLocationEngine(defaultEngine);
    }

    public Engine getLocationEngine(EngineType engineType) {
        try {
            switch (engineType) {
                case TRAIL:
                    return new Trail();
                default:
                    throw new Exception("Could not locate engine type");
            }
        } catch (Exception e) {}

        return null;
    }
}
