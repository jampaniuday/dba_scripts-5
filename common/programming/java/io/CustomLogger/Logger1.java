// -----------------------------------------------------------------------------
// Logger1.java
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
 
import java.io.*;
import java.util.*;

/**
 * -----------------------------------------------------------------------------
 * Used to test the Java I/O package to format output to a file using the 
 * PrintWriter wrapper class.
 * <p>
 * FileWriter by itself works, but Java has another class that makes it much 
 * easier to do simple text output to a file: PrintWriter.  PrintWriter has 
 * print and println methods just like System.out, so using it to write to a 
 * file is almost the same as just printing text to the screen.  Furthermore,
 * PrintWriter handles the exceptions for you so you won't need to worry about
 * try/catch blocks every time you want to write a line to the file.  There is 
 * still one Exception that has to be dealt with, though.  A PrintWriter object 
 * takes a FileWriter object as a parameter in the constructor.  Remember that
 * even the constructor of FileWriter throws an exception, so you will have to
 * create it inside of a try/catch block.
 * <p>
 * Think of close() as "Save".  Calling close() saves the file.  As long as you
 * call close() when you are finished writing to the file, you don't need to 
 * worry about calling flush().  If you forget to call close(), though, nothing 
 * will be written to the file.  It would be the same as if you had typed your
 * entire program and then closed Emacs without saving - whoops.
 * <p>
 * Example usage:
 *
 *   Logger1 log = new Logger1("TestLogger.log");
 *   log.start();
 *   log.writeln("main : < Setting counter to 1");
 *   log.writeln("main : > Out of main with counter at 1024");
 *   log.stop();
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class Logger1 {

    private String      logFileName  = null;
    private PrintWriter pw           = null;
    private Date        startTime    = null;
    private Date        stopTime     = null;


    /**
     * Used to write a given String object (without a newline) to the log file.
     * @param s String that will be written to the log file.
     */
    public void write (String s) {
        pw.print(s);
    }


    /**
     * Used to write a given String object (with a newline) to the log file.
     * @param s String that will be written to the log file.
     */
    public void writeln (String s) {
        pw.println(s);
    }


    /**
     * Used to write a newline character to the log file.
     */
    public void writeln () {
        pw.println();
    }


    /**
     * Used to open the log file for writting.  This method MUST be called
     * before writting to the log file.
     */
    public void start() {
        startTime = Calendar.getInstance().getTime();

        try {
            pw = new PrintWriter(new FileWriter(logFileName));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        pw.println("# --------------------------------------------");
        pw.println("# LOG FILE     : " + logFileName);
        pw.println("# START TIME   : " + startTime);
        pw.println("# --------------------------------------------");
        pw.println();
    }


    /**
     * Used to flush and close the log file.  This method MUST be called in 
     * order for all data to be flushed and saved to the log file.
     */
    public void stop() {
        stopTime = Calendar.getInstance().getTime();
        long diff = stopTime.getTime() - startTime.getTime();

        pw.println();
        pw.println("# --------------------------------------------");
        pw.println("#           << END OF LOGFILE >>              ");
        pw.println("# --------------------------------------------");
        pw.println("# STOP TIME    : " + stopTime);
        pw.println("# ELAPSED TIME : " + (diff / (1000L)) + " seconds.");
        pw.println("# --------------------------------------------");
        pw.close();
    }


    /**
     * Constructor used to create this object.  Responsible for setting
     * this object's log file name.
     * @param logFileName Name of the path/logfile to create.
     */
    public Logger1 (String logFileName) {
        this.logFileName = logFileName;
    }

}
