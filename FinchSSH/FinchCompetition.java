import edu.cmu.ri.createlab.terk.robot.finch.Finch;

public class FinchCompetition {




    public static void main(final String[] args) {
       Finch myFinch = new Finch();


myFinch.setWheelVelocities(200, 200, 10000);
myFinch.quit();
    }

}
