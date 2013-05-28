package mtp;

public class Point {
    private int x;
    private int y;
    private char name;

    public Point(int x, int y) {
        init(x, y, (char)0);
    }

    public Point(int x, int y, char name) {
        init(x, y, name);
    }

    private void init(int x, int y, char name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public char getName() { return name; }
}
