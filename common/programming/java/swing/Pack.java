// -----------------------------------------------------------------------------
// Pack.java
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
 * The pack() method uses its layout manager to size a Window to fit the 
 * preferred size and layouts of its subcomponents. 
 * 
 * It is important to understand that the pack() method uses a layout manager to
 * control the size of the Window; you don’t want to use it if you don’t have a 
 * layout manager (like that used in absolute positioning).
 * 
 * In many references, you will see arguments on both sides as whether to use 
 * setSize() or pack(). Neither is right or wrong. It is at the decision of the 
 * developer as whether to use exact dimensions for the Window using setSize() 
 * or the automatic pack() method.
 * 
 * Whatever decision is made it is important to understand that either the 
 * pack() method or setSize() method should be called to set the size of the
 * JFrame. If neither is called, the height and width of the Window will be 0.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class Pack extends JFrame {

    // Object fields
    private Button button1 = new Button("Button 1");
    private Button button2 = new Button("Button 2");
    private Button button3 = new Button("Button 3");
    private Button button4 = new Button("Button 4");
    private Button button5 = new Button("Button 5");


    /**
     * Noarg constructor
     */
    public Pack() {

        super("pack() Example");

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        Container contentPane = this.getContentPane();
        contentPane.setLayout(new FlowLayout());

        contentPane.add(button1);
        contentPane.add(button2);
        contentPane.add(button3);
        contentPane.add(button4);
        contentPane.add(button5);

        // this.setSize(450, 90);
        this.pack();
        
    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        Pack mainFrame = new Pack();
        mainFrame.setVisible(true);
    }
    
}