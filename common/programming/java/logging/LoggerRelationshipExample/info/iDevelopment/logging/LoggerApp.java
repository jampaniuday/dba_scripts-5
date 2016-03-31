// -----------------------------------------------------------------------------
// LoggerApp.java
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
 
package info.iDevelopment.logging;

import java.util.logging.*;
import info.iDevelopment.logging.child.*;

/**
 * -----------------------------------------------------------------------------
 * 
 * The following example application uses both the ParentLogger and ChildLogger
 * class to demonstrate Logger Relationships. Notice the messages written to
 * the console after executing this program. All messages are being logged
 * at the level specified by the ChildLogger class since we DID set the level
 * of logging [ via the method logger.setLevel(Level.INFO) ] in the ChildLogger
 * class.
 * 
 * 
 *  Compile Program
 *  --------------------------
 *      javac info/iDevelopment/logging/*.java
 *
 *  Run Program
 *  --------------------------
 *      java info.iDevelopment.logging.LoggerApp
 *
 *  Program Output
 *  --------------------------
 *      Jun 26, 2003 12:37:19 PM info.iDevelopment.logging.child.ChildLogger printMethod
 *      INFO: Child Logger: Info message!
 *      Jun 26, 2003 12:37:19 PM info.iDevelopment.logging.child.ChildLogger printMethod
 *      SEVERE: Child Logger: Severe message!
 * 
 * 
 * To really test how the logging relationship works, we can NOT set the 
 * logging level in the ChildLogger by commenting out the line:
 * 
 *      // level = Level.INFO;
 * 
 * When commenting out the above line in the ChildLogger class, passing null
 * to logger.setLevel(), then according to the package structure, the logger
 * in the ChildLogger class will try to use the Logger and its associated
 * level defined int he ParentLogger class in the "info.iDevelopment.logging" 
 * parent package. In this case, the level associated with teh parent logger
 * is SEVERE.
 * 
 * Executing the program with no level attached to the Logger object in the
 * ChildLogger class will result in only the message with the level SEVERE
 * printing:
 * 
 *      Jun 26, 2003 1:04:04 PM info.iDevelopment.logging.child.ChildLogger printMethod
 *      SEVERE: Child Logger: Severe message!
 * 
 * This program highlights how the parent-child relationship between Logger
 * objects can affect the logging from within an application. For most
 * applications, this type of relationship may be undesirable. For situations
 * like this, you can use the setUseParentHandlers() method in the Logger class 
 * to set the use of the parent logger and the associated Handlers to false.
 * However, in some cases this parent-child relationship can be judicially 
 * employed to log the same message in two different destinations. Take for
 * example a situation where the logger "info.iDevelopment.logging" is designed
 * to write log messages to a file. The program "ChildLogger" is required to
 * write the information to both console and file. In this situation, the child
 * logger "info.iDevelopment.logging.child" can use a ConsoleHandler to write
 * to the console, whereas the logging requests will automatically be delegated
 * to the parent logger "info.iDevelopment.logging". The parent logger will
 * log the same information to a file.
 * 
 * We can also see the fact that the change of configuration in the parent
 * logger can affect the behavior of the child logger.
 * 
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class LoggerApp {


    public static void main(String[] args) {

        ParentLogger pLogger = new ParentLogger();
        ChildLogger   cLogger = new ChildLogger();
        cLogger.printMethod();

    }

} 


