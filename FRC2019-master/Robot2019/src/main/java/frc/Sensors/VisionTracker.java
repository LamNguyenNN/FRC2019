package frc.Sensors
import main.java.deploy.frc.DriveTrain;
import main.java.deploy.frc.Network;
import edu.wpi.first.wpilibj.*;

public class VisionTracker {
	
	private DriveTrain driveTrain;
	
	public VisionTracker(DriveTrain driveTrain) {
		this.driveTrain = driveTrain;
	}
	
	public void moveByVision() {
		String direction = RobotNetwork.dirEntry.getString("bad");
		if(direction.equalsIgnoreCase("l"))
	    {
	      System.out.println("l");
	      driveTrain.drive(0, 0.15);
	    }
	    else if(direction.equalsIgnoreCase("r"))
	    {
	      System.out.println("r");
	      driveTrain.drive(0.15, 0);
	    }
	    else if(direction.equalsIgnoreCase("f"))
	    {
	      System.out.println("f");
	      driveTrain.drive(0.15, 0.15);
	    } else {
	    	driveTrain.stop();
	    }
	}
	
}