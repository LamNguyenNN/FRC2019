package frc.GameStates;

import edu.wpi.first.wpilibj.*;
import frc.Controller.*;
import frc.DriveTrain.*;
import frc.Mechanisms.*;
import frc.Network.*;
import frc.Sensors.*;
import frc.robot.*;

public class Teleop{
	
	private Astronaut driver1, driver2;
	private DriveTrain driveTrain;
    private LinearSlider slider;
    private IntakeShooter intakeShooter;
	private VisionTracker visionTracker;
	private UltrasonicHandler ultraFront, ultraSlide;
	private Platypus platypus;
	private boolean drivable;
	
	public Teleop (Astronaut driver1, Astronaut driver2, DriveTrain driveTrain, LinearSlider slider, Platypus platypus, IntakeShooter intakeShooter, VisionTracker visionTracker, UltrasonicHandler ultraFront, UltrasonicHandler ultraSlide) {
		this.driver1 = driver1;
		this.driver2 = driver2;
		this.driveTrain = driveTrain;
		this.slider = slider;
		this.platypus = platypus;
		this.intakeShooter = intakeShooter;
		this.visionTracker = visionTracker;
		this.ultraFront = ultraFront;
		this.ultraSlide = ultraSlide;
	}
	
	public void drive() {
		double leftMotorMove = driver1.getLeftYAxis();
		double rightMotorMove = driver1.getRightYAxis();
		driveTrain.drive(leftMotorMove, rightMotorMove);
	}
	
	public void deliverHatch() {
		if (ultraFront.dist > 6) {
			visionTracker.moveByVision();
			System.out.println("too far");
		} else {
			System.out.println("in front");
			System.out.println(ultraFront.dist);
			if (ultraSlide.dist > 18 * slider.getSlideLevel()) {
				slider.stop();
				if(ultraFront.dist <= 4) {
					driveTrain.stop();
					platypus.closePlatypus();
					System.out.println("stopping and platting");
		        	resetBot();
		        } else {
		        	driveTrain.drive(0.15, 0.15);
		        }
			} else {
				slider.slide(0.15);
			}
		}
	}
	
	public void resetBot() {
		if(ultraFront.dist <= 6) {
			driveTrain.drive(0.15, 0.15);
		} else if (ultraSlide.dist > 10) {
			driveTrain.stop();
			slider.slide(-0.25);
		} else {
			driveTrain.stop();
			slider.slide(0);
			driver1.buttonXToggle = false;
		}
		platypus.openPlatypus();
	}
	
	public void setDrivable(boolean drivable) {
		this.drivable = drivable;
	}

	public boolean isDrivable(){
		return drivable;
	}
	
}