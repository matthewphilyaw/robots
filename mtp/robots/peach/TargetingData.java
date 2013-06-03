package mtp.robots.peach;

import java.awt.*;
import java.util.*;
import java.util.List;

public class TargetingData {
    final List<TargetingPrediction> predictions = new ArrayList<TargetingPrediction>();
    final long timeCreated;

    public TargetingData(long timeCreated) {
       this.timeCreated = timeCreated;
    }
    public List<TargetingPrediction> getPredictions() { return this.predictions; }
    public long getTimeCreated() { return this.timeCreated; }
    public void renderPredictions(Graphics2D g) {
        g.setColor(new Color(0, 255, 0, 255));
        for (TargetingPrediction t : this.predictions) {
            g.drawLine(t.getAssassin().getX(), t.getAssassin().getY(), t.getTarget().getX(), t.getTarget().getY());
            g.fillOval(t.getAssassin().getX() - 2, t.getAssassin().getY() - 2, 4, 4);
            g.fillOval(t.getTarget().getX() - 5, t.getTarget().getY() - 5, 10, 10);
        }
    }
}
