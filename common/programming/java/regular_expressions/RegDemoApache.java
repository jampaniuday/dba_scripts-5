// -----------------------------------------------------------------------------
// RegDemoApache.java
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
 
import org.apache.regexp.*;

/**
 * -----------------------------------------------------------------------------
 * Used to provide an example of how to utilize the Apache Regular Expression 
 * Implementation.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */
 
public class RegDemoApache {

    private static void doRegDemo() throws RESyntaxException {

        RE r;
        boolean found;

        String pattern1 = "^A[^b]-\\d+ - ";
        String input1   = "AU-120 - Network Cable.";

        String pattern2 = "^A[^U]\\d+ - ";
        String input2   = "AU-130 - Network Cable.";

        System.out.println();

        r = new RE(pattern1);
        found = r.match(input1);
        System.out.println(
            pattern1 + 
            (found ? "  [ MATCHES ]  " : "  [ DOES NOT MATCH ]  ") + 
            input1 + "\n");

        r = new RE(pattern2);
        found = r.match(input2);
        System.out.println(
            pattern2 + 
            (found ? "  [ MATCHES ]  " : "  [ DOES NOT MATCH ]  ") + 
            input2 + "\n");

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) throws RESyntaxException  {
        doRegDemo();
    }

}

