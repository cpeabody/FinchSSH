package finchssh;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.ConnectionMonitor;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peabody
 */
public class FinchSSHTool implements ConnectionMonitor {

    GUITester app;
    String hostname;
    String username;
    String password;
    Connection conn = null;
    String fileToTransferPath;
    String fileName;
    String destinationPath = "/home/pi";
    String compileCmd;
    String runCmd;

    public FinchSSHTool(String hostname, String username, String password, String fileToTransferPath, String fileName, GUITester app) {
        this.app = app;
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.fileToTransferPath = fileToTransferPath;
        this.fileName = fileName;
        String classFileName = fileName.substring(0, fileName.lastIndexOf("."));
        compileCmd = "sudo javac -classpath :Finch/finch.jar " + fileName;
        runCmd = "sudo java -classpath Finch/finch.jar: " + classFileName;
        //app.disableWindow();
        
        app.initFeedbackPanel();

        app.displayFeedback("\n=================================");
        app.displayFeedback("HostName: " + this.hostname);
        app.displayFeedback("UserName: " + this.username);
        app.displayFeedback("Password: " + this.password);
       app.displayFeedback("FiletoTransferPath: " + this.fileToTransferPath);
        app.displayFeedback("fileName: " + this.fileName);
        app.displayFeedback("destinationPath: " + destinationPath);
        app.displayFeedback("compileCmd: " + compileCmd);
        app.displayFeedback("runCmd: " + runCmd);
        app.displayFeedback("=================================\n");
        
//        System.out.println("\n=================================");
//        System.out.println("HostName: " + this.hostname);
//        System.out.println("UserName: " + this.username);
//        System.out.println("Password: " + this.password);
//        System.out.println("FiletoTransferPath: " + this.fileToTransferPath);
//        System.out.println("fileName: " + this.fileName);
//        System.out.println("destinationPath: " + destinationPath);
//        System.out.println("compileCmd: " + compileCmd);
//        System.out.println("runCmd: " + runCmd);
//        System.out.println("=================================\n");

        //run program
        runFinchConnect();

       // app.enableWindow();
    }
    
    
    
    
    
    
    private void runFinchConnect() {
        conn = makeConnection(hostname, username, password);  // make a connection
        if (conn.isAuthenticationComplete()) {
            FileTransfer ft = new FileTransfer(conn, fileToTransferPath, destinationPath);
            System.out.println("attempting transfer ..."); //test
            app.displayFeedback("Transfering file.....");
            ft.doTransfer(conn);//transfer File
            System.out.println("Transfer Complete!"); //test
            app.displayFeedback("..Complete");
            app.displayFeedback("Compiling");
            
            sendCommand(conn, compileCmd); //send a linux command  
            app.displayFeedback("..Complete");
            app.displayFeedback("Running program...");
            sendCommand(conn, runCmd); //send a linux command  
            app.displayFeedback("..Complete");
        } else {     
            System.out.println("Connection not made!");
            app.displayFeedback("Connection not made!");
        }

        app.CompleteRun();
        conn.close();
       // closeProgram();

    }
    
    
        private Connection makeConnection(String host, String user, String passwrd) {
        Connection c = null;
        boolean isAuthenticated = false;

        try {
            c = new Connection(host);
            c.connect(); //CONNECTION
            isAuthenticated = c.authenticateWithPassword(user, passwrd);
        } catch (IOException ex) {
            app.displayFeedback("Connection Failed!\nError: " + ex.getMessage());
            System.out.println("Connection Failed!\nError: " + ex.getMessage()); //test
        }

        if (isAuthenticated) {
            System.out.println("**** Connected to: " + host + " ****\n");
            app.displayFeedback("**** Connected to: " + host + " ****\n"); //test
        }
        return c;
    }

    public void sendCommand(Connection conn, String cmd) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(FinchSSH.class.getName()).log(Level.SEVERE, null, ex);
        }

        Session sess = null;
        try {
            sess = conn.openSession();// Create a session 
            sess.execCommand(cmd);
            InputStream stdout = new StreamGobbler(sess.getStdout());
            InputStream stderr = new StreamGobbler(sess.getStderr());
            BufferedReader buff_STDOUT = new BufferedReader(new InputStreamReader(stdout));
            BufferedReader buff_STDERR = new BufferedReader(new InputStreamReader(stderr));
            checkForFeedback_Simple(buff_STDOUT);
            checkForFeedback_Simple(buff_STDERR);

            sess.close();
        } catch (IOException e) {
            e.printStackTrace(System.err);
            System.out.println("ERROR at 74"); //test
            System.exit(2);
        }

    }

    private void checkForFeedback_Simple(BufferedReader br) throws IOException {
        boolean read = true;
        while (read) {
            String line = br.readLine();

            if (line == null) {
                read = false;
            }
            System.out.println(line);
            app.displayFeedback(line);
            
        }
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void closeProgram() {

        System.out.println("Closing connection!");
        conn.close();
        System.exit(0);
    }
}
