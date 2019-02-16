package frc.Mechanisms
import edu.wpi.first.wpilibj.*;

public class LinearSlide {
	
	private Victor slide;
	public int slideLevel;
	private int limitedSlide;
	private double limit;
	
	public LinearSlide(int port) {
		slide = new Victor(port);
		slideLevel = 0;
		limitedSlide = 0;
		limit = 0.025;
	}
	
	public void slide(double slideMotorMove) {
		double change = 0.0;
	    change = slideMotorMove - limitedSlide;
	    if (Math.abs(change) > limit)
	    {
	      int sign = (int)Math.signum(change);
	      change = limit * sign;
	    }

	    limitedSlide += change;
	    slide.set(limitedSlide);
	}
	
	
	
	
	
}