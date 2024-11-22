// REFERENCE: https://stackoverflow.com/questions/16134549/how-to-make-a-splash-screen-for-gui
// REFERENCE: Java Swing Tutorial for Beginners by Java Code Junkie on YouTube
// REFERENCE: https://www.youtube.com/watch?v=4PfDdJ8GFHI&list=PL3bGLnkkGnuV699lP_f9DvxyK5lMFpq6U&index=3

package ui;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

// creates a splash screen

public class Splash extends JFrame {

    // EFFECTS: constructs a splashs screen for the app
    public Splash() {
        super("Splash Screen");
        setSize(1000, 1000);
        setLayout(new BorderLayout());
        // image created by Gemini AI
        JLabel splashScreen = new JLabel(new ImageIcon("data/images/Gemini_Generated_Image_p9po5yp9po5yp9po.jpeg"));
        add(splashScreen);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
