// -----------------------------------------------------------------------------
// ControllingCase.java
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
 * Used to provide an example of how to change the case of a given string.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ControllingCase {

    private static void doCase() {

        String a = "Alex Michael Hunter";
        String b = "alex michael hunter";

        System.out.println("\n");
        System.out.println("Original string  : " + a);
        System.out.println("Uppercase string : " + a.toUpperCase());
        System.out.println("Lowercase string : " + a.toLowerCase() + "\n");

        System.out.println("Original string  : " + a);
        System.out.println("Compare string  : " + b);
        
        if (a.equals(b)) {
            System.out.println("equal() returned TRUE");
        } else {
            System.out.println("equal() returned FALSE");
        }
        
        if (a.equalsIgnoreCase(b)) {
            System.out.println("equalIgnoreCase() returned TRUE");
        } else {
            System.out.println("equalIgnoreCase() returned FALSE");
        }

        System.out.println("\n");
    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        doCase();
    }

}

