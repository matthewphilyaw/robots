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
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.File;
import javax.json.*;

public class MilkShake extends AdvancedRobot {
    static final UUID battleId = UUID.randomUUID();

    static final List<BulletInfo> bulletsFired = new ArrayList<BulletInfo>();
    static final Object bulletsFiredLock = new Object();
    static final Object LELock = new Object();
    static final Engine le = new Manager(EngineType.TRAIL).getLocationEngine();
    static final JsonBuilderFactory jsFac = Json.createBuilderFactory(null);
    static final String serverAddress = "localhost";
    static Socket socket;
    static PrintWriter sOut;

    final UUID roundId = UUID.randomUUID();
    final List<BulletInfo> bulletsThatHit = new ArrayList<BulletInfo>();

    TargetingData td;
    boolean fire = false;
    TargetingPrediction solutionToFire;
    DrawPrediction dp = null;

    //used for debugging
    Long predictionStart = 0L;
    Long duration = 0L;

    public void run() {
        if (socket == null && this.getBattleNum() < 1)
        {
            try
            {
                out.println("setting it up");
                socket = new Socket(serverAddress, 8888);
                sOut = new PrintWriter(socket.getOutputStream(), true);
                out.println("connected.");
            }
            catch (Exception e)
            {
                out.println("failed");
            }
        }

        this.setAdjustGunForRobotTurn(true);
        this.setAdjustRadarForGunTurn(true);

        while (true) {
            waitFor(new RadarTurnCompleteCondition(this));
            setTurnRadarRight(360);
            //setAhead(100);
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
        if (e.getEnergy() < 1) 
        {
            out.println("enemy has no energy... ignore");
            return;
        }
        synchronized (LELock) {
            JsonObject obj = jsFac.createObjectBuilder()
                .add("self", jsFac.createObjectBuilder()
                        .add("heading", this.getHeadingRadians())
                        .add("loc", jsFac.createObjectBuilder()
                            .add("x", this.getX())
                            .add("y", this.getY())))
                .add("target", jsFac.createObjectBuilder()
                        .add("bearing", e.getBearingRadians())
                        .add("distance", e.getDistance())
                        .add("energy", e.getEnergy())
                        .add("heading", e.getHeadingRadians())
                        .add("name", e.getName())
                        .add("velocity", e.getVelocity()))
                .build();

            if (sOut != null) { 
                out.println("Sending!!!");
                sOut.println(obj.toString());
                sOut.flush();
            }

            PredictionGroup pgroup;

            le.updateEngine(this, e);

            if (this.getGunTurnRemaining() > 0)
                return;

            if (this.fire && !(this.getGunHeat() > 0)) {
                this.fire = false;
                this.setFire(Rules.MAX_BULLET_POWER);
                return;
            }

            if ((this.getTime() - predictionStart) <= duration) {
                System.out.println("waiting on duration to expire");
                return;
            }

            predictionStart = 0L;
            duration = 0L;
            dp = null;

            pgroup = le.getPredictionsForNTicks(60);

            if (!this.fire)  {
                if (pgroup.getPredictions().size() > 0) {
                    dp = new DrawPrediction(pgroup, new Point(this.getX(), this.getY()));

                    // use new system
                    predictionStart = this.getTime();
                    duration = pgroup.getActualTicks();
                    System.out.println("Actual number of ticks found: " + duration.toString());
                    this.setTurnGunRightRadians(TargetingData.normalizeAngleToCannon(this, this.getFiringAngle(this, pgroup)));
                    this.fire = true;
                    System.out.println("firing!");
                    return;
                }
                else {
                    this.setTurnGunRightRadians(TargetingData.normalizeAngleToCannon(this, this.getHeadingRadians() + e.getBearingRadians()));
                    return;
                }
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

    public void onPaint(Graphics2D g) {
        synchronized (LELock) {
            if (dp == null) return;

            PredictionGroup pg = dp.getPredictionGroup();
            if (pg == null) return;
            if (pg.getPredictions().size() < 1) return;

            List<Prediction> lp = pg.getPredictions();
            double firstAngle = this.getHeadingRadians() + pg.getRootEvent().getBearingRadians();
            Point fp = new Point(dp.getSelfPoint().getX() + Math.sin(firstAngle) * pg.getRootEvent().getDistance(),
                                 dp.getSelfPoint().getY() + Math.cos(firstAngle) * pg.getRootEvent().getDistance());
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
