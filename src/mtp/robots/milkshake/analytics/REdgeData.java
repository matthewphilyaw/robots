package mtp.robots.milkshake.analytics;

public class REdgeData {
    int visited = -1;
    public int getVisitedCount() { return this.visited; }
    public void incrementVisitedCount() { this.visited++; }

    @Override
    public String toString() {
        return Integer.valueOf(visited).toString();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
