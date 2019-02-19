package frc.Mechanisms;
import edu.wpi.first.wpilibj.*;

public class Platypus{
	
	private Victor platypus;
	private int timer;
	
	public Platypus(int port) {
		platypus = new Victor(port);
	}
	
	public void openPlatypus() {
		while(timer < 50) {
			platypus.set(0.5);
			timer++;
		}
		timer = 0;
		platypus.set(0);
	}
	
	public void closePlatypus() {
		while(timer < 50) {
			platypus.set(-0.5);
			timer++;
		}
		timer = 0;
		platypus.set(0);
	}

	public void set(double value)
	{
		platypus.set(value);
	}
	
}