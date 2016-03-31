// -----------------------------------------------------------------------------
// ChangeLookFeel.java
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
 * Demonstrate how to change the Look and Feel of an application.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */
 
public class ChangeLookFeel extends    JFrame
                            implements ActionListener, ItemListener    {

    // All Components
    JButton    metalButton    = null;
    JButton    windowsButton  = null;
    JButton    motifButton    = null;
    JComboBox  comboBox       = null;
    JButton    southButton    = null;       

    // Items for JComboBox
    final static String ITEM1  = "Item 1";
    final static String ITEM2  = "Item 2";
    final static String ITEM3  = "Item 3";

    
    /**
     * Noarg constructor
     */
    public ChangeLookFeel() {

        // ---------------------------------------------------------------------
        // Call super class constructor plus set the size for the Window
        // ---------------------------------------------------------------------
        super("Change Pluggable Look and Feel Example");


        // ---------------------------------------------------------------------
        // Get the frame's content pane. It is not neccessary to set the 
        // layout manager to BorderLayout (as I did below) since BorderLayout
        // is the default for the frame's content pane.
        // ---------------------------------------------------------------------
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());


        // ---------------------------------------------------------------------
        // Now create the button that will be used to change the pluggable
        // look and feel. Notice that since "this" implements ActionListener,
        // it can be passed to the addActionListener() method. The method that
        // will be called when any of these buttons are pressed is:
        // 
        //      public void actionPerformed(ActionEvent e)
        //
        // ---------------------------------------------------------------------
        metalButton = new JButton("Metal");
        metalButton.addActionListener(this);

        windowsButton = new JButton("Windows");
        windowsButton.addActionListener(this);

        motifButton = new JButton("Motif");
        motifButton.addActionListener(this);

        contentPane.add(metalButton,   BorderLayout.EAST);
        contentPane.add(windowsButton, BorderLayout.CENTER);
        contentPane.add(motifButton,   BorderLayout.WEST);


        // ---------------------------------------------------------------------
        // Finally, create a JButton and JComboBox component that show what
        // that component looks like given the different Look and Feels.
        // ---------------------------------------------------------------------
        String    comboBoxItems[] = {ITEM1, ITEM2, ITEM3};
        JComboBox comboBox = new JComboBox(comboBoxItems);
        comboBox.setEditable(false);
        comboBox.addItemListener(this);
        contentPane.add(comboBox, BorderLayout.NORTH);

        southButton = new JButton("South");
        southButton.addActionListener(this);
        contentPane.add(southButton, BorderLayout.SOUTH);


        // ---------------------------------------------------------------------
        // Window listener to close application when Window gets closed
        // ---------------------------------------------------------------------
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
    }


    public void actionPerformed(ActionEvent e) {
    
        String strPLAF = new String();
                
        try {
                
            String s = e.getActionCommand();
            System.out.println(s);
            
            if (s.equals("Metal")) {
                strPLAF = "javax.swing.plaf.metal.MetalLookAndFeel";
            } else if (s.equals("Windows")) {
                strPLAF = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
            } else if (s.equals("Motif")) {
                strPLAF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            } else {
                return;
            }
            
            UIManager.setLookAndFeel(strPLAF);
            SwingUtilities.updateComponentTreeUI(this);
            pack();
                    
        } catch (Exception e2) {
            e2.printStackTrace(System.err);
        }
    }


    /**
     * Overridden method that gets called when the user chooses a new option
     * in the item list.
     * @param evt The triggering event
     */
    public void itemStateChanged(ItemEvent evt) {
        System.out.println("ITEM: " + evt.getItem());
    }

    
    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main( String args[]) {
        ChangeLookFeel app = new ChangeLookFeel();
        app.pack();
        app.setVisible(true);
    }

}


