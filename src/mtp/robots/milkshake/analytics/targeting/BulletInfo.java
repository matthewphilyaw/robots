package mtp.robots.milkshake.analytics.targeting;

import mtp.robots.milkshake.analytics.targeting.TargetingPrediction;
import robocode.Bullet;
import java.util.UUID;

public class BulletInfo {
    private final int bulletFired;
    private final TargetingPrediction prediction;
    private final UUID battleId;
    private final UUID roundId;

    private boolean didHit;

    public BulletInfo(int bulletFired, TargetingPrediction prediction, UUID battleId, UUID roundId) {
        this.bulletFired = bulletFired;
        this.prediction = prediction;
        this.battleId = battleId;
        this.roundId = roundId;
    }

    public int getBulletFired() { return this.bulletFired; }
    public TargetingPrediction getPrediction() { return this.prediction; }
    public boolean getDidHit() { return this.didHit; }


    public boolean doesBulletMatch(Bullet bulletFired, UUID battleId, UUID roundId) {
        return bulletFired.hashCode() == this.bulletFired &&
               battleId == this.battleId &&
               roundId == this.roundId;

    }

    public void setHit() { this.didHit = true; }
}
