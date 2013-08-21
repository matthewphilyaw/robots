package mtp.robots.milkshake.bot;

import mtp.robots.milkshake.analytics.location.*;
import mtp.robots.milkshake.analytics.targeting.BulletInfo;
import mtp.robots.milkshake.analytics.targeting.TargetingData;
import mtp.robots.milkshake.analytics.targeting.TargetingPrediction;
import mtp.robots.milkshake.util.*;
import robocode.*;

import java.awt.Graphics2D;
import java.util.*;
import java.util.List;
import java.util.UUID;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedWriter;

public class MilkShake extends AdvancedRobot {
    static final int LAST_HEADING_SIZE = 1;
    static final UUID battleId = UUID.randomUUID();

    static final List<BulletInfo> bulletsFired = new ArrayList<BulletInfo>();
    static final Object bulletsFiredLock = new Object();
    static final Object LELock = new Object();
    static final LocationEngine le = new LocationManager(LocationEngineType.TRAILPREDICTION).getLocationEngine();

    final UUID roundId = UUID.randomUUID();
    final List<BulletInfo> bulletsThatHit = new ArrayList<BulletInfo>();
    final RingBuffer<LocationVertex> lastVertices = new RingBuffer<LocationVertex>(LAST_HEADING_SIZE);

    TargetingData td;
    boolean fire = false;
    TargetingPrediction solutionToFire;

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
        synchronized (LELock) {
            le.updateEngine(this, e);
        }

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

            List<TargetingPrediction> solutions = td.getTargetingSolutions(1);
            if (solutions.size() > 0) {
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
                bi.setHit();
                bulletsThatHit.add(bi);
                solutionToFire = null;
                break;
            }
        }
    }

    public void onPaint(Graphics2D g) {
        if (td == null) return;
        if (this.getTime() - td.getTimeCreated() > 5) { return; }
        td.renderPredictions(g);
    }

    @Override
    public void onBattleEnded(BattleEndedEvent event) {
        try {
            File f = new File("graph.dot");
            FileWriter fw = new FileWriter(f.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(le.print());
            bw.flush();
            bw.close();
        }
        catch (Exception e) { System.out.println(e.getMessage()); }
    }
}
