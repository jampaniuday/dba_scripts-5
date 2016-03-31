// -----------------------------------------------------------------------------
// ChildLogger.java
// -----------------------------------------------------------------------------

/*
 * =============================================================================
 * Copyright (c) 1998-2007 Jeffrey M. Hunter. All rights reserved.
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
 
package info.iDevelopment.logging.child;

import java.util.logging.*;
import info.iDevelopment.logging.ParentLogger;

/**
 * -----------------------------------------------------------------------------
 * Up to this point in the examples, I have shown code that places all 
 * Logger objects in the same namespace hierarchy for a given application
 * instance. This example provides code to demonstrate the parent-child 
 * relationship of Logger objects.
 * 
 * This class is contained within the info.iDevelopment.logging.child package
 * and contains a method named printMethod() that prints logging information to 
 * a console. This program is similar to ParentLogger.java with the exception
 * that the logger object obtained in the ParentLogger class will now be the
 * parent logger of the Logger object obtained in the ChildLogger class. This is
 * due to the fact that the namespace "info.iDevelopment.logging.child" resides
 * below the namespace "info.iDevelopment.logging".
 * 
 * Notice that within the constructor, we specify the level associated with the
 * Logger object obtained as INFO. Within the printMethod(), we print two
 * logging messages, one with the level INFO and the other with the level 
 * SEVERE.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ChildLogger {

    private Logger logger = Logger.getLogger("info.iDevelopment.logging.child");
    private Level  level  = null;

    public ChildLogger() {

        // NOTE: You can test how the Logging API Relationship works by
        //       commenting out the following line!
        level = Level.INFO;
        
        // Set the level as INFO by default.
        // Notice that we can comment out the above line and pass a null value 
        // to setLevel(). If the value is specified, it will use the level
        // specified. If no value is specified, it will use the level supplied 
        // by the parent logger!
        logger.setLevel(level);

    }

    public void printMethod() {

        logger.log(Level.INFO,   "Child Logger: Info message!");
        logger.log(Level.SEVERE, "Child Logger: Severe message!");

    }

} 


