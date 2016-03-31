// -----------------------------------------------------------------------------
// ConvertingDataTypes.java
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
 * Used to provide several examples of converting data types in Java.
 * Throughout many of the examples, I will make use of many <code>static</code>
 * classes included with Sun's SDK.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ConvertingDataTypes {

    /**
     * Helper utility used to print a String to STDOUT.
     * @param s String that will be printed to STDOUT.
     */
    private static void prt(String s) {
        System.out.println(s);
    }

    /**
     * Helper utility used to print a String to STDOUT.
     */
    private static void prt() {
        System.out.println();
    }

    private static void doDataConversionExample() {

        prt("+------------------------------------------+");
        prt("| Declare Global Variables                 |");
        prt("+------------------------------------------+");
        String  str        = null;
        String  binstr     = null;
        String  hexstr     = null;
        String  aChar      = null;
        int     i          = 1994;
        long    l          = 5823523441948l;
        float   f          = 1948.35f;
        double  d          = 19488177491027.19;
        boolean b          = false;
        prt();
        prt();



        prt("+------------------------------------------+");
        prt("| integer to String                        |");
        prt("+------------------------------------------+");
        str = Integer.toString(i);
        prt("Converted integer " + i + " to String \"" + str + "\"");
        str = "" + i;
        prt("Converted integer " + i + " to String \"" + str + "\"");
        prt(); 

        prt("+------------------------------------------+");
        prt("| long to String                           |");
        prt("+------------------------------------------+");
        str = Long.toString(l);
        prt("Converted long " + l + " to String \"" + str + "\"");
        prt(); 

        prt("+------------------------------------------+");
        prt("| float to String                           |");
        prt("+------------------------------------------+");
        str = Float.toString(f);
        prt("Converted float " + f + " to String \"" + str + "\"");
        prt(); 
        
        prt("+------------------------------------------+");
        prt("| double to String                         |");
        prt("+------------------------------------------+");
        str = Double.toString(d);
        prt("Converted double " + d + " to String \"" + str + "\"");
        prt();
        prt();



        prt("+------------------------------------------+");
        prt("| String to integer                        |");
        prt("+------------------------------------------+");
        str = "1994";
        i = Integer.valueOf(str).intValue();
        prt("Converted String \"" + str + "\" to integer " + i);
        i = Integer.parseInt(str);
        prt("Converted String \"" + str + "\" to integer " + i);
        prt();

        prt("+------------------------------------------+");
        prt("| String to long                           |");
        prt("+------------------------------------------+");
        str = "5823523441948";
        l = Long.valueOf(str).longValue();
        prt("Converted String \"" + str + "\" to long " + l);
        l = Long.parseLong(str);
        prt("Converted String \"" + str + "\" to long " + l);
        prt();

        prt("+------------------------------------------+");
        prt("| String to float                          |");
        prt("+------------------------------------------+");
        str = "1948.35";
        f = Float.valueOf(str).floatValue();
        prt("Converted String \"" + str + "\" to float " + f);
        prt();

        prt("+------------------------------------------+");
        prt("| String to double                         |");
        prt("+------------------------------------------+");
        str = "19488177491027.19";
        d = Double.valueOf(str).doubleValue();
        prt("Converted String \"" + str + "\" to double " + d);
        prt();
        prt();


        prt("+------------------------------------------+");
        prt("| decimal to Binary String                 |");
        prt("+------------------------------------------+");
        binstr = Integer.toBinaryString(i);
        prt("Converted decimal " + i + " to Binary String \"" + binstr + "\"");
        prt();

        prt("+------------------------------------------+");
        prt("| decimal to hexadecimal                   |");
        prt("+------------------------------------------+");
        hexstr = Integer.toString(i, 16);
        prt("Converted decimal " + i + " to hexadecimal \"" + hexstr + "\"");
        hexstr = Integer.toHexString(i);
        prt("Converted decimal " + i + " to hexadecimal \"" + hexstr + "\"");
        hexstr = Integer.toHexString( 0x10000 | i).substring(1).toUpperCase();
        prt("Converted decimal " + i + " to hexadecimal \"" + hexstr + "\"");
        prt();

        prt("+------------------------------------------+");
        prt("| hexadecimal (String) to integer          |");
        prt("+------------------------------------------+");
        hexstr  = "B8DA3";
        i = Integer.valueOf(hexstr, 16).intValue();
        prt("Converted hexadecimal (String) \"" + hexstr + "\" to integer " + i);
        i = Integer.parseInt(hexstr, 16); 
        prt("Converted hexadecimal (String) \"" + hexstr + "\" to integer " + i);
        prt();

        prt("+------------------------------------------+");
        prt("| ASCII code to String                     |");
        prt("+------------------------------------------+");
        int j = 65;
        aChar = new Character((char)j).toString();
        prt("Converted ASCII code " + j + " to String \"" + aChar + "\"");
        prt();

        prt("+------------------------------------------+");
        prt("| integer to ASCII code (byte)             |");
        prt("+------------------------------------------+");
        char c = 'A';
        i = (int) c; // i will have the value 65 decimal
        prt("Converted integer " + c + " to ASCII code (byte) " + i);
        prt();

        prt("+------------------------------------------+");
        prt("| To extract Ascii codes from a String     |");
        prt("+------------------------------------------+");
        String test = "ABCD";
        for ( int x = 0; x < test.length(); ++x ) {
            char e = test.charAt( x );
            int k = (int) e;
            System.out.println(k);
        }
        prt();
        prt();



        prt("+------------------------------------------+");
        prt("| integer to boolean                       |");
        prt("+------------------------------------------+");
        i = -1;
        b = (i != 0);
        prt("Converted integer " + i + " to boolean " + b);
        i = 0;
        b = (i != 0);
        prt("Converted integer " + i + " to boolean " + b);
        i = 1;
        b = (i != 0);
        prt("Converted integer " + i + " to boolean " + b);
        prt();

        prt("+------------------------------------------+");
        prt("| boolean to integer                       |");
        prt("+------------------------------------------+");
        b = false;
        i = (b)?1:0;
        prt("Converted boolean " + b + " to integer " + i);
        b = true;
        i = (b)?1:0;
        prt("Converted boolean " + b + " to integer " + i);
        prt();
        prt();



        prt("+--------------------------------------------------------------+");
        prt("| Catch illegal number conversion, use the try/catch mechanism |");
        prt("+--------------------------------------------------------------+");
        try {
            str = "1994";
            i = Integer.parseInt(str);
            prt("Converted character \"" + str + "\" to integer " + i);
        } catch(NumberFormatException e) {
            prt("Number Format Exception");
        }

        try {
            str = "FAILURE";
            i = Integer.parseInt(str);
            prt("Converted character \"" + str + "\" to integer " + i);
        } catch(NumberFormatException e) {
            prt("Number Format Exception");
        }

        prt();


    }


    public static void main(String[] args) {
        prt();
        doDataConversionExample();
    }

}
