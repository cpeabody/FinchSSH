
import edu.cmu.ri.createlab.terk.robot.finch.Finch;


public class Fin {

    public static void main(final String[] args) {
       Finch myFinch = new Finch();
myFinch.setLED(255,000,000);
myFinch.setWheelVelocities(200, 200, 3000);
myFinch.setLED(000,255,000);
myFinch.setWheelVelocities(-200, -200, 3000);
myFinch.setLED(000,000,255);
myFinch.setWheelVelocities(200, -200, 3000);
myFinch.quit();
    }

}