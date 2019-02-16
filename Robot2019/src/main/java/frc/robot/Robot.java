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


  Victor driveMotorLeft1, driveMotorLeft2, driveMotorRight1, driveMotorRight2;
  Victor slide;
  Victor grasshopper1, grasshopper2;
  Victor platypus;
  Victor ballIntake;
  Joystick astronautOne, astronautTwo;
  NetworkTable table;
  NetworkTableEntry direction, camMode;
  NetworkTableValue left, right, forward;
  AnalogInput encoder;
  Ultrasonic ultraSlide;
  char startPos;

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
  int buttonY = 4;
  int buttonX = 3;
  int buttonB = 2;
  boolean buttonXPress = false;
  boolean buttonXToggle = true;
  double limit = 0.025D;
  double limitedLeft = 0D;
  double limitedRight = 0D;
  double limitedSlide = 0D;

  //Encoder
  int initialAngle = 0;
  int currentAngle = 0;
  int nextAngle = 0;
  int numRotations = 0;
  int totDegreeFromStart = 0;
  int literalCancer = 0;
  double diameter = 1.395;

  //ultrasonic
  double rangeFront;
  UltrasonicHandler ultraHandlerFront, ultraHandlerSlide;
  Thread ultraThreadFront, ultraThreadSlide;

  //grasshopper
  boolean grasshopperUp = false;

  //auto delivery
  int tracking;
  boolean dropping;

  @Override
  public void robotInit() {

    tracking = false;
    dropping = false;

    driveMotorLeft1 = new Victor(5);
    driveMotorLeft2 = new Victor(6);
    driveMotorRight1 = new Victor(0);
    driveMotorRight2 = new Victor(1);
    grasshopper1 = new Victor(2);
    grasshopper2 = new Victor(3);
    slide = new Victor(7);
    platypus = new Victor(4);
    ballIntake = new Victor(8);

    astronautOne = new Joystick(0);
    astronautTwo = new Joystick(1);
   
    encoder = new AnalogInput(0);
    initialAngle = (int)(encoder.getVoltage()*360/4.76);
    currentAngle = (int)(encoder.getVoltage()*360/4.76);
   
    ultraHandlerFront = new UltrasonicHandler(new Ultrasonic(8, 9, Unit.kInches));//, ultraSlide);
    ultraHandlerSlide = new UltrasonicHandler(new Ultrasonic(6, 7, Unit.kInches));
    ultraThreadFront = new Thread(ultraHandlerFront);
    ultraThreadSlide = new Thread(ultraHandlerSlide);

    table = NetworkTableInstance.getDefault().getTable("SmartDashboard");
    direction = table.getEntry("dir");
    left = table.getEntry("left").getValue();
    right = table.getEntry("right").getValue();
    forward = table.getEntry("forward").getValue();
   
    table.getEntry("left").setValue(new String("l"));
    table.getEntry("right").setValue(new String("r"));
    table.getEntry("forward").setValue(new String("f"));
   
    camMode = table.getEntry("cam");

    SmartDashboard.putString("Start Position", "m");
   
    ultraThreadFront.start();
    ultraThreadSlide.start();
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

    direction = table.getEntry("dir");

    System.out.println("Slide:" + ultraHandlerSlide.dist);
    System.out.println("Other:" + ultraHandlerFront.dist);

   // System.out.println(direction.getValue());
    
    String dir = direction.getString("bad");
    visionMove(dir);
    
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    System.out.println("Slide:" + ultraHandlerSlide.dist);

    double leftMotorMove = astronautOne.getRawAxis(leftYAxis);
    double rightMotorMove = astronautOne.getRawAxis(rightYAxis);
    
    nextAngle = (int)(encoder.getVoltage()*360/4.76);
    calcEncRot(currentAngle, nextAngle);
    currentAngle = nextAngle;

    if(Math.abs(leftMotorMove) <= deadzone)
    {
      leftMotorMove = 0D;
    }

    if(Math.abs(rightMotorMove) <= deadzone)
    {
      rightMotorMove = 0D;
    }

    //slide.set(limitedRight*-1);

    motorSet(leftMotorMove * maxSpeed, rightMotorMove * maxSpeed);
    
    if(astronautOne.getRawButton(buttonX))
    {
      deliverHatch();
      tracking = 1;
    }
    else if(astronautOne.getRawButton(buttonB))
    {
      deliverHatch();
      tracking = 2;
    }

    if(tracking > 0)
    {
      deliverHatch();
    }
    else if(dropping)
    {
      backUpBot();
    }

    //Camera switch
    /*if(astronautOne.getRawButton(buttonX) && buttonXToggle) 
    {
      buttonXToggle = false;
      if(buttonXPress) 
      {
        buttonXPress = false;
        camMode.setValue(new String("y"));
      } 
      else 
      {
        buttonXPress = true;
        camMode.setValue(new String("n"));
      }
    } 
    else if (!astronautOne.getRawButton(buttonX)) 
    {
      buttonXToggle = true;
    }*/
    
    //Grasshopper Toggle
    if(astronautOne.getRawButton(buttonY)) 
    {
        grasshopper1.set(1);
        grasshopper2.set(1);
        System.out.println("up");
    } else if (astronautOne.getRawButton(buttonA)) {
      grasshopper1.set(-1);
      grasshopper2.set(-1);
      System.out.println("down");
    } else {
      grasshopper1.set(0);
      grasshopper2.set(0);
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
    driveMotorLeft1.set(leftVal * -1);
    driveMotorLeft2.set(leftVal * -1);
    driveMotorRight1.set(rightVal);
    driveMotorRight2.set(rightVal);
  }

  private void calcEncRot(int currentAngle, int nextAngle) {
    totDegreeFromStart = numRotations*360 - initialAngle + nextAngle;

    //System.out.println("Rotation #: " + numRotations);
    //System.out.println("Total rotations: " + totDegreeFromStart);

    if(currentAngle > 315 && nextAngle < 50) 
    {
      numRotations++;
    } 
    else if (currentAngle < 50 && nextAngle > 315) 
    {
      numRotations--;
    }

    System.out.println((totDegreeFromStart/360)*diameter*Math.PI*4);

  }

  private void visionMove(String direction) {
    if(direction.equalsIgnoreCase("l"))
    {
      System.out.println("l");
      motorSet(0, 0.15);
    }
    else if(direction.equalsIgnoreCase("r"))
    {
      System.out.println("r");
      motorSet(0.15, 0);
    }
    else if(direction.equalsIgnoreCase("f"))
    {
      System.out.println("f");
      motorSet(0.15, 0.15);
    }

    //If string method doesn't work
    /*if(direction.getValue().equals(left))
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
    }*/
  }

  private void deliverHatch()
  {
    if(ultraHandlerFront.dist > 6)
    {
      visionMove(table.getEntry("dir").getString("bad"));
    }
    else if(ultraHandlerFront.dist <= 6)
    {
      if(ultraHandlerSlide.dist >= 18 * tracking)
      {
        moveSlider(0);
        if(ultraHandlerFront.dist <= 4)
        {
          motorSet(0, 0);
          tracking = 0;
          dropping = true;
        }
        else
        {
          motorSet(0.15, 0.15);
        }
      }
      else
      {
        moveSlider(0.25);
      }
    }
  }

  private void backUpBot()
  {
    if(ultraHandlerFront.dist <= 6)
    {
        motorSet(-0.15, -0.15);
    }
    else if(ultraHandlerSlide.dist >= 16)
    {
      moveSlider(-0.25);
    }
    else
    {
      dropping = false;
    }
  }

  private void moveSlider(double value)
  {
    double change = 0D;
    change = value - limitedSlide;
    if (Math.abs(change) > limit)
    {
      int sign = (int)Math.signum(change);
      change = limit * sign;
    }

    limitedSlide += change;
    slide.set(limitedSlide);
  }

}
