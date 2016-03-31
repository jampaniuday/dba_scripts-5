// -----------------------------------------------------------------------------
// ReadWriteTextFile.java
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
 
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * -----------------------------------------------------------------------------
 * This program takes the input of one file and outputs it to another in plain 
 * text.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class ReadWriteTextFile {

    private static void doReadWriteTextFile() {

        try {
        
            // input/output file names
            String inputFileName  = "README_InputFile.txt";
            String outputFileName = "ReadWriteTextFile.out";

            // Create FileReader Object
            FileReader inputFileReader   = new FileReader(inputFileName);
            FileWriter outputFileReader  = new FileWriter(outputFileName);

            // Create Buffered/PrintWriter Objects
            BufferedReader inputStream   = new BufferedReader(inputFileReader);
            PrintWriter    outputStream  = new PrintWriter(outputFileReader);

            // Keep in mind that all of the above statements can be combined
            // into the following:
            //BufferedReader inputStream = new BufferedReader(new FileReader("README_InputFile.txt"));
            //PrintWriter outputStream   = new PrintWriter(new FileWriter("ReadWriteTextFile.out"));

            outputStream.println("+---------- Testing output to a file ----------+");
            outputStream.println();

            String inLine = null;

            while ((inLine = inputStream.readLine()) != null) {
                outputStream.println(inLine);
            }

            outputStream.println();
            outputStream.println("+---------- Testing output to a file ----------+");

            outputStream.close();
            inputStream.close();

        } catch (IOException e) {

            System.out.println("IOException:");
            e.printStackTrace();

        }

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        doReadWriteTextFile();
    }

}
