package CTFbots;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import robocode.HitByBulletEvent;
import robocode.HitObjectEvent;
import robocode.HitObstacleEvent;
import robocode.HitWallEvent;
import robocode.ScannedObjectEvent;
import robocode.ScannedRobotEvent;
import net.sf.robocode.extensionsApi.CaptureTheFlagApi;

public class FlagSeeker extends CaptureTheFlagApi{
	
	/**
	 * Note that CaptureTheFlagApi inherits AdvancedRobot, so users can directly use functions of AdvancedRobot.
	 */
	
	List<String> myteam = null;

	Point2D enemyFlag;
	Rectangle2D homeBase;
	
	boolean getEnemyFlag;

	Point2D destination;
	int skipGoTo = 20;
	
	public void run() {
		registerMe(); 
		
		//write your logic here
		
		while (true) {
			myteam = getTeammates();
			homeBase = getOwnBase();
			enemyFlag = getEnemyFlag();
			System.out.println("UpdateBattlefieldState done");
			
				if (enemyFlag.distance(getX(), getY()) < 50)
				{
					getEnemyFlag = false;
				}
				if (getEnemyFlag)
				{
					destination = enemyFlag;
				}
				else 
				{
					destination = new Point2D.Double(homeBase.getCenterX(), homeBase.getCenterY());
				}
				
//				if (skipGoTo > 10)
//				{
					goTo(destination);
//				}
//				else
//				{
//					skipGoTo++;
//				}
				execute();
		}
	}
	
	public void goTo(Point2D point)	{
		double dx = point.getX() - getX();
		double dy = point.getY() - getY();
		int quad = 90;
		if (dx < 0 )
		{
			quad = -90;
		}
		
		double absBearing = quad - Math.atan(dy/dx) * 180 / Math.PI;
		double bearing = absBearing - getHeading();
		while (bearing > 180)
		{
			bearing -= 360;
		}
		while (bearing < -180)
		{
			bearing += 360;
		}
		setTurnRight(bearing);
		setAhead(100);
	}
	
	public void onHitObject(HitObjectEvent event) {
		if (event.getType().equals("flag") && enemyFlag.distance(getX(), getY()) < 50)
		{
			getEnemyFlag = false;
			skipGoTo = 0;
		}
		else if (event.getType().equals("base") && homeBase.contains(new Point2D.Double(getX(), getY())))
		{
			getEnemyFlag = true;
			skipGoTo = 0;
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		fire(1);
	}
	
	public void onHitByBullet(HitByBulletEvent e) {
		// Turn to confuse the other robot
		setTurnRight(5);
	}
	
	public void onHitObstacle(HitObstacleEvent e) {
		
		back(20);
		turnRight(30);
		ahead(30);
	}
	
	public void onHitWall(HitWallEvent e) {
		// Move away from the wal
		back(20);
		turnRight(30);
		ahead(30);
	}

	public void onScannedObject(ScannedObjectEvent e) {
		if (e.getObjectType().equals("flag")) {
			e.getBearing();
		}
	}
	

}