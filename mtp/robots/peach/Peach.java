package mtp.robots.peach;
import robocode.*;
import java.util.*;
import java.awt.Graphics2D;

public class Peach extends AdvancedRobot {
    private FiringSolution fs = new FiringSolution();
    private Point p = new Point(0, 0);
    private long time = 0;

    public void run() {
        if (time == 0) time = this.getTime();

        while (true) {
            waitFor(new RadarTurnCompleteCondition(this));
            setTurnRadarRight(360);
            setAhead(100);
        }
    }

    // abstract further.
    // Create interface for target
    // use anonymous inner classes to generate one based off event
    public void onScannedRobot(ScannedRobotEvent e) {
        try {
            final double angle = Math.toRadians((this.getHeading() + e.getBearing()) % 360);
            final Point target =  new Point((int)(getX() + Math.sin(angle) * e.getDistance()),
                                      (int)(getY() + Math.cos(angle) * e.getDistance()));
            final Point assassin = new Point((int)getX(),
                                       (int)getY());

            final int tv = (int)e.getVelocity();
            final int av = (int)this.getVelocity();

            TargetingData td = new TargetingData();
            List<TargetingPrediction> pd = td.getPredictions();

            pd.add(new TargetingPrediction(target,
                                           assassin,
                                           0));

            final double ta = Math.toRadians((e.getHeading() % 360));
            final double aa = Math.toRadians((this.getHeading() % 360));
            for (int t = 0; t < 10; t++) {
                final int th = tv * (t + 1);
                final int ah = av * (t + 1);

                final Point targetP = new Point ((int)(Math.sin(ta) * th) + target.getX(),
                                           (int)(Math.cos(ta) * th) + target.getY());
                final Point assassinP = new Point ((int)(Math.sin(aa) * ah) + assassin.getX(),
                                             (int)(Math.cos(aa) * ah) + assassin.getY());

                pd.add(new TargetingPrediction(targetP, assassinP, t));
                Point[] set = new Point[3];
                set[0] = new Point(targetP.getX(), targetP.getY(), 'b');
                set[1] = new Point(assassinP.getX(),assassinP.getY());
                set[2] = new Point(assassinP.getX(), targetP.getY());

                Triangle tr = new Triangle(set);
                fs.addTriangle(tr);
            }
        }
        catch (Exception ex) {
            out.println(ex.getMessage());
        }
    }

    public void onPaint(Graphics2D g) {
        fs.drawSolutions(g);
    }
}
