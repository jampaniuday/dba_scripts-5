// -----------------------------------------------------------------------------
// CommandLineParameters.java
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
 
/**
 * -----------------------------------------------------------------------------
 * Used to provide an example of passing parameters from the command line.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class CommandLineParameters {

    private static void usage() {

        System.out.println("\nUsage: \n\t" +
                           "CommandLineParameters p1 p2 p3\n");
        System.exit(1);

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {

        if (args.length != 3) usage();


        if (args[0].equals("p1")) {
            System.out.println("Received the correct first parameter \"p1\".");
        } else {
            System.out.println("You did not pass the first parameter as \"p1\".");
        }


        if (args[1].equals("p2")) {
            System.out.println("Received the correct second parameter \"p2\".");
        } else {
            System.out.println("You did not pass the second parameter as \"p2\".");
        }


        if (args[2].equals("p3")) {
            System.out.println("Received the correct third parameter \"p3\".");
        } else {
            System.out.println("You did not pass the third parameter as \"p3\".");
        }

    } 

}

