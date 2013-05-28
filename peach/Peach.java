package mtp;
import robocode.*;
import java.awt.Color;
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
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        try {
            double angle = Math.toRadians((this.getHeading() + e.getBearing()) % 360);
            int x = (int)(getX() + Math.sin(angle) * e.getDistance());
            int y = (int)(getY() + Math.cos(angle) * e.getDistance());

            int v = (int)e.getVelocity();

            long delta = this.getTime() - time;
            out.format("ticks past: %d\n", delta);
            time = this.getTime();

            if (v <= 0)
                return;

            double a = Math.toRadians((e.getHeading() % 360));
            for (int t = 1; t <= 10; t++) {
                int h = v * t;
                int nX = (int)(Math.sin(a) * h);
                int nY = (int)(Math.cos(a) * h);

                out.format("angle = %d, v = %d, h = %d, nX = %d, nY = %d, x = %d, y = %d, xA = %f, yA = %f\n", (int)e.getHeading() % 360, v, h, nX, nY, x, y, Math.sin(a), Math.cos(a));
                nX += x;
                nY += y;

                p = new Point(x, y);

                Point[] set = new Point[3];
                set[0] = new Point(nX, nY, 'b');
                set[1] = new Point((int)getX(), (int)getY());
                set[2] = new Point((int)getX(), nY);

                Triangle tr = new Triangle(set);

                fs.addTriangle(tr);
            }

            run = false;
        }
        catch (Exception ex) {
            //don't care
        }
    }

    public void onPaint(Graphics2D g) {
        fs.drawSolutions(g);
    }
}
