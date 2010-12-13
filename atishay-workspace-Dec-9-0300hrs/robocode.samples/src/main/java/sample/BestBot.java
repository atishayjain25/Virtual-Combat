package sample;
import java.awt.Color;

import robocode.*;
import robocode.robotinterfaces.IObjectEvents;
import robocode.robotinterfaces.IObjectRobot;
public class BestBot extends Robot implements IObjectEvents, IObjectRobot
{
	boolean isrstopper;
	int time=0;
    public void run()
    {
        while (true)
        {
        	setBodyColor(new Color(205,100,120));
        	setGunColor(new Color(100,100,200));
        	setRadarColor(new Color(200,200,200));
        	setScanColor(new Color(50,45,120));
        	setAdjustGunForRobotTurn(true);
        	//if(!botlocked)
        	{
	           // ahead(40);
	            turnGunRight(20);
	            time++;
	            if(time>20)
	            {	
	            	ahead(25);
	            	time=0;
	            }
	                   	}
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
    	time=0;
    	turnRight(e.getBearing()+getHeading()-getRadarHeading());
    	smartFire(e.getDistance());
    	if(e.getBearing()>=-90 &&e.getBearing()<90)
    		ahead(e.getDistance()/4);
    	else
    		back(e.getDistance()/4);
    	scan();
     }
    public void onHitObstacle(HitObstacleEvent e)
    {
    	turnLeft(90);
    	ahead(40);
    }
    public void onHitWall(HitWallEvent e)
    {
    	turnLeft(90);
    	ahead(40);
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
