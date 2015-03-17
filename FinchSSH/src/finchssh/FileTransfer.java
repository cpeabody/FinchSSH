package finchssh;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileTransfer {

    String toTransfer;
    String targetPath;

    public FileTransfer(Connection conn, String toTransfer, String targetPath) {

        this.toTransfer = toTransfer;
        this.targetPath = targetPath;
    }

    public boolean doTransfer(Connection conn) {

        Session sess = null;

        try {
            sess = conn.openSession();// Create a session
            SCPClient scp = new SCPClient(conn); //transfer client
            System.out.println("Client Made");
            scp.put(toTransfer, targetPath, "0777");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(FinchSSH.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("Put complete");

            sess.close();
            System.out.println("Transfer Complete!\n");
        } catch (IOException ex) {
            System.out.println("Transfer Failed");

        }

        return true;
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

}//end class
