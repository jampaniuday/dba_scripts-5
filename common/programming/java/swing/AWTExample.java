// -----------------------------------------------------------------------------
// AWTExample.java
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

/**
 * -----------------------------------------------------------------------------
 * This class provides an example of a simple AWT frame that contains an
 * example toolbar implementation made up of several AWT button objects.
 * This example will contain three buttons typically found in toolbar -
 * Copy, Cut, and Paste.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class AWTExample extends Frame {

    // Object fields
    private Button copyButton;
    private Button cutButton;
    private Button pasteButton;
    private Button  exitButton;


    /**
     * Public no-arg constructor
     */
    public AWTExample() {

        super("Simple AWT Example");
        setSize(450, 250);

        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );

        ActionListener buttonListener = new ActionListener() {
        
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
            
        };


        // Toolbar Panel
        Panel toolbarPanel = new Panel();
        toolbarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        copyButton = new Button("Copy");
        copyButton.addActionListener(buttonListener);
        toolbarPanel.add(copyButton);

        cutButton = new Button("Cut");
        cutButton.addActionListener(buttonListener);
        toolbarPanel.add(cutButton);

        pasteButton = new Button("Paste");
        pasteButton.addActionListener(buttonListener);
        toolbarPanel.add(pasteButton);

        add(toolbarPanel, BorderLayout.NORTH);

        // Bottom Panel
        Panel bottomPanel = new Panel();

        exitButton = new Button("Exit");
        exitButton.addActionListener(buttonListener);
        bottomPanel.add(exitButton);
        
        add(bottomPanel, BorderLayout.SOUTH);

    }




    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {

        AWTExample mainFrame = new AWTExample();
        mainFrame.setVisible(true);

    }

}
