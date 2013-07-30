package mtp.robots.milkshake.analytics;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RVertexData {
    final double heading;
    final int scale = 1;

    public RVertexData(double heading) {
        this.heading = heading;
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

    private double fuzzyWuzzy() {
        BigDecimal scaled = BigDecimal.valueOf(this.heading).setScale(this.scale, RoundingMode.HALF_UP);
        return scaled.doubleValue();
    }
}
