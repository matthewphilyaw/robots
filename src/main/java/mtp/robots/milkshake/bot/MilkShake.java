package mtp.robots.milkshake.bot;

import mtp.robots.milkshake.analytics.location.*;
import mtp.robots.milkshake.analytics.targeting.BulletInfo;
import mtp.robots.milkshake.analytics.targeting.TargetingData;
import mtp.robots.milkshake.analytics.targeting.TargetingPrediction;
import mtp.robots.milkshake.util.Point;
import robocode.*;
import robocode.util.Utils;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.UUID;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedWriter;

public class MilkShake extends AdvancedRobot {
    static final UUID battleId = UUID.randomUUID();

    static final List<BulletInfo> bulletsFired = new ArrayList<BulletInfo>();
    static final Object bulletsFiredLock = new Object();
    static final Object LELock = new Object();
    static final Engine le = new Manager(EngineType.TRAIL).getLocationEngine();

    final UUID roundId = UUID.randomUUID();
    final List<BulletInfo> bulletsThatHit = new ArrayList<BulletInfo>();

    TargetingData td;
    boolean fire = false;
    TargetingPrediction solutionToFire;
    PredictionGroup pg = null;

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
            PredictionGroup pgroup;

            le.updateEngine(this, e);
            pgroup = le.getPredictionsForNTicks(100);

            if (this.getGunTurnRemaining() > 0)
                return;

            if (!this.fire)  {
                if (pgroup.getPredictions().size() > 0) {
                    pg = pgroup;

                    // use new system
                    this.setTurnGunRightRadians(TargetingData.normalizeAngleToCannon(this, this.getFiringAngle(this, pg)));
                    this.fire = true;
                    System.out.println("firing!");
                    return;
                }
                //else
                //    this.setTurnGunRightRadians(this.getHeadingRadians() + e.getBearingRadians());
            }

            // use old system.
            if (this.fire && !(this.getGunHeat() > 0)) {
                this.fire = false;
                this.setFire(Rules.MAX_BULLET_POWER);
            }
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

    public void onPaint2(Graphics2D g) {
        if (td == null) return;
        if (this.getTime() - td.getTimeCreated() > 5) { return; }
        td.renderPredictions(g);
    }

    public void onPaint(Graphics2D g) {
        synchronized (LELock) {
            if (pg == null) return;
            if (pg.getPredictions().size() < 1) return;

            List<Prediction> lp = pg.getPredictions();
            double firstAngle = this.getHeadingRadians() + pg.getRootEvent().getBearingRadians();
            Point fp = new Point(this.getX() + Math.sin(firstAngle) * pg.getRootEvent().getDistance(),
                                 this.getY() + Math.cos(firstAngle) * pg.getRootEvent().getDistance());
            g.setColor(new Color(120, 255, 0, 255));
            for (int i = 0; i < lp.size(); i++) {
                Prediction c = lp.get(i);
                Point cp = new Point(fp.getX() + Math.sin(c.getTargetBearingFromLastPrediction()) * c.getTargetDistanceFromLastPrediction(),
                        fp.getY() + Math.cos(c.getTargetBearingFromLastPrediction()) * c.getTargetDistanceFromLastPrediction());
                g.drawLine(fp.getX().intValue(), fp.getY().intValue(), cp.getX().intValue(), cp.getY().intValue());
                g.fillOval(cp.getX().intValue() - 2, cp.getY().intValue() - 2, 4, 4);
                g.fillOval(cp.getX().intValue() - 5, cp.getY().intValue() - 5, 10, 10);
                fp = cp;
            }
        }
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

    private Double getFiringAngle(AdvancedRobot host, PredictionGroup pg) {
        List<Prediction> lp = pg.getPredictions();
        double firstAngle = host.getHeadingRadians() + pg.getRootEvent().getBearingRadians();
        Point fp = new Point(host.getX() + Math.sin(firstAngle) * pg.getRootEvent().getDistance(),
                             host.getY() + Math.cos(firstAngle) * pg.getRootEvent().getDistance());
        System.out.println("drawing!");
        for (int i = 0; i < lp.size(); i++) {
            Prediction c = lp.get(i);
            Point cp = new Point(fp.getX() + Math.sin(c.getTargetBearingFromLastPrediction()) * c.getTargetDistanceFromLastPrediction(),
                                 fp.getY() + Math.cos(c.getTargetBearingFromLastPrediction()) * c.getTargetDistanceFromLastPrediction());
            fp = cp;
        }

        Point offset = new Point(fp.getX() - host.getX(), fp.getY() - host.getY());
        return Utils.normalAbsoluteAngle((Math.PI / 2) - Math.atan2(offset.getY(), offset.getX()));
    }
}
