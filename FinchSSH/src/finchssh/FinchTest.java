
package finchssh;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;

/**
 *
 * @author CJ
 */
public class FinchTest {

    Finch myFinch = new Finch();

    public FinchTest() {
        
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
