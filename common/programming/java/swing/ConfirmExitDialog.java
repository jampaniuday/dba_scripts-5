// -----------------------------------------------------------------------------
// ConfirmExitDialog.java
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
 * A common strategy when closing the main frame and exiting the application is
 * to display a dialog box asking the user something like, "Are you sure?". This
 * can be accomplished using the JOptionPane while reimplementing the 
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ConfirmExitDialog extends JFrame {

    /**
     * Noarg constructor
     */
    public ConfirmExitDialog() {

        // -------------------------------------------------------------
        // Call super class constructor plus set the size for the Window
        // -------------------------------------------------------------
        super("Confirm Exit Dialog Example");
        this.setSize(350, 150);

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
        JLabel label1 = new JLabel("Confirm Exit Dialog Example", JLabel.LEFT);

        // -----------------------------------------------------
        // Add all buttons directory to the frame's content pane
        // -----------------------------------------------------
        contentPane.add(label1, BorderLayout.SOUTH);
        
    }


    /**
     * Since we want to control what happens when a user attempts to close
     * out the frame, we need to override the
     * javax.swing.JFrame.processWindowEvent() method.
     * @param e WindowEvent being passed as a result of user actions at the
     *          Window level.
     */
    protected void processWindowEvent(WindowEvent e) {

        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
        
            int exit = JOptionPane.showConfirmDialog(this, "Are you sure?");
            if (exit == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
            
        } else {

            // If you do not want listeners processing the WINDOW_CLOSING
            // events, you could this next call in an else block for the:
            //     if (e.getID() ...)
            // statement. That way, only the other types of Window events
            // (iconification, activation, etc.) would be sent out.

            super.processWindowEvent(e);
        }
    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        ConfirmExitDialog mainFrame = new ConfirmExitDialog();
        mainFrame.setVisible(true);
    }
    
}