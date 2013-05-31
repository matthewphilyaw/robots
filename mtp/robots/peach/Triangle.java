package mtp.robots.peach;

public class Triangle {
    private final Point[] points;

    public Triangle(Point[] p) throws Exception {
        if (p.length != 3) throw new Exception("Need exactly three points");
        points = new Point[3];
        for (int i = 0; i < 3; i++) points[i] = p[i];
    }

    public Point[] getPoints() { return points; }
}
