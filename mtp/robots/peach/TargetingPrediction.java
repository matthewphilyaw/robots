package mtp.robots.peach;

public class TargetingPrediction {
    private final Point target;
    private final Point assassin;
    private final int time;

    public TargetingPrediction(Point target, Point assassin, int time) {
        this.target = target;
        this.assassin = assassin;
        this.time = time;
    }

    public Point getTarget() { return this.target; }
    public Point getAssassin() { return this.assassin; }
    public int getTime() { return this.time; }
}
