/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.networktables.*;

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


  Victor driveMotorLeftOne, driveMotorLeftTwo, driveMotorRightOne, driveMotorRightTwo, led;
  Joystick astronautOne, astronautTwo;
  NetworkTable table;
  NetworkTableEntry direction, camMode;
  NetworkTableValue left, right, forward;
  AnalogInput ultraSonic;
  Encoder enc;
  

  //joystick constants
  int leftXAxis = 0;
  int leftYAxis = 1;
  int leftTrigger = 2;
  int rightTrigger = 3;
  int rightXAxis = 4;
  int rightYAxis = 5; 
  double deadzone = 0.05;
  double maxSpeed = 0.6;
  int buttonA = 1;
  boolean buttonAPress = false;
  boolean buttonAToggle = true;

  @Override
  public void robotInit() {
    driveMotorLeftOne = new Victor(7);
    driveMotorLeftTwo = new Victor(8);
    driveMotorRightOne = new Victor(0);
    driveMotorRightTwo = new Victor(1);
    led = new Victor(9);
    astronautOne = new Joystick(0);
    astronautTwo = new Joystick(1);
    //table = NetworkTableInstance.getDefault().getTable("Smart Dashboard");
    //table = NetworkTableInstance.getDefault().getTable("SmartDashboard");
    table = NetworkTableInstance.getDefault().getTable("SmartDashboard");
    direction = table.getEntry("dir");
    table.getEntry("left").setValue(new String("l"));
    left = table.getEntry("left").getValue();
    table.getEntry("right").setValue(new String("r"));
    right = table.getEntry("right").getValue();
    table.getEntry("forward").setValue(new String("f"));
    forward = table.getEntry("forward").getValue();
    ultraSonic = new AnalogInput(0);
    enc = new Encoder(0, 1, true, Encoder.EncodingType.k2X);
    enc.reset();
    enc.setMaxPeriod(.5);
    enc.setMinRate(0.1);
    enc.setDistancePerPulse(0.0048);
    enc.setSamplesToAverage(50);
   // enc.setReverseDirection(true);

   camMode = table.getEntry("cam");
   
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {

    direction = table.getEntry("dir");

    System.out.println(direction.getValue());

    if(direction.getValue().equals(left))
    {
      System.out.println("l");
      motorSet(0, -0.15);
    }
    else if(direction.getValue().equals(right))
    {
      System.out.println("r");
      motorSet(-0.15, 0);
    }
    else if(direction.getValue().equals(forward))
    {
      System.out.println("f");
      motorSet(-0.15, -0.15);
    }

    //some real sound like shit down here

  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    led.set(0);
    double leftMotorMove = astronautOne.getRawAxis(leftYAxis);
    double rightMotorMove = astronautOne.getRawAxis(rightYAxis);

    //double theBigDist = enc.get();
    double raw = enc.getRaw();
    double distance = enc.getDistance();
    double period = enc.getPeriod();
    double rate = enc.getRate();
    boolean direction = enc.getDirection();
    boolean stopped = enc.getStopped();
    System.out.println("Raw: " + raw);
    System.out.println("Distance: " + distance);
    System.out.println("Period: " + period);
    System.out.println("Rate: " + rate);
    System.out.println("Direction: " + direction);
    System.out.println("stopped: " + stopped);
    //System.out.println(ultraSonic.getVoltage());


    if(Math.abs(leftMotorMove) <= deadzone)
    {
      leftMotorMove = 0D;
    }

    if(Math.abs(rightMotorMove) <= deadzone)
    {
      rightMotorMove = 0D;
    }

    motorSet(leftMotorMove * maxSpeed, rightMotorMove * maxSpeed);

    /*double rawVolt = ai.getVoltage();
    rawVolt = (rawVolt * 50) ;
    System.out.println(rawVolt);*/

    

    if(astronautOne.getRawButton(buttonA) && buttonAToggle) {
      buttonAToggle = false;
      if(buttonAPress) {
        buttonAPress = false;
        camMode.setValue(new String("y"));
      } else {
        buttonAPress = true;
        camMode.setValue(new String("n"));
      }
    } else if (!astronautOne.getRawButton(buttonA)) {
      buttonAToggle = true;
    }

  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

  private void motorSet(double leftVal, double rightVal)
  {
    driveMotorLeftOne.set(leftVal * -1);
    driveMotorLeftTwo.set(leftVal * -1);
    driveMotorRightOne.set(rightVal);
    driveMotorRightTwo.set(rightVal);
  }

}
