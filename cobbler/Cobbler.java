package mtp;
import robocode.*;

public class Cobbler extends Robot
{
	public void run() {
		while (true) {
			this.turnGunRight(360);
			this.ahead(100);
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		// should never be less than zero, but it's an old habbit.
		if (this.getGunHeat() <= 0)
			this.fire(1);
	}

	public void onHitWall(HitWallEvent e) {
		// square up to the wall
		this.turnRight(e.getBearing());
		// turn the fuck around with mad tank face
		this.turnRight(180);
		this.turnGunRight(360);
		this.ahead(50);
		this.turnRight(90);
	}

	public void onHitRobot(HitRobotEvent e) {
		if (e.getBearing() > -90 && e.getBearing() <= 90) {
			this.back(100);
			this.turnRight(-90);
		}
	}
}
								