package ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/*
* Represent application's main window frame
*/
public class ImagePanel extends JPanel {

    /*
     * REQUIRES: imagePath != null
     * EFFECTS: loads the image from the given path and display it in a centered JLabel.
     */
    public ImagePanel(String imagePath) {
        setLayout(new BorderLayout());
        JLabel imageLabel = createImageLabel(imagePath);
        add(imageLabel, BorderLayout.CENTER);
    }

    /*
     * REQURIES: imagePath != null
     * EFFECTS: creates and returns a JLabel with the specified image
     */
    private JLabel createImageLabel(String imagePath) {
        JLabel backgroundImage = new JLabel(new ImageIcon(imagePath));
        
        if (new ImageIcon(imagePath).getImageLoadStatus() == MediaTracker.ERRORED) {
            System.out.println("Image not loaded: " + imagePath);
            System.out.println(new File(imagePath).getAbsolutePath());
        }
        
        backgroundImage.setHorizontalAlignment(SwingConstants.CENTER);

        return backgroundImage;
    }
}
