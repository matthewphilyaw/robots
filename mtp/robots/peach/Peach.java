package mtp.robots.peach;
import robocode.*;
import java.awt.Graphics2D;
import java.util.List;

public class Peach extends AdvancedRobot {
    private TargetingData td;
    private boolean fire = false;

    public void run() {
        this.setAdjustGunForRobotTurn(true);
        this.setAdjustRadarForGunTurn(true);

        while (true) {
            waitFor(new RadarTurnCompleteCondition(this));
            setTurnRadarRight(360);
            setAhead(100);
        }
    }


    public void onHitWall(HitWallEvent e) {
        this.turnRight(e.getBearing());
        this.turnRight(180);
        this.ahead(50);
        this.turnRight(90);
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        if (this.getGunTurnRemaining() > 0)
            return;

        if (this.fire && !(this.getGunHeat() > 0)) {
            this.setFire(Rules.MAX_BULLET_POWER);
            this.fire = false;
        }

        try {
            td = TargetingData.GenerateTargetingData(this, e, 10);
            //out.println(td);

            List<Double> solutions = td.getTargetingSolutions(5);
            if (solutions.size() > 0) {
                this.setTurnGunRightRadians(solutions.get(solutions.size() - 1));
                this.fire = true;
            }
        }
        catch (Exception ex) {
            out.println(ex.getMessage());
        }
    }

    public void onPaint(Graphics2D g) {
        if (td == null) return;
        if (this.getTime() - td.getTimeCreated() > 5) { return; }
        td.renderPredictions(g);
    }
}
