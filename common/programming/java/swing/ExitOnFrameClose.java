// -----------------------------------------------------------------------------
// ExitOnFrameClose.java
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
 * 
 * For many of the application you write, closing the main application frame
 * should cause the program to exit (shutting down the virtual machine). 
 * However, the default implementation is to only "hide" the frame when the 
 * application frame is closed, leaving the virtual machine running with no
 * visible frame. There are two ways to exit an application when the frame is
 * closed:
 * 
 * 1.) Using a Window Listener
 * 
 *     // ------------------------------------------------------------
 *     // Window listener to close application when Window gets closed
 *     // ------------------------------------------------------------
 *     this.addWindowListener(new WindowAdapter() {
 *         public void windowClosing(WindowEvent e) {
 *             System.exit(0);
 *         }
 *     });
 *  
 *  2.) Use the setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) Method
 *      (Java SDK 1.4 and higher)
 *
 *     // -------------------------------------
 *     // Close Application When Frame is Close
 *     // -------------------------------------
 *     mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 * 
 * -----------------------------------------------------------------------------
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ExitOnFrameClose extends JFrame {

    /**
     * Noarg constructor
     */
    public ExitOnFrameClose() {

        // -------------------------------------------------------------
        // Call super class constructor plus set the size for the Window
        // -------------------------------------------------------------
        super("Exit On Frame Close Example");
        this.setSize(350, 150);

        // -------------------------------------
        // Close Application When Frame is Close
        // -------------------------------------
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ------------------------------------------------------------------
        // Get the frame's content pane. It is not neccessary to set the 
        // layout manager to BorderLayout (as I did below) since BorderLayout
        // is the default for the frame's content pane.
        // ------------------------------------------------------------------
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());

        // -----------------
        // Simple text label
        // -----------------
        JLabel label1 = new JLabel("Exit Application on Frame Close Example", JLabel.LEFT);
    
        // -----------------------------------------------------
        // Add all buttons directory to the frame's content pane
        // -----------------------------------------------------
        contentPane.add(label1, BorderLayout.SOUTH);

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        ExitOnFrameClose mainFrame = new ExitOnFrameClose();
        mainFrame.setVisible(true);
    }
    
}