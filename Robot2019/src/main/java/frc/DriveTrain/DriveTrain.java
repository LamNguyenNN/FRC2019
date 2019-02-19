package frc.DriveTrain;
import edu.wpi.first.wpilibj.*;

public class DriveTrain {
	
	private Victor driveMotorLeft1, driveMotorLeft2, driveMotorRight1, driveMotorRight2;
	private double maxSpeed;
	
	public DriveTrain(int leftVictor1, int leftVictor2, int rightVictor1, int rightVictor2, double maxSpeed) {
		this.driveMotorLeft1 = new Victor(leftVictor1);
		this.driveMotorLeft2 = new Victor(leftVictor2);
		this.driveMotorRight1 = new Victor(rightVictor1);
		this.driveMotorRight2 = new Victor(rightVictor2);
		this.maxSpeed = maxSpeed;
	}
	
	public void drive(double leftMotorMove, double rightMotorMove) {
		driveMotorLeft1.set(-1*leftMotorMove * maxSpeed);
		driveMotorLeft2.set(-1*leftMotorMove * maxSpeed);
		driveMotorRight1.set(rightMotorMove * maxSpeed);
		driveMotorRight2.set(rightMotorMove * maxSpeed);
	//	System.out.println(driveMotorLeft1.getSpeed());
	//	System.out.println(driveMotorRight1.getSpeed());
	}
	
	public void stop() {
		driveMotorLeft1.set(0);
		driveMotorLeft2.set(0);
		driveMotorRight1.set(0);
		driveMotorRight2.set(0);
	}
	
}