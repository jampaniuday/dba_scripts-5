// -----------------------------------------------------------------------------
// BorderLayoutManager.java
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
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * -----------------------------------------------------------------------------
 * The BorderLayout manager has five regions in which to place components:
 * NORTH, SOUTH, EAST, WEST, and CENTER. When you enlarge the window, the center
 * region will get as much of the available space as possible. The other regions
 * will only expand as much as necessary to fill the available space.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class BorderLayoutManager extends JFrame {

    // Object fields
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;


    /**
     * Noarg constructor
     */
    public BorderLayoutManager() {

        // -------------------------------------------------------------
        // Call super class constructor plus set the size for the Window
        // -------------------------------------------------------------
        super("BorderLayout Manager Example");
        this.setSize(450, 250);

        // ------------------------------------------------------------------
        // Get the frame's content pane. It is not neccessary to set the 
        // layout manager to BorderLayout (as I did below) since BorderLayout
        // is the default for the frame's content pane.
        // ------------------------------------------------------------------
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());

        // ----------------------------------
        // Create all 5 buttons for this test
        // ----------------------------------
        button1 = new Button("Button 1 - (NORTH)");
        button2 = new Button("Button 2 - (CENTER)");
        button3 = new Button("Button 3 - (WEST)");
        button4 = new Button("Button 4 - (SOUTH)");
        button5 = new Button("Button 5 - (EAST)");
    
        // -----------------------------------------------------
        // Add all buttons directory to the frame's content pane
        // -----------------------------------------------------
        contentPane.add(button1, BorderLayout.NORTH);
        contentPane.add(button2, BorderLayout.CENTER);
        contentPane.add(button3, BorderLayout.WEST);
        contentPane.add(button4, BorderLayout.SOUTH);
        contentPane.add(button5, BorderLayout.EAST);

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
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        BorderLayoutManager mainFrame = new BorderLayoutManager();
        mainFrame.setVisible(true);
    }
    
}