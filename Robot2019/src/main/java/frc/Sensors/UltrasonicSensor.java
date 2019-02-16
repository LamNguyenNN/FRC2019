package frc.Sensors;
import edu.wpi.first.wpilibj.*;
public class UltrasonicHandler implements Runnable {

    public Ultrasonic ultra;//, ultraSlide;
    public double dist;
    public boolean running;

    public UltrasonicHandler(Ultrasonic ultra) {
        this.ultra = ultra;
        this.running = true;
        this.ultra.setEnabled(true);
    }

    @Override
    public void run() {
        while(running) {
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
}