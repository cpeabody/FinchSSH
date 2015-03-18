package view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainWindow extends JFrame {

    private Container content;
    private int window_HEIGHT;
    private int window_WIDTH;
    private Dimension window_Dimension;
    private JLabel testLabel = new JLabel("NOTHING HAS BEEN ADDED TO THE CONTENT PANE");

    public MainWindow(int width, int height) throws HeadlessException {
        window_Dimension = new Dimension(width, height);
        this.setTitle("RRC - Remote Robot Connection");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(100, 80);
        content = this.getContentPane();
        content.add(testLabel);
        // this.setMinimumSize(window_Dimension);        
    }

    public void addContent(JPanel pane) {

        content.removeAll();
        content.add(pane);
        this.pack();
        this.update(this.getGraphics());
        this.setVisible(true);
    }

    public void closeWindow() {
        this.setVisible(false);
        this.dispose();
    }

}
