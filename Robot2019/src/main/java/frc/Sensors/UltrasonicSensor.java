package frc.Sensors;
import edu.wpi.first.wpilibj.*;
public class UltrasonicSensor implements Runnable {

    public Ultrasonic ultra;//, ultraSlide;
    public double dist;
    public boolean enabled;

    public UltrasonicSensor(Ultrasonic ultra) {
        this.ultra = ultra;
        this.enabled = true;
        this.ultra.setEnabled(true);
    }

    @Override
    public void run() {
        while(enabled) {
            ultra.ping();
            try
            {
                Thread.sleep(100);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            this.dist = ultra.getRangeInches();
        }
    }
    
    public double getDist() {
    	return this.dist;
    }
    
    public void setEnabled (boolean enabled) {
    	this.enabled = enabled;
    	this.ultra.setEnabled(enabled);
    }
    
}