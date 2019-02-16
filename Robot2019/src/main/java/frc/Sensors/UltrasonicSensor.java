package frc.Sensors;
import edu.wpi.first.wpilibj.*;
public class UltrasonicHandler implements Runnable {

    public Ultrasonic ultra;//, ultraSlide;
    public double dist;
    public boolean enabled;

    public UltrasonicHandler(int ping, int echo) {
        this.ultra = new Ultrasonic(ping, echo)
        this.enabled = false;;
        this.ultra.setEnabled(false);
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