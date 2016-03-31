// -----------------------------------------------------------------------------
// LoggingDemo2.java
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
 
import java.util.logging.*;
import java.io.*;

/**
 * -----------------------------------------------------------------------------
 * Simple demo that uses the Java SDK 1.4 Logging API
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class LoggingDemo2 {

    private static Logger         logger   = Logger.getLogger("info.iDevelopment.MyLogger");
    private        ConsoleHandler console  = null;
    private        FileHandler    file     = null;

    public LoggingDemo2() {

        // Create a new handler to write to the console
        console = new ConsoleHandler();

        // create a new handler to write to a named file
        try {
            file = new FileHandler("LoggingDemo2.xml");
        } catch(IOException ioe) {
            logger.warning("Could not create a file...");
        }

        // Add the handlers to the logger
        logger.addHandler(console);
        logger.addHandler(file);
        
    }

    public void logMessage() {

        // Log a message
        logger.info("I am logging a test message...");

    }

    public static void main(String [] args) {

        LoggingDemo2 demo = new LoggingDemo2();
        demo.logMessage();

    }

} 


