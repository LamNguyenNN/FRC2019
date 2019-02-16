package frc.Controller
import edu.wpi.first.wpilibj.*;

public class Astronaut {
	
	private static numControllers = 0;
	private Joystick joystick;
	
	final private int buttonA = 1;
	final private int buttonY = 4;
	final private int buttonX = 3;
	final private int buttonB = 2;
	final private int buttonLeftBumper = 5;
	final private int buttonRightBumper = 6;
	
	final int leftXAxis = 0;
	final int leftYAxis = 1;
	final int rightXAxis = 4;
	final int rightYAxis = 5;
	final int leftTrigger = 2;
	final int rightTrigger = 3;
	
	public Astronaut(int port) {
		numControllers++;
		joystick = new Joystick(port);
	}
	
	public double getLeftYAxis() {
		return joystick.getRawAxis(leftYAxis);
	}
	
	public double getRightYAxis() {
		return joystick.getRawAxis(rightYAxis);
	}
	
	public boolean isButtonPressed(String button) {
		int buttonValue = 0;
		switch(button.toLowerCase()):
			case "a":
				buttonValue = 1;
				break;
			case "b":
				buttonValue = 2;
				break;
			case "x":
				buttonValue = 3;
				break;
			case "y":
				buttonValue = 4;
				break;
			case "lb":
				buttonValue = 5;
				break;
			case "rb":
				buttonValue = 6;
				break;
		if(joystick.getRawButton(buttonValue)) {
			return true;
		} else {
			return false;
		}
	}
	
}