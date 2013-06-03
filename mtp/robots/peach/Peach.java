package mtp.robots.peach;
import robocode.*;
import java.util.*;
import java.awt.Graphics2D;

public class Peach extends AdvancedRobot {
    private TargetingData td;

    public void run() {
        while (true) {
            waitFor(new RadarTurnCompleteCondition(this));
            setTurnRadarRight(360);
            setAhead(100);
        }
    }


    public void onHitWall(HitWallEvent e) {
        this.turnRight(e.getBearing());
        this.turnRight(180);
        this.turnGunRight(360);
        this.ahead(50);
        this.turnRight(90);
    }

    // abstract further.
    // Create interface for target
    // use anonymous inner classes to generate one based off event
    public void onScannedRobot(ScannedRobotEvent e) {
        try {
            final double angle = Math.toRadians((this.getHeading() + e.getBearing()) % 360);
            final Point target =  new Point((int)(getX() + Math.sin(angle) * e.getDistance()),
                                            (int)(getY() + Math.cos(angle) * e.getDistance()));
            final Point assassin = new Point((int)getX(), (int)getY());

            final int tv = (int)e.getVelocity();
            final int av = (int)this.getVelocity();

            td = new TargetingData(this.getTime());
            List<TargetingPrediction> pd = td.getPredictions();

            //pd.add(new TargetingPrediction(target, assassin, 0));

            final double ta = Math.toRadians((e.getHeading() % 360));
            final double aa = Math.toRadians((this.getHeading() % 360));

            for (int t = 1; t < 25; t++) {
                final int th = tv * (t);
                final int ah = av * (t);

                final Point targetP = new Point ((int)(Math.sin(ta) * th) + target.getX(),
                                           (int)(Math.cos(ta) * th) + target.getY());
                final Point assassinP = new Point ((int)(Math.sin(aa) * ah) + assassin.getX(),
                                             (int)(Math.cos(aa) * ah) + assassin.getY());

                TargetingPrediction tp = new TargetingPrediction(targetP, assassinP, t);
                out.println("----------------------");
                out.format("Tick: %d\nAngle needed: %f\nVelocity needed: %f\nDistance to target: %f\n",
                            tp.getTime(),
                            tp.getAngle(),
                            tp.getVelocity(),
                            tp.getDistance());
                pd.add(tp);

            }
            out.println("=============================");
        }
        catch (Exception ex) {
            out.println(ex.getMessage());
        }
    }

    public void onPaint(Graphics2D g) {
        if (td == null) return;
        if (this.getTime() - td.getTimeCreated() > 5) { return; }
        td.renderPredictions(g);
    }
}
