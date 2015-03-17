package finchssh;

import java.util.Scanner;
import view.FeedPanel;
import view.MainWindow;
import view.RunPanel;
import view.PropPanel;
import view.SplashPanel;

public class ProgControl {

    private String input_IP = null;
    private String input_User = null;
    private String input_Password = null;
    private final MainWindow window;
    private final Scanner scan = new Scanner(System.in); //used in bebug
    private RunPanel rPan;
    private FeedPanel fPan;
    private PropPanel pPan;

    public ProgControl() {
        window = new MainWindow(600, 400);
        startProgram();
    }

    /**
     * Starts Program this method is only called by constructor
     */
    private void startProgram() {

        //create  and add splash panel
        SplashPanel sPan = new SplashPanel();
        window.addContent(sPan);

        try {
            Thread.sleep(2500);
        } catch (InterruptedException ex) {
            System.out.println("Sleep Error");
            System.exit(-1);
        }

        //create  and add properties panel
        pPan = new PropPanel(this);
        window.addContent(pPan);

    }

    /**
     * Called by properties Panel to set properties for SSH connection
     *
     * @param ip Address of pi on network
     * @param user User name (ex: pi)
     * @param password Password (ex: raspberry)
     */
    public void assignProperties(String ip, String user, String password) {
        input_IP = ip;
        input_User = user;
        input_Password = password;
        System.out.println("IP: " + input_IP + "\nUser: " + input_User + "\nPassword: " + input_Password + "\n");

//Start Run Panel
        rPan = new RunPanel(this);
        window.addContent(rPan);

    }

    /**
     * Called by RunPanel to transfer/compile/run selected file remotely
     *
     * @param filePath selected file path
     * @param fileName selected file Name
     */
    public void doFinchRun(String filePath, String fileName) {

        FinchSSHTool finchRun = new FinchSSHTool(input_IP, input_User, input_Password, filePath, fileName, this);

    }

    public boolean displayFeedback(String str) {

        fPan.addText(str);

        return true;
    }

    public boolean initFeedbackPanel() {
        fPan = new FeedPanel();
        window.addContent(fPan);
        return true;
    }

    public void CompleteRun() {
        fPan.clearText();
        window.addContent(rPan);
    }

    public RunPanel getrPan() {
        return rPan;
    }

    public FeedPanel getfPan() {
        return fPan;
    }

    public PropPanel getpPan() {
        return pPan;
    }

    public static void main(String[] args) {
        ProgControl app = new ProgControl();
    }

    public void goToPropPanel() {
        window.addContent(pPan);
    }

}
