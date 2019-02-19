package frc.Mechanisms;
import edu.wpi.first.wpilibj.*;

public class IntakeShooter {
	
	private Victor intakeShooter;
	private int timer;
	
	public IntakeShooter(int port) {
		intakeShooter = new Victor(port);
		timer = 0;
	}
	
	public void intake() {
		while(timer < 50) {
			intakeShooter.set(1);
			timer++;
		}
		timer = 0;
		stop();
	}
	
	public void shoot() {
		intakeShooter.set(1);
	}
	
	public void stop() {
		intakeShooter.set(0);
	}
	
	public void set(double val) {
		intakeShooter.set(val);
	}
	
}