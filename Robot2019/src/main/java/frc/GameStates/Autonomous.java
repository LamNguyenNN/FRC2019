package frc.GameStates;

import edu.wpi.first.wpilibj.*;
import frc.Controller.*;
import frc.DriveTrain.*;
import frc.Mechanisms.*;
import frc.Network.*;
import frc.Sensors.*;

public class Autonomous{
	
	private Astronaut driver1, driver2;
	private DriveTrain driveTrain;
    private LinearSlider slider;
    private IntakeShooter intakeShooter;
	private VisionTracker visionTracker;
	private UltrasonicSensor ultraFront, ultraSlide;
	private Platypus platypus;
	
	public Autonomous (Astronaut driver1, Astronaut driver2, DriveTrain driveTrain, LinearSlider slider, Platypus platypus, IntakeShooter intakeShooter, VisionTracker visionTracker, UltrasonicSensor ultraFront, UltrasonicSensor ultraSlide) {
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
	
	public void move() {
		visionTracker.moveByVision();
	}
	
	public void deliverHatch() {
		if (ultraFront.getDist() > 6) {
			visionTracker.moveByVision();
		} else {
			if (ultraSlide.getDist() > 18 * slider.getSlideLevel()) {
				slider.stop();
				if(ultraFront.getDist() <= 4) {
					driveTrain.stop();
		          platypus.closePlatypus();
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
		if(ultraFront.getDist() <= 6) {
			driveTrain.drive(-0.15, -0.15);
		} else if (ultraSlide.getDist() > 10) {
			driveTrain.stop();
			slider.slide(-0.25);
		} else {
			driveTrain.drive(0, 0);
			slider.slide(0);
		}
		platypus.openPlatypus();
	}
	
	
}