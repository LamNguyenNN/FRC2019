package frc.Network

import edu.wpi.first.wpilibj.*;
import main.java.deploy.frc.Controller;
import main.java.deploy.frc.DriveTrain;
import main.java.deploy.frc.Mechanisms;
import main.java.deploy.frc.Network;
import main.java.deploy.frc.Sensors;

public class Teleop{
	
	private Astronaut driver1, driver2;
	private DriveTrain driveTrain;
	private LinearSlide slide;
	private VisionTracker visionTracker;
	private UltrasonicSensor ultrasonic
	
	public Teleop (Astronaut driver1, Astronaut driver2, DriveTrain driveTrain, LinearSlide linearSlide, VisionTracker visionTracker, UltrasonicSensor ultrasonic) {
		this.driver1 = driver1;
		this.driver2 = driver2;
		this.driveTrain = driveTrain;
		this.linearSlide = linearSlide;
		this.visionTracker = visionTracker;
		this.ultrasonic = ultrasonic;
	}
	
	public void drive() {
		double leftMotorMove = driver1.getLeftYAxis();
		double rightMotorMove = driver1.getRightYAxis();
		driveTrain.drive(leftMotorMove, rightMotorMove);
	}
	
	
	
}