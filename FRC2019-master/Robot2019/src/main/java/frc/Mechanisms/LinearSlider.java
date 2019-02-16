package frc.Mechanisms
import edu.wpi.first.wpilibj.*;

public class LinearSlider {
	
	private Victor slider;
	private int slideLevel;
	private int limitedSlide;
	private double limit;
	
	public LinearSlider(int port) {
		slider = new Victor(port);
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
	    slider.set(limitedSlide);
	}
	
	public void setSlideLevel(int level) {
		this.slideLevel = level % 3;
	}
	
	public int getSlideLevel() {
		return this.slideLevel
	}
	
	public void stop() {
		slider.set(0);
	}
	
	
	
}