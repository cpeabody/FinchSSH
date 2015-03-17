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

public class FinchSSH implements ConnectionMonitor {

    String hostname = "10.0.0.19";//"192.168.1.6"; //pi ip  haha
    String username = "pi";
    String password = "raspberry";
    Connection conn = null;
    String fileToTransfer = "Fin.java";
    String destinationPath = "/home/pi";
    String compileCmd = "sudo javac -classpath :Finch/finch.jar Fin.java";
    String runCmd = "sudo java -classpath Finch/finch.jar: Fin";
    /*
    HostName: 192.168.1.2
UserName: pi
Password: raspberry
FiletoTransferPath: C:\Users\Peabody\Documents\NetBeansProjects\FinchSSH\Fin.java
fileName: Fin.java
destinationPath: /home/pi
compileCmd: sudo javac -classpath :Finch/finch.jar Fin.java
runCmd: sudo java -classpath Finch/finch.jar: Fin
    */
    
   // String compileWalk = "sudo javac -classpath :finch.jar FinchCompetition.java";
   // String runWalk = "sudo java -classpath finch.jar: Walk";
    public FinchSSH() {
        conn = makeConnection(hostname, username, password);  // make a connection
       if (conn.isAuthenticationComplete()) {
        FileTransfer ft = new FileTransfer(conn, fileToTransfer, destinationPath);
        System.out.println("attempting transfer ...");
        ft.doTransfer(conn);//transfer File
        System.out.println("Transfer Complete!");
//        sendCommand(conn, cdCommand); //send a linux command
//        sendCommand(conn, pwdCommand); //send a linux command
        sendCommand(conn, compileCmd); //send a linux command  
        sendCommand(conn, runCmd); //send a linux command  
        } else{
//        
           System.out.println("Connection not made!");
       }

       closeProgram();
    }

    private Connection makeConnection(String host, String user, String passwrd) {
        Connection c = null;
        boolean isAuthenticated = false;

        try {
            c = new Connection(host);
            c.connect(); //CONNECTION
            isAuthenticated = c.authenticateWithPassword(user, passwrd);
        } catch (IOException ex) {
            System.out.println("Connection Failed!\nError: " + ex.getMessage());
        }

        if (isAuthenticated) {
            System.out.println("**** Connected to: " + host + " ****\n");
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
            System.out.println("ERROR at 74");
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

    public static void main(String[] args) {
        FinchSSH app = new FinchSSH();
    }
}
