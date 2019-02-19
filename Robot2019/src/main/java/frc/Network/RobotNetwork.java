package frc.Network;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.networktables.*;

public class RobotNetwork {
	public static NetworkTable table;
	public static NetworkTableEntry dirEntry, camMode;
	public static NetworkTableValue left, right, forward;
	
	public RobotNetwork() {
		this.initNetwork();
	}
	
	public static void initNetwork() {
		table = NetworkTableInstance.getDefault().getTable("SmartDashboard");
		dirEntry = table.getEntry("dir");
		left = table.getEntry("left").getValue();
		right = table.getEntry("right").getValue();
		forward = table.getEntry("forward").getValue();
		   
		table.getEntry("left").setValue(new String("l"));
		table.getEntry("right").setValue(new String("r"));
		table.getEntry("forward").setValue(new String("f"));
	}
	
}