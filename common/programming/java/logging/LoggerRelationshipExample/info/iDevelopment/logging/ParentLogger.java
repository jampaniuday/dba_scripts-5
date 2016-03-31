// -----------------------------------------------------------------------------
// ParentLogger.java
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

/**
 * -----------------------------------------------------------------------------
 * Up to this point in the examples, I have shown code that places all 
 * Logger objects in the same namespace hierarchy for a given application
 * instance. This example provides code to demonstrate the parent-child 
 * relationship of Logger objects.
 * 
 * This class is contained within the info.iDevelopment.logging package and
 * contains a method named printMethod() that prints logging information to a
 * console.
 * 
 * We first obtain a reference to the Logger object with the namespace
 * "info.iDevelopment.logging". It will also assign SEVERE as the level of
 * logging, which means that this logger will ONLY log messages with the 
 * priority of SEVERE. All other logging requests will be discarded, as SEVERE
 * is the highest level. When instantiating the ParentLogger class and invoke
 * the printMethod(), it will print the logging information to the console.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ParentLogger {

    private Logger logger = Logger.getLogger("info.iDevelopment.logging");
    private Level  level  = null;

    public ParentLogger() {

        level = Level.SEVERE;
        
        // Set the level as SEVERE
        logger.setLevel(level);

    }

    public void printMethod() {

        logger.log(level, "Parent Logger: Severe message!");

    }

} 


