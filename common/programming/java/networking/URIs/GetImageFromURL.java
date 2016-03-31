// -----------------------------------------------------------------------------
// GetImageFromURL.java
// -----------------------------------------------------------------------------

 /*
  * =============================================================================
  * Copyright (c) 1998-2011 Jeffrey M. Hunter. All rights reserved.
  * 
  * All source code and material located at the Internet address of
  * http://www.idevelopment.info is the copyright of Jeffrey M. Hunter and
  * is protected under copyright laws of the United States. This source code may
  * not be hosted on any other site without my express, prior, written
  * permission. Application to host any of the material elsewhere can be made by
  * contacting me at jhunter@idevelopment.info.
  *
  * I have made every effort and taken great care in making sure that the source
  * code and other content included on my web site is technically accurate, but I
  * disclaim any and all responsibility for any loss, damage or destruction of
  * data or any other property which may arise from relying on it. I will in no
  * case be liable for any monetary damages arising from such loss, damage or
  * destruction.
  * 
  * As with any code, ensure to test this code in a development environment 
  * before attempting to run it in production.
  * =============================================================================
  */
  
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * -----------------------------------------------------------------------------
 * This class provides an example of how to get an image from a given URL.
 * -----------------------------------------------------------------------------
 */

public class GetImageFromURL extends    JFrame 
                             implements ActionListener {

    private JButton  exitButton = null;
    private URL     url        = null;
        
    /**
     * Noarg constructor
     */
    public GetImageFromURL() {

        // ---------------------------------------------------------------------
        // Call super class constructor plus set the size for the Window
        // ---------------------------------------------------------------------
        super("Get Image from URL Example");
        this.setSize(170, 130);

        // ---------------------------------------------------------------------
        // Get the frame's content pane. It is not neccessary to set the 
        // layout manager to BorderLayout (as I did below) since BorderLayout
        // is the default for the frame's content pane.
        // ---------------------------------------------------------------------
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());


        // ---------------------------------------------------------------------
        // Now get the Image (graphic) from the given URL
        // ---------------------------------------------------------------------
        try {
            url = new URL("http://www.idevelopment.info:80/gifs/oracleonlinux.gif");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // ---------------------------------------------------------------------
        // Get the image from the URL
        // ---------------------------------------------------------------------
        Image image = Toolkit.getDefaultToolkit().getDefaultToolkit().createImage(url);


        // ---------------------------------------------------------------------
        // Exit button
        // ---------------------------------------------------------------------
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);


        // ---------------------------------------------------------------------
        // Finally create a ImagePanel to display the image and exit button
        // ---------------------------------------------------------------------
        ImagePanel imagePanel = new ImagePanel(image);
        contentPane.add(imagePanel, BorderLayout.CENTER);
        contentPane.add(exitButton, BorderLayout.SOUTH);


        // ------------------------------------------------------------
        // Window listener to close application when Window gets closed
        // ------------------------------------------------------------
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }


    /**
     * The action listener for user events like buttons.
     * @param ae Event that was performed by the user
     */
    public void actionPerformed(ActionEvent ae) {

        String action = ae.getActionCommand();
                
        if (action.equals("Exit")) {
            dispose();
            System.out.println("Exiting.");
            System.exit(0);
        } else {
            System.out.println(action);
        }

    }
            

    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {

        GetImageFromURL mainFrame = new GetImageFromURL();
        mainFrame.setVisible(true);
        
    }

}


class ImagePanel extends JPanel {

    Image image;

    public ImagePanel(Image image) {
        this.image = image;
    }

    public void paintComponent(Graphics g) {

        // paint background
        super.paintComponent(g);

        //Draw image at its natural size
        g.drawImage(image, 0, 0, this); //85x62 image

    }
}
