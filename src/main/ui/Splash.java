// REFERENCE: https://stackoverflow.com/questions/16134549/how-to-make-a-splash-screen-for-gui

package ui;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Splash extends JFrame {
    public Splash() {
        super("Splash Screen");
        setSize(1000, 1000);
        setLayout(new BorderLayout());
        // image created by Gemini AI
        JLabel splashScreen = new JLabel(new ImageIcon("images/Gemini_Generated_Image_p9po5yp9po5yp9po.jpeg"));
        add(splashScreen);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            System.out.println("");
        }
        setVisible(false);
        dispose();
    }
}
