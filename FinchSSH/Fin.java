
import edu.cmu.ri.createlab.terk.robot.finch.Finch;


public class Fin {

    public static void main(final String[] args) {
       Finch myFinch = new Finch();
        myFinch.setLED(000, 000, 255,1000);
        myFinch.setLED(000, 255, 000,1000);
        myFinch.setLED(255, 000, 000,1000);
        
        myFinch.setLED(000, 000, 255);
        myFinch.buzzBlocking(255, 2000);
        
         myFinch.setLED(000, 255, 000);        
        myFinch.saySomething("This Finch Is Working!", 2000);
        
        
         myFinch.setLED(255, 000, 000);
        System.out.println("The temp: " + myFinch.getTemperature());
        
        myFinch.setLED(000, 000, 255,1000);
        myFinch.setLED(000, 255, 000,1000);
        myFinch.setLED(255, 000, 000,1000);

        myFinch.quit();
    }

}