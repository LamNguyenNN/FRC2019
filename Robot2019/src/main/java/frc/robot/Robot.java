/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Ultrasonic.Unit;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.networktables.*;

import frc.Controller.*;
import frc.DriveTrain.*;
import frc.Mechanisms.*;
import frc.Network.*;
import frc.Sensors.*;
import frc.GameStates.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   * 
   */

	Astronaut astro1, astro2;
	DriveTrain driveTrain;
	RobotNetwork robotNetwork;
	LinearSlider linearSlider;
	Platypus platypus;
	IntakeShooter intakeShooter;
	VisionTracker visionTracker;
	Autonomous auto;
	Teleop teleop;
	UltrasonicHandler ultraFront, ultraSlide;
  Thread ultraFrontThread, ultraSlideThread;
  double maxSpeed;


  @Override
  public void robotInit() {

    maxSpeed = 0.6;

    astro1 = new Astronaut(0);
  	astro2 = new Astronaut(1);
  	driveTrain = new DriveTrain(8,9,0,1,maxSpeed);
  	robotNetwork = new RobotNetwork();
  	linearSlider = new LinearSlider(6);
  	platypus = new Platypus(4);
  	intakeShooter = new IntakeShooter(7);
  	visionTracker = new VisionTracker(driveTrain);
  	//ultraFront = new UltrasonicSensor(new Ultrasonic(6,7));
    //ultraSlide = new UltrasonicSensor(new Ultrasonic(8, 9));
    ultraFront = new UltrasonicHandler(new Ultrasonic(6,7));
    ultraSlide = new UltrasonicHandler(new Ultrasonic(8,9));
  	ultraFrontThread = new Thread(ultraFront);
  	ultraSlideThread = new Thread(ultraSlide);
  	
  	teleop = new Teleop(astro1, astro2, driveTrain, linearSlider, platypus, intakeShooter, visionTracker, ultraFront, ultraSlide);
  //	auto = new Autonomous(astro1, astro2, driveTrain, linearSlider, platypus, intakeShooter, visionTracker, ultraFront, ultraSlide);
  	
  	
  	ultraFrontThread.start();
  	ultraSlideThread.start();
  }

  @Override
  public void disabledInit() {
    //super.disabledInit();
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {

    //auto.deliverHatch();
    System.out.println("Front: " + ultraFront.dist);
    System.out.println("Slide: " + ultraSlide.dist);

    auto.move();

    
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
  /*	if(astro1.isButtonXToggled()) {
      System.out.println("delivering");
  		teleop.deliverHatch();
    } else {
      System.out.println("drive");
      teleop.drive();
    }*/

    if(astro1.isButtonPressed("lb")) {
      platypus.set(0.5);
    } else if (astro1.isButtonPressed("rb")) {
      platypus.set(-0.5);
    } 
  	else {
      platypus.set(0);
    }

    if(astro1.isButtonPressed("y")) {
      linearSlider.set(1);
    } else if (astro1.isButtonPressed("a")) {
      linearSlider.set(-1);
    } else {
      linearSlider.set(0);
    }

    if(astro1.isButtonPressed("b")) {
      intakeShooter.set(1);
    } else if (astro1.isButtonPressed("x")) {
      intakeShooter.set(-1);
    } else {
      intakeShooter.stop();
    }

  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
