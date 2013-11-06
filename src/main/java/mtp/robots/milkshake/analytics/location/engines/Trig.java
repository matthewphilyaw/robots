package mtp.robots.milkshake.analytics.location.engines;

import mtp.robots.milkshake.analytics.location.*;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

import java.util.List;

public class Trig implements Engine {
    AdvancedRobot currentHost;
    ScannedRobotEvent currentTarget;

    @Override
    public void updateEngine(AdvancedRobot host, ScannedRobotEvent target) {
        this.currentHost = host;
        this.currentTarget = target;
    }

    @Override
    public List<Prediction> getNextNPredictions(Integer n) {
        return null;
    }

    @Override
    public List<Prediction> getPredictionsForNTicks(Integer ticks) {
        /*// bearing angle relative to grid.
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

            final Point targetP = new Point((int)(Math.sin(e.getHeadingRadians()) * th) + targetPos.getX(),
                    (int)(Math.cos(e.getHeadingRadians()) * th) + targetPos.getY());
            final Point assassinP = new Point((int)(Math.sin(self.getHeadingRadians()) * ah) + assassinPos.getX(),
                    (int)(Math.cos(self.getHeadingRadians()) * ah) + assassinPos.getY());

            TargetingPrediction tp = new TargetingPrediction(targetP, assassinP, t);
            tp.setNormalizedToCannonAngle(TargetingData.normalizeAngleToCannon(self, tp.getAngle()));
            pd.add(tp);
        }

        return td;*/

        return null;
    }

    @Override
    public Double getSuccessRate() {
        return null;
    }

    @Override
    public String print() {
        return null;
    }
}
