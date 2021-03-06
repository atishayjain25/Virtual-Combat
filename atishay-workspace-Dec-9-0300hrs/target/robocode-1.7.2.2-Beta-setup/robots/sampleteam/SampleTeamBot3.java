package sampleteam;

import java.awt.Color;

import robocode.*;
import robocode.robotinterfaces.IObjectEvents;
import robocode.robotinterfaces.IObjectRobot;


public class SampleTeamBot3 extends TeamRobot implements IObjectEvents, IObjectRobot{
	boolean isrstopper;
	public void run()
    {
		String[] teammates = getTeammates();
	       if (teammates != null) {
	           for (String member : teammates) {
	               out.println(member);
	           }
	       }

        while (true)
        {
        	setBodyColor(new Color(9,82,22));
        	setGunColor(new Color(212,153,17));
        	setRadarColor(new Color(232,229,49));
        	setScanColor(new Color(24,240,99));
        	setBulletColor(new Color(227,104,23));
        	setAdjustGunForRobotTurn(true);
        	setTurnGunRight(10);
        	setAhead(20);
        	turnGunRight(10);
        }
    }
 
    public void smartFire(double dce)
    {
    	if(getGunHeat()==0)
    	{	
	    	if(dce<150)
	    		fire(3);
	    	else if(dce<400)
	    		fire(2);
	    	else
	    		fire(1);
    	}
    }
    public void onScannedRobot(ScannedRobotEvent e) {
    	if(isTeammate(e.getName()))
    		return;
    	turnGunRight(e.getBearing()+getHeading()-getRadarHeading());
    	smartFire(e.getDistance());
    	if(e.getBearing()>=-90 &&e.getBearing()<90)
    		setAhead(e.getDistance()/4);
    	else
    		setBack(e.getDistance()/4);
    	scan();
     }
    public void onHitObstacle(HitObstacleEvent e)
    {
    	turnLeft(90);
    	setAhead(40);
    }
    public void onHitWall(HitWallEvent e)
    {
    	out.println("Wall bearing: "+e.getBearing());
    	turnRight(90);
    	setAhead(40);
    }
    public void onHitObject(HitObjectEvent e)
    {
    	if(e.getType().equals("flag"))
    		stop();
    	if(isrstopper)
    	{
    		turnRight(90);
    		ahead(60);
    	}
    }
    public void onScannedObject(ScannedObjectEvent e) {
		//if (e.getObjectType().equals("flag")) {
			//turnRight(e.getBearing()+getHeading()-getRadarHeading());
			//ahead(e.getDistance());
		//}
		isrstopper=e.isRobotStopper();
	}
    public IObjectEvents getObjectEventListener() {
		return this;
	}


}
