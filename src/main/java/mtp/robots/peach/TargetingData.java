package mtp.robots.peach;

import robocode.util.Utils;
import robocode.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class TargetingData {
    final List<TargetingPrediction> predictions = new ArrayList<TargetingPrediction>();
    final long timeCreated;
    final AdvancedRobot self;

    private TargetingData(long timeCreated, AdvancedRobot self) {
        this.timeCreated = timeCreated;
        this.self = self;
    }

    public static TargetingData GenerateTargetingData(AdvancedRobot self, ScannedRobotEvent e, int numPredictions) {
        // bearing angle relative to grid.
        final double angle = self.getHeadingRadians() + e.getBearingRadians();

        // targets current point on the grid
        final int targetVel = (int)e.getVelocity();
        final Point targetPos =  new Point((int)(self.getX() + Math.sin(angle) * e.getDistance()),
                                           (int)(self.getY() + Math.cos(angle) * e.getDistance()));

        // my current point on the grid
        final int assassinVel = (int)self.getVelocity();
        final Point assassinPos = new Point((int)self.getX(), (int)self.getY());

        TargetingData td = new TargetingData(self.getTime(), self);
        List<TargetingPrediction> pd = td.getPredictions();

        for (int t = 1; t <= numPredictions; t++) {
            final int th = targetVel * (t);
            final int ah = assassinVel * (t);

            final Point targetP = new Point ((int)(Math.sin(e.getHeadingRadians()) * th) + targetPos.getX(),
                                             (int)(Math.cos(e.getHeadingRadians()) * th) + targetPos.getY());
            final Point assassinP = new Point ((int)(Math.sin(self.getHeadingRadians()) * ah) + assassinPos.getX(),
                                               (int)(Math.cos(self.getHeadingRadians()) * ah) + assassinPos.getY());

            TargetingPrediction tp = new TargetingPrediction(targetP, assassinP, t);
            tp.setNormalizedToCannonAngle(TargetingData.normalizeAngleToCannon(self, tp.getAngle()));
            pd.add(tp);
        }

        return td;
    }

    public List<TargetingPrediction> getTargetingSolutions(int minTicks) {
        List<TargetingPrediction> solutions = new ArrayList<TargetingPrediction>();
        System.out.println("---");
        for (TargetingPrediction p : this.predictions) {
            int adjustedTicks = minTicks;
            double ticksToTurn = Math.abs(Math.toDegrees(p.getNormalizedToCannonAngle())) / Rules.GUN_TURN_RATE;

            // idea being that if ticksToTurn is not a whole number,
            // it will take one extra tick to finish rotating
            if (ticksToTurn % adjustedTicks > 0)
                adjustedTicks++;

            if (ticksToTurn > adjustedTicks)
                continue;

            System.out.println("Solution picked: " + p.getTime() + "\nTicks to turn: " + ticksToTurn + "\nAngle: " + Math.toDegrees(p.getNormalizedToCannonAngle()));
            solutions.add(p);
        }
        System.out.println("---");
        return solutions;
    }

    public List<TargetingPrediction> getPredictions() { return this.predictions; }

    public long getTimeCreated() { return this.timeCreated; }

    public void renderPredictions(Graphics2D g) {
        g.setColor(new Color(0, 14, 255, 255));
        for (TargetingPrediction t : this.predictions) {
            g.drawLine(t.getAssassin().getX(), t.getAssassin().getY(), t.getTarget().getX(), t.getTarget().getY());
            g.fillOval(t.getAssassin().getX() - 2, t.getAssassin().getY() - 2, 4, 4);
            g.fillOval(t.getTarget().getX() - 5, t.getTarget().getY() - 5, 10, 10);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("== == == ==\n");
        for (TargetingPrediction t : this.predictions)
        {
            sb.append("-- -- -- --\n");
            sb.append(String.format("Tick: %d\nAngle needed: %f\nVelocity needed: %f\nDistance to target: %f\nTicks to rotate cannon: %f\n",
                    t.getTime(),
                    Math.toDegrees(t.getNormalizedToCannonAngle()),
                    t.getVelocity(),
                    t.getDistance(),
                    t.getNormalizedToCannonAngle() / Rules.GUN_TURN_RATE_RADIANS));
        }
        sb.append("== == == ==\n");

        return sb.toString();
    }

    private static double normalizeAngleToCannon(AdvancedRobot self, double angle) {
        final double gunAngle = self.getGunHeadingRadians();

        if (Utils.isNear(gunAngle, angle))
            return 0.0;

        if (gunAngle > angle)
            return -(gunAngle - angle);

        return angle - gunAngle;
    }
}
