package mtp.robots.peach;
import robocode.*;
import java.awt.Graphics2D;

public class Peach extends AdvancedRobot {
    private TargetingData td;

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
        try {
            td =  TargetingData.GenerateTargetingData(this, e, 5);
            out.println(td);
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
