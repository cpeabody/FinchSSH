package finchssh;

import java.util.Scanner;
import view.FeedPanel;
import view.MainWindow;
import view.RunPanel;
import view.PropPanel;
import view.SplashPanel;

public class GUITester {

    private String input_IP = null;
    private String input_User = null;
    private String input_Password = null;
    private final MainWindow window;
    private final Scanner scan = new Scanner(System.in); //used in bebug
    RunPanel rPan;
    FeedPanel fPan;

    public GUITester() {
        window = new MainWindow(600, 400);

        //debug alt exit from terminal
        doAction(getOption(), window);
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
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            System.out.println("Sleep Error");
            System.exit(-1);
        }

        //create  and add properties panel
        PropPanel pPan = new PropPanel(this);
        window.addContent(pPan);

        //debug alt exit from terminal
        doAction(getOption(), window);

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

    public void displayFeedback(String str) {
        fPan.addText(str);
    }

    public void initFeedbackPanel() {
        fPan = new FeedPanel();
        window.addContent(fPan);
    }

    public void CompleteRun() {
        fPan.clearText();
        window.addContent(rPan);
    }

    /**
     * Hides main Window
     */
    public void disableWindow() {
        window.setVisible(false);
    }

    /**
     * Sets main window to visible
     */
    public void enableWindow() {
        window.setVisible(true);
    }

    public static void main(String[] args) {
        GUITester app = new GUITester();
    }

    /**
     * Debug Method
     *
     * @return return option for alternative exit from program
     */
    private int getOption() {
        String optionList = "\nSelect an Option:\n1) Close\n2)Exit\n--->  ";
        System.out.print(optionList);
        int opt = scan.nextInt();
        return opt;
    }

    /**
     * Debug Method:
     *
     * @param action alternative exit from program
     * @param win window
     */
    private void doAction(int action, MainWindow win) {

        switch (action) {
            case 1:
                System.out.println("Close Slected!\n Closing..\n");
                win.closeWindow();
                break;
            case 2:
                System.out.println("Exit Selected!\n Exiting program..\n");
                System.exit(0);
            default:
                System.out.println("Invalid Option!\n Exiting program..\n");
                System.exit(-1);
        }

    }
}
