// -----------------------------------------------------------------------------
// JavaStatement.java
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * -----------------------------------------------------------------------------
 * A Java program that lets you (the developer) run a short segment of Java code
 * without the need to write, compile, and run a small program simply
 * to test short pieces of code. Having to perform this task repeatedly just
 * to test small things can become annoying very fast.
 * 
 * If you take a look in the tools.jar library (found in the $JAVA_HOME/lib
 * directory of your Java SDK), you'll find the javac compiler. Many developers
 * do not realize that an application can access javac programmatically. The
 * class com.sun.tools.javac.Main acts as the main entry point. Basically,
 * if you know how to use javac on the command line, you already know how to
 * use this class. It's compile() method takes the familiar command-line input
 * arguments.
 *
 * 
 * COMPILE:
 *      javac -classpath "$JAVA_HOME/lib/tools.jar:." JavaStatement.java
 *  
 * RUN:
 *      java -cp "$JAVA_HOME/lib/tools.jar:." JavaStatement
 * -----------------------------------------------------------------------------
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class JavaStatement extends Frame implements ActionListener {

    /** The compiler object. */
    private com.sun.tools.javac.Main javac = new com.sun.tools.javac.Main();

    private TextArea text;
    private Button runBtn;


    /**
     * Create a new JavaStatement window.
     */
    public JavaStatement() {
        super("Java Statement");

        // Create the components

        text = new TextArea(5, 45);
        text.setFont(new Font("Monospaced", Font.PLAIN, 12));

        runBtn = new Button("Run!");
        runBtn.addActionListener(this);

        // Lay them out

        this.add(text, BorderLayout.CENTER);
        Panel p = new Panel();
        p.add(runBtn);
        this.add(p, BorderLayout.SOUTH);

        this.setBackground(SystemColor.control);
    }


    /**
     * Shows a message to the user.
     *
     * @param msg the message
     */
    private void showMsg(String msg) {
        System.err.println(msg);
    }


    /**
     * Compiles and runs the short code segment.
     *
     * @throws IOException if there was an error creating the source file.
     */
    private void doRun() throws IOException {
        // Create a temp. file

        File file = File.createTempFile("jav", ".java",
                new File(System.getProperty("user.dir")));

        // Set the file to be deleted on exit

        file.deleteOnExit();

        // Get the file name and extract a class name from it

        String filename = file.getName();
        String classname = filename.substring(0, filename.length()-5);

        // Output the source

        PrintWriter out = new PrintWriter(new FileOutputStream(file));
        out.println("/**");
        out.println(" * Source created on " + new Date());
        out.println(" */");
        out.println("public class " + classname + " {");
        out.println("    public static void main(String[] args) throws Exception {");

        // Your short code segment

        out.print("        "); out.println(text.getText());

        out.println("    }");
        out.println("}");

        // Flush and close the stream

        out.flush();
        out.close();

        // Compile

        String[] args = new String[] {
            "-d", System.getProperty("user.dir"),
            filename
        };
        int status = javac.compile(args);

        // Run

        switch (status) {
            case 0:  // OK
                // Make the class file temporary as well

                new File(file.getParent(), classname + ".class").deleteOnExit();

                try {
                    // Try to access the class and run its main method

                    Class clazz = Class.forName(classname);
                    Method main = clazz.getMethod("main", new Class[] { String[].class });
                    main.invoke(null, new Object[] { new String[0] });
                } catch (InvocationTargetException ex) {
                    // Exception in the main method that we just tried to run

                    showMsg("Exception in main: " + ex.getTargetException());
                    ex.getTargetException().printStackTrace();
                } catch (Exception ex) {
                    showMsg(ex.toString());
                }
                break;
            case 1: showMsg("Compile status: ERROR"); break;
            case 2: showMsg("Compile status: CMDERR"); break;
            case 3: showMsg("Compile status: SYSERR"); break;
            case 4: showMsg("Compile status: ABNORMAL"); break;
            default:
                showMsg("Compile status: Unknown exit status");
        }
    }


    /**
     * Listens to actions from the run button.
     */
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (runBtn == source) {
            // Disable the run button while we're running
            runBtn.setEnabled(false);
            try {
                doRun();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            runBtn.setEnabled(true);
        }
    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {

        // Create the frame and set it to close on exit
        Frame f = new JavaStatement();
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Window w = e.getWindow();
                w.hide();
                w.dispose();
                System.exit(0);
            }
        });

        f.pack();
        f.show();

    }


}
