// -----------------------------------------------------------------------------
// rot13.java
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

/**
 * -----------------------------------------------------------------------------
 * The following example is a "very" simple version of the rot13 algorithm.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class rot13 {

    static private int abyte = 0;
    static private String password = "ThisIsMyPassphrase";

    public static String doConvert(String in) {

        StringBuffer tempReturn = new StringBuffer();


        for (int i=0; i<in.length(); i++) {

            abyte = in.charAt(i);
            int cap = abyte & 32;
            abyte &= ~cap;
            abyte = ( (abyte >= 'A') && (abyte <= 'Z') ? ((abyte - 'A' + 13) % 26 + 'A') : abyte) | cap;
            tempReturn.append((char)abyte);
        }


        return tempReturn.toString();
        
    }


    public static void main (String args[]) {

        System.out.println("\nInitial password           = " + password);

        String aPassword = doConvert(password);
        System.out.println("Convert initial password   = " + aPassword);

        String bPassword = doConvert(aPassword);
        System.out.println("Perform another conversion = " + bPassword + "\n");
    }

}
