package mtp.robots.milkshake.analytics.location.Trail;

import robocode.ScannedRobotEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Position {
    final int scale;
    final RoundingMode roundingMode;
    final ScannedRobotEvent scannedRobotEvent; 

    public Position(ScannedRobotEvent scannedRobotEvent, int scale, RoundingMode roundingMode) {
        this.scale = scale;
        this.roundingMode = roundingMode;
        this.scannedRobotEvent = scannedRobotEvent;
    }

    public double getScaledValue() {
        return this.fuzzyWuzzy();
    }

    public ScannedRobotEvent getScannedRobotEvent() {
        return this.scannedRobotEvent;
    }

    @Override
    public int hashCode() {
         return Double.valueOf(fuzzyWuzzy()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Position)) return false;
        if (obj == this) return true;
        return obj.hashCode() == this.hashCode();

    }

    @Override
    public String toString() {
        return Double.valueOf(this.getScaledValue()).toString();
    }

    private double fuzzyWuzzy() {
        BigDecimal scaled = BigDecimal.valueOf(this.scannedRobotEvent.getHeading()).setScale(this.scale, this.roundingMode);
        return scaled.doubleValue();
    }
}
