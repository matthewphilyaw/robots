package mtp.robots.milkshake.analytics.location;

public interface LocationPrediction {
    Double getHeading();
    Double getVelocity();
    Integer getTicksFromLastPrediction();
}
