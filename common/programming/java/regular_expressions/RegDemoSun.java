// -----------------------------------------------------------------------------
// RegDemoSun.java
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
 
import java.util.regex.*;

/**
 * -----------------------------------------------------------------------------
 * Used to provide an example of how to utilize the Java 2 Regular Expression
 * Implementation from Sun. It is important to note that a Regular Expression
 * implentation didn't appear in the Java SDK until version 1.4.
 * 
 * Sun's implementation of its regular expression parser enables you to both
 * "find" and "match" character sequences against a particular pattern. Using
 * "find" enables the user to find matches in a string while "match" requires
 * an EXACT match of a regular expression.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */
 
public class RegDemoSun {

    private static void doRegDemo() {

        String patternStrFull    = "^A[^b]-\\d+ - .+$";
        String patternStrPart    = "^A[^b]-\\d+ - ";

        CharSequence inputStr1      = "AU-120 - Network Cable.";
        CharSequence inputStr2      = "au-120 - Network Cable.";

        Pattern patternFull         = Pattern.compile(patternStrFull);
        Pattern patternPart         = Pattern.compile(patternStrPart);
        Pattern patternCaseInsFull  = Pattern.compile(patternStrFull, Pattern.CASE_INSENSITIVE);
        Pattern patternCaseInsPart  = Pattern.compile(patternStrPart, Pattern.CASE_INSENSITIVE);
        
        Matcher matcher = null;

        String matchStr     = " [ MATCHES ]  ";
        String noMatchStr   = " [ DOES NOT MATCH ]  ";
        String foundStr     = " [ FOUND ]  ";
        String noFoundStr   = " [ NOT FOUND ]  ";
        boolean found   = false;
        
        System.out.println();


        // -------------------------------------------------------
        // Test 1 : (Case Sensitive / match() v.s. find() )
        // -------------------------------------------------------

        System.out.println("Case sensitive :: matches() v.s. find()");
        System.out.println("---------------------------------------\n");
        
        matcher  = patternFull.matcher(inputStr1);
        found    = matcher.matches();
        System.out.println("Test 1: /" + inputStr1 + "/" + (found ? matchStr : noMatchStr) + "/" + patternStrFull + "/\n");

        matcher  = patternPart.matcher(inputStr1);
        found    = matcher.matches();
        System.out.println("Test 2: /" + inputStr1 + "/" + (found ? matchStr : noMatchStr) + "/" + patternStrPart + "/\n");

        matcher = patternFull.matcher(inputStr1);
        found = matcher.find();
        System.out.println("Test 3: /" + inputStr1 + "/" + (found ? foundStr : noFoundStr) + "/" + patternStrFull + "/\n");

        matcher = patternPart.matcher(inputStr1);
        found = matcher.find();
        System.out.println("Test 4: /" + inputStr1 + "/" + (found ? foundStr : noFoundStr) + "/" + patternStrPart + "/\n");

        matcher  = patternFull.matcher(inputStr2);
        found    = matcher.matches();
        System.out.println("Test 5: /" + inputStr2 + "/" + (found ? matchStr : noMatchStr) + "/" + patternStrFull + "/\n");

        matcher  = patternPart.matcher(inputStr2);
        found    = matcher.matches();
        System.out.println("Test 6: /" + inputStr2 + "/" + (found ? matchStr : noMatchStr) + "/" + patternStrPart + "/\n");

        matcher = patternFull.matcher(inputStr2);
        found = matcher.find();
        System.out.println("Test 7: /" + inputStr2 + "/" + (found ? foundStr : noFoundStr) + "/" + patternStrFull + "/\n");

        matcher = patternPart.matcher(inputStr2);
        found = matcher.find();
        System.out.println("Test 8: /" + inputStr2 + "/" + (found ? foundStr : noFoundStr) + "/" + patternStrPart + "/\n");

        System.out.println();



        // -------------------------------------------------------
        // Test 2 : (Case Insensitive / match() v.s. find() )
        // -------------------------------------------------------
        
        System.out.println("Case Insensitive :: matches() v.s. find()");
        System.out.println("-----------------------------------------\n");
        
        matcher  = patternCaseInsFull.matcher(inputStr1);
        found    = matcher.matches();
        System.out.println("Test 1: /" + inputStr1 + "/" + (found ? matchStr : noMatchStr) + "/" + patternStrFull + "/\n");

        matcher  = patternCaseInsPart.matcher(inputStr1);
        found    = matcher.matches();
        System.out.println("Test 2: /" + inputStr1 + "/" + (found ? matchStr : noMatchStr) + "/" + patternStrPart + "/\n");

        matcher = patternCaseInsFull.matcher(inputStr1);
        found = matcher.find();
        System.out.println("Test 3: /" + inputStr1 + "/" + (found ? foundStr : noFoundStr) + "/" + patternStrFull + "/\n");

        matcher = patternCaseInsPart.matcher(inputStr1);
        found = matcher.find();
        System.out.println("Test 4: /" + inputStr1 + "/" + (found ? foundStr : noFoundStr) + "/" + patternStrPart + "/\n");

        matcher  = patternCaseInsFull.matcher(inputStr2);
        found    = matcher.matches();
        System.out.println("Test 5: /" + inputStr2 + "/" + (found ? matchStr : noMatchStr) + "/" + patternStrFull + "/\n");

        matcher  = patternCaseInsPart.matcher(inputStr2);
        found    = matcher.matches();
        System.out.println("Test 6: /" + inputStr2 + "/" + (found ? matchStr : noMatchStr) + "/" + patternStrPart + "/\n");

        matcher = patternCaseInsFull.matcher(inputStr2);
        found = matcher.find();
        System.out.println("Test 7: /" + inputStr2 + "/" + (found ? foundStr : noFoundStr) + "/" + patternStrFull + "/\n");

        matcher = patternCaseInsPart.matcher(inputStr2);
        found = matcher.find();
        System.out.println("Test 8: /" + inputStr2 + "/" + (found ? foundStr : noFoundStr) + "/" + patternStrPart + "/\n");

        System.out.println();


        // ---------------------------------------------------------------
        // Test 3 : (Case Sensitive / Using convenience method matches() )
        // ---------------------------------------------------------------
        
        System.out.println("Case Sensitive :: Using convenience method Pattern.matches(Pattern, CharSequence)");
        System.out.println("---------------------------------------------------------------------------------\n");

        found = Pattern.matches(patternStrFull, inputStr1);
        System.out.println("Test 1: /" + inputStr1 + "/" + (found ? matchStr : noMatchStr) + "/" + patternStrFull + "/\n");

        found = Pattern.matches(patternStrPart, inputStr1);
        System.out.println("Test 2: /" + inputStr1 + "/" + (found ? matchStr : noMatchStr) + "/" + patternStrPart + "/\n"); 

        System.out.println();

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        doRegDemo();
    }

}
