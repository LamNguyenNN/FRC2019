package frc.robot;
import edu.wpi.first.wpilibj.*;
public class UltrasonicHandler implements Runnable {

    public Ultrasonic ultra;//, ultraSlide;
    public double prevDist, dist;
    public double margin;
    public boolean running;

    public UltrasonicHandler(Ultrasonic ultra) {
        this.ultra = ultra;
        this.running = true;
        this.ultra.setEnabled(true);
        this.margin = 1;
        this.ultra.ping();
        this.prevDist = ultra.getRangeInches();

    }

    @Override
    public void run() {
        while(running) {
            this.prevDist = this.dist;
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
            if(Math.abs(this.dist-this.prevDist) > margin) {
                this.dist = this.prevDist;
            }
           // System.out.println(this.dist);
        }
    }
}