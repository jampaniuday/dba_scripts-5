// -----------------------------------------------------------------------------
// StringTokenizerDemo.java
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
 
import java.util.*;

/**
 * -----------------------------------------------------------------------------
 * Used to provide an example of the StringTokenizer method for parsing strings.
 * The StringTokenizer method is useful for taking strings apart, breaking the 
 * string down into words or tokens. The string will be broken into tokens
 * at the word boundries in European languages or any other character delimitor.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */
 
public class StringTokenizerDemo {

    private static void doStringTokenizer1() {

        String a = "Alex Michael Hunter";

        System.out.println("-------------------------");
        System.out.println("Original string : " + a );

        StringTokenizer st = new StringTokenizer(a);
        while (st.hasMoreTokens()) {
            System.out.println("Token : " + st.nextToken());
        }
        System.out.println();
    }

    private static void doStringTokenizer2() {

        printResults2("A|B|C|D" , process2("A|B|C|D") );
        printResults2("A||C|D"  , process2("A||C|D")  );
        printResults2("A|||D|E" , process2("A|||D|E") );

    }

    private static void printResults2(String input, String[] outputs) {
        System.out.println("Input: " + input);
        for (int i=0; i<outputs.length; i++) {
            System.out.println("Output " + i + " was: " + outputs[i]);
        }
    }

    private static String[] process2(String line) {

        int    MAXFIELDS = 5;
        String DELIM     = "|";

        String[] results = new String[MAXFIELDS];

        // Unless you ask StringTokenizer to give you the tokens, it silently
        // discards multiple tokens.
        StringTokenizer st = new StringTokenizer(line, DELIM, true);

        int i = 0;

        // Stuff each token into the current user
        while (st.hasMoreTokens()) {

            String s = st.nextToken();
            if (s.equals(DELIM)) {
                if (i++ >= MAXFIELDS) {
                    // This is messy: A vector would be better to allow any
                    // number of fields
                    throw new IllegalArgumentException (
                        "Input line " +
                        line +
                        " has too many fields");
                }
                continue;
            }
            results[i] = s;

        } // while
        return results;

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        doStringTokenizer1();
        doStringTokenizer2();
    }

}

