package mtp.robots.peach;

import robocode.util.Utils;

public class TargetingPrediction {
    private final Point target;
    private final Point assassin;
    private final Point relTarget;
    private final double angle;
    private final double velocity;
    private final double distance;
    private final int time;

    public TargetingPrediction(Point target, Point assassin, int time) {
        relTarget = new Point(target.getX() - assassin.getX(), target.getY() - assassin.getY());
        distance = Math.sqrt(Math.pow(Math.abs(relTarget.getX()), 2) + Math.pow(Math.abs(relTarget.getY()), 2));
        this.target = target;
        this.assassin = assassin;
        this.time = time;
        angle = Utils.normalAbsoluteAngle((Math.PI / 2) - Math.atan2(relTarget.getY(), relTarget.getX()));
        velocity = Math.sqrt(Math.pow(relTarget.getX() / (double)this.time, 2) + Math.pow(relTarget.getY() / this.time,2));
    }


    public Point getTarget() { return target; }
    public Point getAssassin() { return assassin; }
    public double getAngle() { return angle; }
    public double getVelocity() { return this.velocity; }
    public double getDistance() { return this.distance; }
    public int getTime() { return this.time; }
}
