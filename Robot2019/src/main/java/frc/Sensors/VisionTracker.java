package frc.Sensors;
import frc.DriveTrain.*;
import frc.Network.*;

public class VisionTracker {
	
	private DriveTrain driveTrain;
	
	public VisionTracker(DriveTrain driveTrain) {
		this.driveTrain = driveTrain;
	}
	
	public void moveByVision() {
		String direction = RobotNetwork.dirEntry.getString("bad");
		if(direction.equalsIgnoreCase("r"))
	    {
	      System.out.println("r");
	      driveTrain.drive(0, -0.3);
	    }
	    else if(direction.equalsIgnoreCase("l"))
	    {
	      System.out.println("l");
	      driveTrain.drive(-0.3, 0);
	    }
	    else if(direction.equalsIgnoreCase("f"))
	    {
	      System.out.println("f");
	      driveTrain.drive(-0.3, -0.3);
	    } else {
	    	driveTrain.stop();
	    }
	}
	
}