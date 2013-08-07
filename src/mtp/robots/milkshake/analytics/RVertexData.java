package mtp.robots.milkshake.analytics;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RVertexData {
    final double heading;
    final int scale;
    final RoundingMode roundingMode;

    public RVertexData(double heading, int scale, RoundingMode roundingMode) {
        this.heading = heading;
        this.scale = scale;
        this.roundingMode = roundingMode;
    }

    public double getScaledValue() {
        return fuzzyWuzzy();
    }

    @Override
    public int hashCode() {
         return Double.valueOf(fuzzyWuzzy()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof RVertexData)) return false;
        if (obj == this) return true;
        return obj.hashCode() == this.hashCode();

    }

    @Override
    public String toString() {
        return Double.valueOf(this.getScaledValue()).toString();
    }

    private double fuzzyWuzzy() {
        BigDecimal scaled = BigDecimal.valueOf(this.heading).setScale(this.scale, this.roundingMode);
        return scaled.doubleValue();
    }
}
