package mtp.robots.milkshake.bot;

import mtp.robots.milkshake.analytics.*;
import mtp.robots.milkshake.targeting.*;
import robocode.*;
import java.awt.Graphics2D;
import java.util.*;
import java.util.List;
import java.util.UUID;

public class MilkShake extends AdvancedRobot {
    private static UUID battleId = UUID.randomUUID();
    private static List<BulletInfo> bulletsFired = new ArrayList<BulletInfo>();
    private static Object bulletsFiredLock = new Object();

    private UUID roundId = UUID.randomUUID();
    private TargetingData td;
    private boolean fire = false;
    private TargetingPrediction solutionToFire;
    private List<BulletInfo> bulletsThatHit = new ArrayList<BulletInfo>();

    public void run() {
        this.setAdjustGunForRobotTurn(true);
        this.setAdjustRadarForGunTurn(true);

        while (true) {
            waitFor(new RadarTurnCompleteCondition(this));
            setTurnRadarRight(360);
            setAhead(100);
        }
    }

    public void onHitWall(HitWallEvent e) {
        this.setTurnRight(e.getBearing());
        waitFor(new TurnCompleteCondition(this));
        this.setTurnRight(180);
        waitFor(new TurnCompleteCondition(this));
        this.setAhead(50);
        this.setTurnRight(90);
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        if (this.getGunTurnRemaining() > 0)
            return;

        if (this.fire && !(this.getGunHeat() > 0)) {
            this.fire = false;
            Bullet b = this.setFireBullet(Rules.MAX_BULLET_POWER);
            if (solutionToFire == null) { System.out.println("solution to fire is null"); return; }
            if (b == null) { System.out.println("bullet is null"); return; }
            synchronized (bulletsFiredLock) {
                bulletsFired.add(new BulletInfo(b.hashCode(),
                                 solutionToFire,
                                 MilkShake.battleId,
                                 this.roundId));
            }
        }

        try {
            td = TargetingData.GenerateTargetingData(this, e, 30);
            //out.println(td);

            List<TargetingPrediction> solutions = td.getTargetingSolutions(1);
            if (solutions.size() > 0) {
            /*int index = solutions.size() - 1;
                if (solutions.size() > 4)
                    index = solutions.size() - 4;
                this.solutionToFire = solutions.get(index);*/
                this.solutionToFire = solutions.get(0);
                this.setTurnGunRightRadians(solutions.get(0).getNormalizedToCannonAngle());
                this.fire = true;
            }
            else {
                this.setTurnGunRightRadians(this.getHeadingRadians() + e.getBearingRadians());
            }
        }
        catch (Exception ex) {
            out.println(ex.getMessage());
        }
    }

    public void onBulletHit(BulletHitEvent e) {
        synchronized (bulletsFiredLock) {
            for (BulletInfo bi : bulletsFired) {
                if (!bi.doesBulletMatch(e.getBullet(), MilkShake.battleId, this.roundId)) continue;
                bi.setHit(true);
                bulletsThatHit.add(bi);
                solutionToFire = null;
                break;
            }
        }
    }

    public void onRoundEnded(RoundEndedEvent event) {
        double tTicks = 0;
        double avgTicks = 0;

        for (BulletInfo bi : bulletsThatHit) {
            tTicks += bi.getPrediction().getTime();
            System.out.println(bi.getPrediction().getTime());
        }

        avgTicks = tTicks / bulletsThatHit.size();
        System.out.print(String.format("avg ticks for round: %f\ntotal tick: %f\ntotal hits: %d\ntotal bullets (per battle): %d\n",
                                       avgTicks,
                                       tTicks,
                                       bulletsThatHit.size(),
                                       bulletsFired.size()));
    }

    public void onPaint(Graphics2D g) {
        if (td == null) return;
        if (this.getTime() - td.getTimeCreated() > 5) { return; }
        td.renderPredictions(g);
    }
}
