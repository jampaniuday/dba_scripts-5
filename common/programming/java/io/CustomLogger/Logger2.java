//------------------------------------------------------------------------------
// Logger2.java
//------------------------------------------------------------------------------

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
 * A class used to create/write/close log files in a common and consistant
 * manner.
 * <p>
 * Ensure to call the start() method before writing and the close() method when
 * finished. Data may not be writen to the file if close() is not called.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 */

public class Logger2 {

    private String      logFileName  = null;
    private PrintWriter pw           = null;
    private Date        startTime    = null;
    private Date        stopTime     = null;


    /**
     * Constructor used to create this object.  Responsible for setting
     * this object's log file name.
     * @param logFileName Name of the path/logfile to create.
     */
    public Logger2 (String logFileName) {
        this.logFileName = logFileName;
    }


    /**
     * Used to open the log file for writting.  This method MUST
     * be called before writting to the log file.
     * @throws IOException Exception thrown from the java.io package.
     */
    public void start() throws IOException {
        startTime = Calendar.getInstance().getTime();
        pw = new PrintWriter(new FileWriter(logFileName));

        writeln("# --------------------------------------------------------------------");
        writeln("# LOG FILE     : " + logFileName);
        writeln("# START TIME   : " + startTime);
        writeln("# --------------------------------------------------------------------");
        writeln();
    }


    /**
     * Used to write a given String object (without a
     * newline) to the log file.
     * @param s String that will be written to the log file.
     */
    public void write (String s) {
        pw.print(s);
    }


    /**
     * Used to write a given String object (with a
     * newline) to the log file.
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
     * Used to print a Properties object to the log file.
     * @param prop Properties object to print to log.
     * @param propDescription Description of the Property object.
     */
    public void writeProperties(Properties prop, String propDescription) {

        writeln();
        writeln("+-------------------------------------------+");
        writeln("| METHOD: writeProperties                   |");
        writeln("+-------------------------------------------+");
        writeln();
        writeln("Propery Description  : => " + propDescription);
        writeln("-----------------------------------------------------------");

        Enumeration enProps = prop.propertyNames();
        String key = null;
        String value = null;
        while ( enProps.hasMoreElements() ) {
            key = (String) enProps.nextElement();
            if (  "truesource.db.user.tsadmin.pwd".equalsIgnoreCase(key)
                  ||
                  "truesource.db.user.dba.pwd".equalsIgnoreCase(key)
                  ||
                  "truesource.db.user.sys.pwd".equalsIgnoreCase(key)) {
                value = "*********";
            } else {
                value = prop.getProperty(key);
            }
            writeln(key + " = " + value);
        }

        writeln();

    }


    /**
     * Used to print a Properties object to the log file.  This method
     * does not provide a Properties description string.
     * @param prop Properties object to print to log.
     */
    public void writeProperties(Properties prop) {
        writeProperties(prop, "[ No description provided! ]");
    }


    /**
     * A universal error routine that writes error messages to the log 
     * file for use in debugging any run-time errors.
     * This method should be called for all run-time errors that would need to 
     * be displayed in the log file before exiting the application. Note that any
     * error message that need to be written to a log file should occur BEFORE
     * calling the <code>Util.error()</code> method. 
     * @param e Exception that was thrown to get us to this method.
     * @param errorString A string supplied by the developer to better understand
     *        the error.
     * @param sqlText SQL Text used for JDBC connections.
     */
    public void writeError(  Exception  e
                           , String     errorString
                           , String     sqlText) {

        StackTraceElement[] elements = e.getStackTrace();

        writeln();
        writeln("+---------------+");
        writeln("| PROGRAM ERROR |");
        writeln("+-------------------------------------------------------------------------------+");
        writeln("| ERROR STRING  : " + errorString);
        writeln("| MESSAGE       : " + e.getMessage());
        writeln("| CAUSE         : " + e.getCause());
        writeln("| STACK TRACE   : ");
        for (int i=0, n=elements.length; i<n; i++) {
            writeln(
                "|      at " + 
                elements[i].getClassName() + "." + elements[i].getMethodName() +
                    "()" +
                "    [FILE -> " +
                (elements[i].getFileName() == null 
                    ? "Unknown Source File" 
                    : (elements[i].isNativeMethod() 
                        ? "Native Method" 
                        : elements[i].getFileName() + ":" + 
                          elements[i].getLineNumber()
                      )
                ) +
                "]");
        }

        if (sqlText != null) {
            writeln("| SQL TEXT      : \n" + sqlText);
        }

        writeln("+-------------------------------------------------------------------------------+");
        writeln();
        stop();

    }

    /**
     * A universal error routine that writes error messages to the log 
     * file for use in debugging any run-time errors.
     * This method should be called for all run-time errors that would need to 
     * be displayed in the log file before exiting the application. Note that any
     * error message that need to be written to a log file should occur BEFORE
     * calling the <code>Util.error()</code> method. 
     * @param e Exception that was thrown to get us to this method.
     * @param errorString A string supplied by the developer to better understand
     *        the error.
     */
    public void writeError(  Exception  e
                           , String     errorString) {
        writeError(e, errorString, null);
    }


    /**
     * Used to flush and close the log file.  This method MUST
     * be called in order for all data to be flushed and saved
     * to the log file.
     */
    public void stop() {
        stopTime = Calendar.getInstance().getTime();
        long diff = stopTime.getTime() - startTime.getTime();

        writeln();
        writeln("# --------------------------------------------------------------------");
        writeln("#           << END OF LOGFILE >>              ");
        writeln("# --------------------------------------------------------------------");
        writeln("# STOP TIME    : " + stopTime);
        writeln("# ELAPSED TIME : " + (diff / (1000L)) + " seconds.");
        writeln("# --------------------------------------------------------------------");
        pw.close();
    }

}
