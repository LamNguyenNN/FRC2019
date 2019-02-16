package frc.DriveTrain
import edu.wpi.first.wpilibj.*;

public class DriveTrain {
	
	private Victor driveMotorLeft1, driveMotorLeft2, driveMotorRight1, driveMotorRight2;
	
	public DriveTrain(int leftVictor1, int leftVictor2, int rightVictor1, int rightVictor2, double maxSpeed) {
		driveMoterLeft1 = new Victor(leftVictor1);
		driveMoterLeft2 = new Victor(leftVictor2);
		driveMoterRight1 = new Victor(rightVictor1);
		driveMoterRight2 = new Victor(rightVictor2);
	}
	
	public void drive(double leftMotorMove, double rightMotorMove) {
		driveMotorLeft1.set(leftMotorMove * maxSpeed);
		driveMotorLeft2.set(leftMotorMove * maxSpeed);
		driveMotorRight1.set(rightMotorMove * maxSpeed);
		driveMotorRight2.set(rightMotorMove * maxSpeed);
	}
	
}