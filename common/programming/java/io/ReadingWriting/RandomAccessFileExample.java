// -----------------------------------------------------------------------------
// RandomAccessFileExample.java
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

import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;

/**
 * -----------------------------------------------------------------------------
 * This program demonstrates how to randomly access a file using the
 * RandomAccessFile class.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class RandomAccessFileExample {

    private static void doAccess() {

        try {
        
            File file = new File("RandomAccessFileExample.out");
            RandomAccessFile raf = new RandomAccessFile(file, "rw");

            // Read a character
            byte ch = raf.readByte();
            System.out.println("Read first character of file: " + (char)ch);

            // Now read the remaining portion of the line.
            // This will print out from where the file pointer is located
            // (just after the '+' character) and print all remaining characters
            // up until the end of line character.
            System.out.println("Read full line: " + raf.readLine());


            // Seek to the end of file
            raf.seek(file.length());

            // Append to the end of the file
            raf.write(0x0A);
            raf.writeBytes("This will complete the example");

            raf.close();
            
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
        doAccess();
    }

}
